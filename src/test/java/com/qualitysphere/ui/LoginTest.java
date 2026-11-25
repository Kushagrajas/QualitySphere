
package com.qualitysphere.ui;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;
import com.qualitysphere.base.BaseTest;
import com.qualitysphere.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("QualitySphere QA Automation")
@Feature("User Login")
@Listeners(AllureTestNg.class)
public class LoginTest extends BaseTest {
    @Story("Successful Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a registered user can successfully log in to SauceDemo.")
    @Test
    public void verifySuccessfulLogin() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("standard_user", "secret_sauce");

        String actualTitle = loginPage.getProductsPageTitle();

        System.out.println("Page heading: " + actualTitle);

        Assert.assertEquals(
                actualTitle,
                "Products",
        "Login was unsuccessful!"

        );
       // Assert.assertEquals("Wrong", "Correct");
    }
}
