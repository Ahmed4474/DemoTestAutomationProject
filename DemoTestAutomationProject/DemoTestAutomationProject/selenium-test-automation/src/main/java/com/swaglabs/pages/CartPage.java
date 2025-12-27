package com.swaglabs.pages;

import com.swaglabs.drivers.GUIDriver;
import com.swaglabs.utils.CustomSoftAssertion;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Page Object representing the cart page.
 * Responsible for validating selected products
 * and proceeding to checkout.
 */
public class CartPage {

    // Page locators
    private final By productName = By.cssSelector(".inventory_item_name");
    private final By productPrice = By.cssSelector(".inventory_item_price");
    private final By checkoutButton = By.id("checkout");

    // Driver reference
    private GUIDriver driver;

    // Constructor
    public CartPage(GUIDriver driver) {
        this.driver = driver;
    }

    // ---------- Internal helpers ----------

    @Step("Get product name")
    private String getProductName() {
        return driver.element().getText(productName);
    }

    @Step("Get product price")
    private String getProductPrice() {
        return driver.element().getText(productPrice);
    }

    // ---------- Actions ----------

    @Step("Click checkout button")
    public InformationPage clickCheckoutButton() {
        driver.element().click(checkoutButton);
        return new InformationPage(driver);
    }

    // ---------- Validations ----------

    /**
     * Soft validation for product details
     * before proceeding to checkout.
     */
    @Step("Assert product details")
    public CartPage assertProductDetails(
            String expectedProductName,
            String expectedProductPrice
    ) {
        CustomSoftAssertion.softAssertion.assertEquals(
                getProductName(),
                expectedProductName,
                "Product name mismatch"
        );

        CustomSoftAssertion.softAssertion.assertEquals(
                getProductPrice(),
                expectedProductPrice,
                "Product price mismatch"
        );

        return this;
    }
}
