package com.swaglabs.pages;

import com.swaglabs.drivers.GUIDriver;
import com.swaglabs.utils.CustomSoftAssertion;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Page Object representing checkout information step.
 * Handles user data entry and validation.
 */
public class InformationPage {

    // Page locators
    private final By firstName = By.id("first-name");
    private final By lastName = By.id("last-name");
    private final By postalCode = By.id("postal-code");
    private final By continueButton = By.id("continue");

    // Driver reference
    private GUIDriver driver;

    // Constructor
    public InformationPage(GUIDriver driver) {
        this.driver = driver;
    }

    /**
     * Fills user information form.
     */
    @Step("Fill information form: First Name: {0}, Last Name: {1}, Postal Code: {2}")
    public InformationPage fillInformationForm(
            String firstName,
            String lastName,
            String postalCode
    ) {
        driver.element().type(this.firstName, firstName);
        driver.element().type(this.lastName, lastName);
        driver.element().type(this.postalCode, postalCode);
        return this;
    }

    @Step("Click continue button")
    public OverviewPage clickContinueButton() {
        driver.element().click(continueButton);
        return new OverviewPage(driver);
    }

    // ---------- Validations ----------

    /**
     * Validates entered checkout information.
     */
    @Step("Assert information page data")
    public InformationPage assertInformationPage(
            String firstName,
            String lastName,
            String postalCode
    ) {
        CustomSoftAssertion.softAssertion.assertEquals(
                driver.element().getTextFromInput(this.firstName),
                firstName
        );

        CustomSoftAssertion.softAssertion.assertEquals(
                driver.element().getTextFromInput(this.lastName),
                lastName
        );

        CustomSoftAssertion.softAssertion.assertEquals(
                driver.element().getTextFromInput(this.postalCode),
                postalCode
        );

        return this;
    }
}
