package com.swaglabs.drivers;

import com.swaglabs.utils.PropertiesUtils;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Browser-specific WebDriver factory for Firefox.
 * Responsible for configuring Firefox options and
 * initializing the FirefoxDriver instance.
 */
public class FirefoxFactory extends AbstractDriver
        implements WebDriverOptionsAbstract<FirefoxOptions> {

    @Override
    public FirefoxOptions getOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        // Common browser configuration
        firefoxOptions.addArguments("--start-maximized");
        firefoxOptions.addArguments("--disable-extensions");
        firefoxOptions.addArguments("--disable-infobars");
        firefoxOptions.addArguments("--disable-notifications");
        firefoxOptions.addArguments("--remote-allow-origins=*");

        // Enable headless mode for non-local execution
        if (!PropertiesUtils.getPropertyValue("executionType").equalsIgnoreCase("local")) {
            firefoxOptions.addArguments("--headless");
        }

        firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        firefoxOptions.setAcceptInsecureCerts(true);

        return firefoxOptions;
    }

    @Override
    public WebDriver startDriver() {
        return new FirefoxDriver(getOptions());
    }
}
