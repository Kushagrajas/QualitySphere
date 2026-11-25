package com.qualitysphere.pages;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final Logger logger =
            LogManager.getLogger(LoginPage.class);

    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By productsTitle = By.className("title");
    private By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        logger.info("LoginPage initialized");
    }

    public void enterUsername(String username) {
        Allure.step("Entering username");
        logger.info("Entering username");

        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField))
                .sendKeys(username);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void enterPassword(String password) {
        Allure.step("Entering password");
        logger.info("Entering password");

        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField))
                .sendKeys(password);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickLogin() {
        Allure.step("Clicking login button");
        logger.info("Clicking login button");

        wait.until(ExpectedConditions.elementToBeClickable(loginButton))
                .click();
    }

    public void login(String username, String password) {

        logger.info("Starting login process");

        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public String getProductsPageTitle() {
        Allure.step("Verifying Products page title");
        logger.info("Checking Products page title");

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(productsTitle)
        ).getText();
    }

    public String getErrorMessage() {

        logger.info("Retrieving login error message");

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ).getText();
    }
}
