
package com.qualitysphere.ui;

// Import our reusable BaseTest class
import com.qualitysphere.base.BaseTest;

// Import TestNG assertion and annotation
import org.testng.Assert;
import org.testng.annotations.Test;

// HomePageTest inherits browser setup from BaseTest
public class HomePageTest extends BaseTest {

    // First homepage test
    @Test
    public void verifyHomePageTitle() {

        // Get the title of the currently opened website
        String actualTitle = driver.getTitle();

        // Print title in the console
        System.out.println("Website Title: " + actualTitle);

        // Verify expected and actual titles
        Assert.assertEquals(
                actualTitle,
                "Swag Labs",
                "Homepage title is incorrect!"
        );
    }
}
