package com.swaglabs.tests;

import com.swaglabs.drivers.GUIDriver;
import com.swaglabs.listeners.TestNGListeners;
import com.swaglabs.pages.CartPage;
import com.swaglabs.pages.HomePage;
import com.swaglabs.pages.InformationPage;
import com.swaglabs.pages.LoginPage;
import com.swaglabs.utils.JsonUtils;
import com.swaglabs.utils.PropertiesUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.swaglabs.utils.TimestampUtils.getTimestamp;

/**
 * E2E Test Class
 *
 * Covers a complete end-to-end user flow:
 *  - Login
 *  - Add product to cart
 *  - Checkout
 *  - Fill information
 *  - Finish order
 *
 * Tests are dependent to ensure correct execution sequence.
 */
@Listeners(TestNGListeners.class)
public class E2e {

    // =========================
    // Test Variables
    // =========================

    // GUI WebDriver wrapper
    GUIDriver driver;

    // Test data reader (JSON-based)
    JsonUtils testData;

    // Dynamic user data to avoid duplication issues
    String FIRST_NAME;
    String LAST_NAME;

    // =========================
    // Test Cases
    // =========================

    /**
     * Validates successful login using valid credentials.
     */
    @Test
    public void successfulLogin() {
        new LoginPage(driver)
                .enterUsername(testData.getJsonData("login-credentials.username"))
                .enterPassword(testData.getJsonData("login-credentials.password"))
                .clickLoginButton()
                .assertSuccessfulLogin();
    }

    /**
     * Adds a specific product to cart.
     * Depends on successful login.
     */
    @Test(dependsOnMethods = "successfulLogin")
    public void addingProductToCart() {
        new HomePage(driver)
                .addSpecificProductToCart(testData.getJsonData("product-names.item1.name"))
                .assertProductAddedToCart(testData.getJsonData("product-names.item1.name"));
    }

    /**
     * Opens cart and validates selected product details.
     */
    @Test(dependsOnMethods = "addingProductToCart")
    public void checkoutProduct() {
        new HomePage(driver)
                .clickCartIcon()
                .assertProductDetails(
                        testData.getJsonData("product-names.item1.name"),
                        testData.getJsonData("product-names.item1.price")
                );
    }

    /**
     * Fills checkout information form.
     */
    @Test(dependsOnMethods = "checkoutProduct")
    public void fillInformationForm() {
        new CartPage(driver)
                .clickCheckoutButton()
                .fillInformationForm(
                        FIRST_NAME,
                        LAST_NAME,
                        testData.getJsonData("information-form.postalCode")
                )
                .assertInformationPage(
                        FIRST_NAME,
                        LAST_NAME,
                        testData.getJsonData("information-form.postalCode")
                );
    }

    /**
     * Completes checkout and validates confirmation message.
     */
    @Test(dependsOnMethods = "fillInformationForm")
    public void finishCheckout() {
        new InformationPage(driver)
                .clickContinueButton()
                .clickFinishButton()
                .assertConfirmationMessage(
                        testData.getJsonData("confirmation-message")
                );
    }

    // =========================
    // Test Configuration
    // =========================

    /**
     * Initializes test data, driver, and navigates to login page.
     */
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {

        // Load test data from JSON file
        testData = new JsonUtils("test-data");

        // Generate unique user data using timestamp
        FIRST_NAME = testData.getJsonData("information-form.firstName") + getTimestamp();
        LAST_NAME = testData.getJsonData("information-form.lastName") + getTimestamp();

        // Initialize browser based on properties configuration
        String browserName = PropertiesUtils.getPropertyValue("browserType");
        driver = new GUIDriver(browserName);

        // Navigate to application login page
        new LoginPage(driver).navigateToLoginPage();
    }

    /**
     * Closes browser after test execution.
     */
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.browser().closeBrowser();
    }
}
