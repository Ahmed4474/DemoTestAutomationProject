package com.swaglabs.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.swaglabs.utils.PropertiesUtils.getPropertyValue;

/**
 * Waits
 *
 * Explicit and Fluent wait utilities.
 * Centralizes all synchronization logic to avoid flaky tests.
 */
public class Waits {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /**
     * Initializes wait handlers using configured explicit wait time.
     *
     * @param driver WebDriver instance
     */
    public Waits(WebDriver driver) {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is null in Waits");
        }

        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(
                        Integer.parseInt(getPropertyValue("explicitWait"))
                )
        );
    }

    /**
     * Waits until element is present in DOM.
     *
     * @param locator element locator
     * @return WebElement
     */
    public WebElement waitForElementPresent(By locator) {
        LogsUtil.info("Waiting for element to be present: " + locator);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(locator)
        );
    }

    /**
     * Waits until element is visible.
     *
     * @param locator element locator
     * @return WebElement
     */
    public WebElement waitForElementVisible(By locator) {
        LogsUtil.info("Waiting for element to be visible: " + locator);
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    /**
     * Waits until element is clickable.
     *
     * @param locator element locator
     * @return WebElement
     */
    public WebElement waitForElementClickable(By locator) {
        LogsUtil.info("Waiting for element to be clickable: " + locator);
        return wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        );
    }

    /**
     * Fluent wait configuration for advanced synchronization scenarios.
     *
     * @return FluentWait<WebDriver>
     */
    public FluentWait<WebDriver> synchronize() {
        return new FluentWait<>(driver)
                .withTimeout(
                        Duration.ofSeconds(
                                Integer.parseInt(getPropertyValue("explicitWait"))
                        )
                )
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(Exception.class);
    }
}
