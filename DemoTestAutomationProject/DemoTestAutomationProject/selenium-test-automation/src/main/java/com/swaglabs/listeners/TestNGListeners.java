package com.swaglabs.listeners;

import com.swaglabs.drivers.GUIDriver;
import com.swaglabs.utils.*;
import org.testng.*;

import java.io.File;

import static com.swaglabs.utils.PropertiesUtils.loadProperties;

/**
 * Central TestNG listener responsible for managing the entire
 * test execution lifecycle including environment setup,
 * reporting, logging, and test artifacts handling.
 */
public class TestNGListeners
        implements IExecutionListener, ITestListener, IInvokedMethodListener {

    // Directories used to store execution artifacts
    File allure_results = new File("test-outputs/allure-results");
    File logs = new File("test-outputs/Logs");
    File screenshots = new File("test-outputs/screenshots");

    /**
     * Executes once before the test suite starts.
     * Used to prepare the execution environment and
     * clean previous execution artifacts.
     */
    @Override
    public void onExecutionStart() {
        LogsUtil.info("Test Execution started");

        // Load framework configuration properties
        loadProperties();

        // Clean old execution data
        FilesUtils.deleteFiles(allure_results);
        FilesUtils.cleanDirectory(logs);
        FilesUtils.cleanDirectory(screenshots);

        // Prepare directories for current execution
        FilesUtils.createDirectory(allure_results);
        FilesUtils.createDirectory(logs);
        FilesUtils.createDirectory(screenshots);
    }

    /**
     * Executes once after the test suite finishes.
     * Responsible for generating and opening the Allure report.
     */
    @Override
    public void onExecutionFinish() {
        LogsUtil.info("Test Execution finished");

        AllureUtils.generateAllureReport();
        String reportName = AllureUtils.renameReport();
        AllureUtils.openReport(reportName);
    }

    /**
     * Executes after each test method invocation.
     * Handles:
     * - Soft assertion validation
     * - Screenshot capturing
     * - Attaching logs to Allure report
     */
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

        if (method.isTestMethod()) {

            // Assert all collected soft assertions
            CustomSoftAssertion.customAssertAll(testResult);

            // Capture screenshot based on test result status
            switch (testResult.getStatus()) {
                case ITestResult.SUCCESS ->
                        ScreenshotsUtils.takeScreenshot(
                                GUIDriver.getInstance(),
                                "passed-" + testResult.getName()
                        );

                case ITestResult.FAILURE ->
                        ScreenshotsUtils.takeScreenshot(
                                GUIDriver.getInstance(),
                                "failed-" + testResult.getName()
                        );

                case ITestResult.SKIP ->
                        ScreenshotsUtils.takeScreenshot(
                                GUIDriver.getInstance(),
                                "skipped-" + testResult.getName()
                        );
            }

            // Attach execution logs to Allure report
            AllureUtils.attacheLogsToAllureReport();
        }
    }

    /**
     * Triggered when a test case passes.
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        LogsUtil.info("Test case", result.getName(), "passed");
    }

    /**
     * Triggered when a test case fails.
     */
    @Override
    public void onTestFailure(ITestResult result) {
        LogsUtil.info("Test case", result.getName(), "failed");
    }

    /**
     * Triggered when a test case is skipped.
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        LogsUtil.info("Test case", result.getName(), "skipped");
    }
}
