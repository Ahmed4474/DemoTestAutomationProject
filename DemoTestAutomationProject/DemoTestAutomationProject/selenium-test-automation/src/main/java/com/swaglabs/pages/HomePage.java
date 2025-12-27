package com.swaglabs.pages;

import com.swaglabs.drivers.GUIDriver;
import com.swaglabs.utils.LogsUtil;
import com.swaglabs.utils.PropertiesUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.locators.RelativeLocator;

/**
 * Page Object representing the home/products page.
 * Responsible for product interactions and navigation to cart.
 */
public class HomePage {

    // Page locators
    private final By cartIcon = By.cssSelector("[data-test='shopping-cart-link']");

    // Driver reference
    private GUIDriver driver;

    // Constructor
    public HomePage(GUIDriver driver) {
        this.driver = driver;
    }

    /**
     * Navigates user to the home page.
     */
    @Step("Navigate to home page")
    public HomePage navigateToHomePage() {
        driver.browser().navigateToURL(
                PropertiesUtils.getPropertyValue("homeURL")
        );
        return this;
    }

    /**
     * Dynamically locates a product by name
     * and adds it to the shopping cart.
     */
    @Step("Add specific product to cart")
    public HomePage addSpecificProductToCart(String productName) {
        LogsUtil.info("Adding " + productName + " to cart");

        By addToCartButton = RelativeLocator.with(By.tagName("button"))
                .below(By.xpath("//div[.='" + productName + "']"));

        driver.element().click(addToCartButton);
        return this;
    }

    @Step("Click cart icon")
    public CartPage clickCartIcon() {
        driver.element().click(cartIcon);
        return new CartPage(driver);
    }

    // ---------- Validations ----------

    /**
     * Verifies that a product was successfully added to the cart.
     */
    @Step("Assert product added to cart")
    public HomePage assertProductAddedToCart(String productName) {

        By addToCartButton = RelativeLocator.with(By.tagName("button"))
                .below(By.xpath("//div[.='" + productName + "']"));

        String actualValue = driver.element().getText(addToCartButton);

        driver.validate().validateEquals(
                actualValue,
                "Remove",
                "Product not added to cart"
        );

        LogsUtil.info(productName + " added to cart successfully");
        return this;
    }
}
