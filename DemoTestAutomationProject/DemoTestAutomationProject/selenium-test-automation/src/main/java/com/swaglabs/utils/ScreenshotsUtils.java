package com.swaglabs.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

/**
 * ScreenshotsUtils
 *
 * Utility class for capturing screenshots
 * and attaching them to Allure reports.
 */
public class ScreenshotsUtils {

    public static final String SCREENSHOTS_PATH = "test-outputs/screenshots/";

    private ScreenshotsUtils() {
        super();
    }

    /**
     * Takes screenshot and attaches it to Allure report.
     *
     * @param driver WebDriver instance
     * @param screenshotName name of screenshot
     */
    public static void takeScreenshot(WebDriver driver, String screenshotName) {
        try {
            File screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            File screenshotFile = new File(
                    SCREENSHOTS_PATH + screenshotName + "_" +
                            TimestampUtils.getTimestamp() + ".png"
            );

            FileUtils.copyFile(screenshot, screenshotFile);
            AllureUtils.attachScreenshotToAllure(
                    screenshotName, screenshotFile.getPath()
            );

        } catch (Exception e) {
            LogsUtil.error("Failed to take screenshot: " + e.getMessage());
        }
    }
}
