package com.swaglabs.pages;

import com.swaglabs.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Page Object representing the order confirmation page.
 * Used to validate successful order completion.
 */
public class ConfirmationPage {

    // Page locators
    private final By confirmationMessage = By.cssSelector(".complete-header");

    // Driver reference
    private GUIDriver driver;

    // Constructor
    public ConfirmationPage(GUIDriver driver) {
        this.driver = driver;
    }

    @Step("Get confirmation message")
    public String getConfirmationMessage() {
        return driver.element().getText(confirmationMessage);
    }

    /**
     * Validates successful order confirmation message.
     */
    @Step("Assert confirmation message: {0}")
    public void assertConfirmationMessage(String expectedMessage) {
        driver.validate().validateEquals(
                getConfirmationMessage(),
                expectedMessage,
                "Confirmation message mismatch"
        );
    }
}
