package com.swaglabs.drivers;

import com.swaglabs.utils.PropertiesUtils;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.Map;

/**
 * Browser-specific WebDriver factory for Microsoft Edge.
 * Handles Edge browser configuration and driver initialization.
 */
public class EdgeFactory extends AbstractDriver
        implements WebDriverOptionsAbstract<EdgeOptions> {

    @Override
    public EdgeOptions getOptions() {
        EdgeOptions edgeOptions = new EdgeOptions();

        // Common browser configuration
        edgeOptions.addArguments("--start-maximized");
        edgeOptions.addArguments("--disable-extensions");
        edgeOptions.addArguments("--disable-infobars");
        edgeOptions.addArguments("--disable-notifications");
        edgeOptions.addArguments("--remote-allow-origins=*");

        // Enable headless mode for non-local execution
        if (!PropertiesUtils.getPropertyValue("executionType").equalsIgnoreCase("local")) {
            edgeOptions.addArguments("--headless=new");
        }

        // Disable browser services and notifications
        Map<String, Object> edgePrefs = Map.of(
                "profile.default_content_setting_values.notifications", 2,
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "autofill.profile_enabled", false
        );

        edgeOptions.setExperimentalOption("prefs", edgePrefs);
        edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

        return edgeOptions;
    }

    @Override
    public WebDriver startDriver() {

        // Local EdgeDriver path configuration
        System.setProperty(
                "webdriver.edge.driver",
                "C:\\Users\\user\\Downloads\\edgedriver_win64\\msedgedriver.exe"
        );

        return new EdgeDriver(getOptions());
    }
}
