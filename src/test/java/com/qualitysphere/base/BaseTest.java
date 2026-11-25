
package com.qualitysphere.base;

import com.qualitysphere.utils.ScreenshotUtils;

import io.qameta.allure.Allure;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.util.Properties;

public class BaseTest {

    protected WebDriver driver;
    protected Properties properties;

    protected Logger logger =
            LogManager.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp() {

        logger.info("========== Test Execution Started ==========");

        properties = new Properties();

        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "config.properties file not found!"
                );
            }

            properties.load(input);

        } catch (IOException e) {
            logger.error("Unable to load configuration file!", e);
            throw new RuntimeException(
                    "Unable to load configuration file!", e
            );
        }

        String browser = properties.getProperty("browser");
        String baseUrl = properties.getProperty("baseUrl");

        if ("chrome".equalsIgnoreCase(browser)) {

            logger.info("Launching Chrome browser");
            driver = new ChromeDriver();

        } else {

            logger.error("Unsupported browser: {}", browser);

            throw new IllegalArgumentException(
                    "Unsupported browser: " + browser
            );
        }

        driver.manage().window().maximize();

        logger.info("Opening application URL: {}", baseUrl);

        driver.get(baseUrl);

        logger.info("Application opened successfully");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        if (driver != null) {

            if (result.getStatus() == ITestResult.FAILURE) {

                logger.error(
                        "Test FAILED: {}",
                        result.getName()
                );

                try {

                    // Capture screenshot as bytes for Allure
                    byte[] screenshot = ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BYTES);

                    Allure.addAttachment(
                            "Failure Screenshot",
                            "image/png",
                            new ByteArrayInputStream(screenshot),
                            ".png"
                    );

                    logger.info(
                            "Screenshot attached to Allure report"
                    );

                    // Save screenshot locally as well
                    String screenshotPath =
                            ScreenshotUtils.captureScreenshot(
                                    driver,
                                    result.getName()
                            );

                    logger.info(
                            "Failure screenshot saved: {}",
                            screenshotPath
                    );

                } catch (Exception e) {

                    logger.error(
                            "Unable to capture failure screenshot",
                            e
                    );
                }

            } else if (result.getStatus() == ITestResult.SUCCESS) {

                logger.info(
                        "Test PASSED: {}",
                        result.getName()
                );

            } else if (result.getStatus() == ITestResult.SKIP) {

                logger.warn(
                        "Test SKIPPED: {}",
                        result.getName()
                );
            }

            logger.info("Closing browser");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            driver.quit();

            logger.info("Browser closed successfully");
        }

        logger.info("========== Test Execution Finished ==========");
    }
}
