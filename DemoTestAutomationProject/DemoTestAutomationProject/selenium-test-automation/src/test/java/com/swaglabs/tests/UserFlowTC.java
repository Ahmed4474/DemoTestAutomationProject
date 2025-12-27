package com.swaglabs.tests;

import com.swaglabs.drivers.GUIDriver;
import com.swaglabs.listeners.TestNGListeners;
import com.swaglabs.pages.*;
import com.swaglabs.utils.JsonUtils;
import com.swaglabs.utils.PropertiesUtils;
import org.testng.annotations.*;

@Listeners(TestNGListeners.class)
public class UserFlowTC {

    // =========================
    // Test Variables
    // =========================

    // GUI driver instance
    GUIDriver driver;

    // Test data reader
    JsonUtils testData;

    // =========================
    // Test Case
    // =========================

    /**
     * Single end-to-end user flow test.
     * Covers:
     *  - Login
     *  - Add product
     *  - Checkout
     *  - Finish order
     */
    @Test
    public void userFlow() {

        // Login and navigate to Home page
        HomePage homePage = new LoginPage(driver)
                .enterUsername(testData.getJsonData("login-credentials.username"))
                .enterPassword(testData.getJsonData("login-credentials.password"))
                .clickLoginButton()
                .assertSuccessfulLogin();

        // Add product and navigate to Cart page
        CartPage cartPage = homePage
                .addSpecificProductToCart(testData.getJsonData("product-names.item1.name"))
                .assertProductAddedToCart(testData.getJsonData("product-names.item1.name"))
                .clickCartIcon();

        // Validate cart and navigate to Information page
        InformationPage informationPage = cartPage
                .assertProductDetails(
                        testData.getJsonData("product-names.item1.name"),
                        testData.getJsonData("product-names.item1.price")
                )
                .clickCheckoutButton();

        // Fill information and finish checkout
        informationPage
                .fillInformationForm(
                        testData.getJsonData("information-form.firstName"),
                        testData.getJsonData("information-form.lastName"),
                        testData.getJsonData("information-form.postalCode")
                )
                .assertInformationPage(
                        testData.getJsonData("information-form.firstName"),
                        testData.getJsonData("information-form.lastName"),
                        testData.getJsonData("information-form.postalCode")
                )
                .clickContinueButton()
                .clickFinishButton()
                .assertConfirmationMessage(
                        testData.getJsonData("confirmation-message")
                );
    }

    // =========================
    // Configuration
    // =========================

    /**
     * Loads test data once before execution.
     */
    @BeforeClass
    public void beforeClass() {
        testData = new JsonUtils("test-data");
    }

    /**
     * Initializes browser before each test.
     */
    @BeforeMethod(alwaysRun = true)
    public void setup() {
        String browserName = PropertiesUtils.getPropertyValue("browserType");
        driver = new GUIDriver(browserName);
        new LoginPage(driver).navigateToLoginPage();
    }

    /**
     * Closes browser after each test.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.browser().closeBrowser();
    }
}
