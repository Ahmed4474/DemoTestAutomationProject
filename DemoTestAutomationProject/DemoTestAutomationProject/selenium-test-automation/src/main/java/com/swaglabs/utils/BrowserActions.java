package com.swaglabs.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

/**
 * BrowserActions
 *
 * This class acts as a wrapper for browser-level operations.
 * It helps keep test and page classes clean by separating
 * browser logic from test logic.
 */
public class BrowserActions {

    // WebDriver instance associated with the current test
    private WebDriver driver;

    /**
     * Constructor to initialize browser actions with WebDriver instance.
     *
     * @param driver WebDriver instance
     */
    public BrowserActions(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Navigates the browser to a specific URL.
     *
     * @param url target URL
     */
    @Step("Navigating to URL: {url}")
    public void navigateToURL(String url) {
        driver.get(url);
        LogsUtil.info("Navigated to URL: ", url);
    }

    /**
     * Returns the current browser URL.
     *
     * @return current URL
     */
    @Step("Getting current URL")
    public String getCurrentURL() {
        LogsUtil.info("Current URL: ", driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }

    /**
     * Returns the current page title.
     *
     * @return page title
     */
    @Step("Getting page title")
    public String getPageTitle() {
        LogsUtil.info("Page title: ", driver.getTitle());
        return driver.getTitle();
    }

    /**
     * Refreshes the current browser page.
     */
    @Step("Refreshing the page")
    public void refreshPage() {
        LogsUtil.info("Refreshing the page");
        driver.navigate().refresh();
    }

    /**
     * Closes the browser and terminates the WebDriver session.
     */
    @Step("Closing the browser")
    public void closeBrowser() {
        LogsUtil.info("Closing the browser");
        if (driver != null) {
            driver.quit();
        }
    }
}
