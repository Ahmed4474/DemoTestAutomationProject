package com.swaglabs.drivers;

import com.swaglabs.utils.BrowserActions;
import com.swaglabs.utils.ElementActions;
import com.swaglabs.utils.LogsUtil;
import com.swaglabs.utils.Validations;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.fail;

/**
 * Central driver manager for GUI-based test execution.
 * Manages WebDriver lifecycle using ThreadLocal to support
 * parallel execution and provides access to common driver actions.
 */
public class GUIDriver {

    // Thread-safe WebDriver storage to support parallel test execution
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    WebDriver driver;

    public GUIDriver(String browserName) {
        driver = getDriver(browserName).startDriver();
        setDriver(driver);
    }

    /**
     * Returns the current WebDriver instance.
     */
    public static WebDriver getInstance() {
        return driverThreadLocal.get();
    }

    /**
     * Safely retrieves the WebDriver instance or fails the test if not initialized.
     */
    public WebDriver get() {
        if (driverThreadLocal.get() == null) {
            LogsUtil.error("Driver is null");
            fail("Driver is null");
            return null;
        }
        return driverThreadLocal.get();
    }

    /**
     * Factory resolver that returns the appropriate browser driver
     * implementation based on the provided browser name.
     */
    private AbstractDriver getDriver(String browserName) {
        return switch (browserName.toLowerCase()) {
            case "chrome" -> new ChromeFactory();
            case "firefox" -> new FirefoxFactory();
            case "edge" -> new EdgeFactory();
            default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
        };
    }

    private void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    // Fluent accessors for common driver actions and validations
    public ElementActions element() {
        return new ElementActions(get());
    }

    public BrowserActions browser() {
        return new BrowserActions(get());
    }

    public Validations validate() {
        return new Validations(get());
    }
}
