package com.swaglabs.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * ElementActions
 *
 * Wrapper class for all element-level interactions.
 * Handles:
 *  - Waiting for elements
 *  - Scrolling to elements
 *  - Logging and Allure steps
 *
 * This class helps keep Page Objects clean and readable.
 */
public class ElementActions {

    // WebDriver instance
    private WebDriver driver;

    // Explicit wait handler
    private Waits waits;

    /**
     * Constructor initializes WebDriver and Waits utilities.
     *
     * @param driver WebDriver instance
     */
    public ElementActions(WebDriver driver) {
        this.driver = driver;
        this.waits = new Waits(driver);
    }

    /**
     * Sends text to an input field.
     *
     * @param locator element locator
     * @param data    text to be entered
     */
    @Step("Sending data: {data} to the element: {locator}")
    public void type(By locator, String data) {
        waits.waitForElementVisible(locator);
        scrollToElement(locator);
        findElement(locator).sendKeys(data);
        LogsUtil.info("Data entered: ", data, " in field: ", locator.toString());
    }

    /**
     * Clicks on a web element.
     *
     * @param locator element locator
     */
    @Step("Clicking on the element: {locator}")
    public void click(By locator) {
        waits.waitForElementClickable(locator);
        scrollToElement(locator);
        findElement(locator).click();
        LogsUtil.info("Clicked on element: ", locator.toString());
    }

    /**
     * Retrieves visible text from a web element.
     *
     * @param locator element locator
     * @return visible text
     */
    @Step("Getting text from the element: {locator}")
    public String getText(By locator) {
        waits.waitForElementVisible(locator);
        scrollToElement(locator);
        String text = findElement(locator).getText();
        LogsUtil.info("Text retrieved from element: ", locator.toString(), " -> ", text);
        return text;
    }

    /**
     * Retrieves value attribute from input fields.
     *
     * @param locator element locator
     * @return input value
     */
    public String getTextFromInput(By locator) {
        waits.waitForElementVisible(locator);
        scrollToElement(locator);
        String value = findElement(locator).getDomAttribute("value");
        LogsUtil.info("Value retrieved from input: ", locator.toString(), " -> ", value);
        return value;
    }

    /**
     * Finds a web element using provided locator.
     *
     * @param locator element locator
     * @return WebElement
     */
    public WebElement findElement(By locator) {
        LogsUtil.info("Finding element: ", locator.toString());
        return driver.findElement(locator);
    }

    /**
     * Scrolls to a web element using JavaScript.
     *
     * @param locator element locator
     */
    @Step("Scrolling to the element: {locator}")
    public void scrollToElement(By locator) {
        LogsUtil.info("Scrolling to element: ", locator.toString());
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", findElement(locator));
    }
}
