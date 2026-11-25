package com.qualitysphere.ui;

import com.qualitysphere.base.BaseTest;
import com.qualitysphere.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NegativeLoginTest extends BaseTest {

    @Test
    public void verifyLoginWithIncorrectPassword() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("standard_user", "wrong_password");

        String errorMessage = loginPage.getErrorMessage();

        System.out.println("Login Error: " + errorMessage);

        Assert.assertTrue(
                errorMessage.contains("Username and password do not match"),
                "Expected login error was not displayed!"
        );
    }
}