
package com.qualitysphere.ui;

import com.qualitysphere.base.BaseTest;
import com.qualitysphere.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataDrivenLoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {

        return new Object[][]{
                {"standard_user", "secret_sauce", true},
                {"standard_user", "wrong_password", false},
                {"wrong_user", "secret_sauce", false},
                {"", "secret_sauce", false},
                {"standard_user", "", false}
        };
    }

    @Test(dataProvider = "loginData")
    public void verifyLoginScenarios(
            String username,
            String password,
            boolean expectedSuccess) {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(username, password);

        if (expectedSuccess) {

            String actualTitle = loginPage.getProductsPageTitle();

            Assert.assertEquals(
                    actualTitle,
                    "Products",
                    "Successful login failed!"
            );

            System.out.println("Login successful for: " + username);

        } else {

            String errorMessage = loginPage.getErrorMessage();

            Assert.assertFalse(
                    errorMessage.isEmpty(),
                    "Expected error message was not displayed!"
            );

            System.out.println(
                    "Login rejected for username: " + username
            );
        }
    }
}
