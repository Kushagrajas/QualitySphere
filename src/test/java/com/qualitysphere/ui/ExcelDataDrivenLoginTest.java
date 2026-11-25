
package com.qualitysphere.ui;

import com.qualitysphere.base.BaseTest;
import com.qualitysphere.pages.LoginPage;
import com.qualitysphere.utils.ExcelUtils;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ExcelDataDrivenLoginTest extends BaseTest {

    @DataProvider(name = "excelLoginData")
    public Object[][] getExcelLoginData() {

        return ExcelUtils.readExcelData(
                "LoginTestData.xlsx",
                "Sheet1"
        );
    }

    @Test(dataProvider = "excelLoginData")
    public void verifyLoginUsingExcelData(
            String username,
            String password,
            boolean expectedSuccess) {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(username, password);

        if (expectedSuccess) {

            String actualTitle =
                    loginPage.getProductsPageTitle();

            Assert.assertEquals(
                    actualTitle,
                    "Products",
                    "Successful login failed!"
            );

            System.out.println(
                    "PASS: Successful login for " + username
            );

        } else {

            String errorMessage =
                    loginPage.getErrorMessage();

            Assert.assertTrue(
                    errorMessage != null &&
                            !errorMessage.isEmpty(),
                    "Expected login error was not displayed!"
            );

            System.out.println(
                    "PASS: Invalid login rejected for " + username
            );
        }
    }
}
