package com.swaglabs.pages;

import com.swaglabs.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Page Object representing order overview page
 * before final order confirmation.
 */
public class OverviewPage {

    // Page locators
    private final By finishButton = By.id("finish");

    // Driver reference
    private GUIDriver driver;

    // Constructor
    public OverviewPage(GUIDriver driver) {
        this.driver = driver;
    }

    @Step("Click finish button")
    public ConfirmationPage clickFinishButton() {
        driver.element().click(finishButton);
        return new ConfirmationPage(driver);
    }
}
