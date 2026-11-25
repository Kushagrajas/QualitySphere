
package com.qualitysphere.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {

    public static String captureScreenshot(
            WebDriver driver, String testName) throws IOException {

        TakesScreenshot screenshot = (TakesScreenshot) driver;

        File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);

        String timestamp = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        );

        String safeTestName = testName.replaceAll(
                "[^a-zA-Z0-9-_]", "_"
        );

        Path destination = Paths.get(
                "target",
                "screenshots",
                safeTestName + "_" + timestamp + ".png"
        );

        Files.createDirectories(destination.getParent());

        Files.copy(
                sourceFile.toPath(),
                destination,
                StandardCopyOption.REPLACE_EXISTING
        );

        return destination.toAbsolutePath().toString();
    }
}
