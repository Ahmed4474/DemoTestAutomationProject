package com.swaglabs.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

/**
 * Validations
 *
 * Centralized validation class wrapping TestNG assertions.
 * This class provides:
 *  - Readable validation steps in Allure reports
 *  - Consistent assertion handling across the framework
 *
 * Used inside Page Objects and Test classes.
 */
public class Validations {

    // WebDriver instance
    private WebDriver driver;

    // Browser-level actions for URL and title validations
    private BrowserActions browserActions;

    /**
     * Constructor initializes WebDriver
     * and browser-level action helpers.
     *
     * @param driver WebDriver instance
     */
    public Validations(WebDriver driver) {
        this.driver = driver;
        this.browserActions = new BrowserActions(driver);
    }

    /**
     * Validates that condition is true.
     *
     * @param condition boolean condition
     * @param message   failure message
     */
    @Step("Validate condition is TRUE")
    public void validateTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    /**
     * Validates that condition is false.
     *
     * @param condition boolean condition
     * @param message   failure message
     */
    @Step("Validate condition is FALSE")
    public void validateFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }

    /**
     * Validates that two values are equal.
     *
     * @param actual   actual value
     * @param expected expected value
     * @param message  failure message
     */
    @Step("Validate Equals | Expected: {expected}")
    public void validateEquals(String actual, String expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }

    /**
     * Validates that two values are not equal.
     *
     * @param actual   actual value
     * @param expected expected value
     * @param message  failure message
     */
    @Step("Validate Not Equals | Expected NOT: {expected}")
    public void validateNotEquals(String actual, String expected, String message) {
        Assert.assertNotEquals(actual, expected, message);
    }

    /**
     * Validates current page URL.
     *
     * @param expected expected URL
     */
    @Step("Validate Page URL: {expected}")
    public void validatePageUrl(String expected) {
        Assert.assertEquals(browserActions.getCurrentURL(), expected);
    }

    /**
     * Validates current page title.
     *
     * @param expected expected title
     */
    @Step("Validate Page Title: {expected}")
    public void validatePageTitle(String expected) {
        Assert.assertEquals(browserActions.getPageTitle(), expected);
    }
}
