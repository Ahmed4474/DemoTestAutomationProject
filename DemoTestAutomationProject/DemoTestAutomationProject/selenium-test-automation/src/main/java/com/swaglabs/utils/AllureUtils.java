package com.swaglabs.utils;

import io.qameta.allure.Allure;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * AllureUtils
 *
 * This utility class is responsible for handling all Allure report operations
 * during test execution such as:
 *  - Generating Allure reports
 *  - Renaming reports using timestamp
 *  - Opening reports automatically after execution
 *  - Attaching logs and screenshots to the report
 *
 * The class is designed as a utility class and should not be instantiated.
 */
public class AllureUtils {

    // Path where Allure stores execution results (used as input for report generation)
    public static final String ALLURE_RESULTS_PATH = "test-outputs/allure-results";

    // Path where the generated Allure report will be created
    static String REPORT_PATH = "test-outputs/allure-report";

    /*
     * Absolute path to Allure executable.
     * This path is environment-specific and configured for local execution.
     * Can be moved later to properties for better portability.
     */
    static String ALLURE_PATH =
            "C:" + File.separator + "Users" + File.separator + "user"
                    + File.separator + "Downloads"
                    + File.separator + "allure-2.35.1"
                    + File.separator + "allure-2.35.1"
                    + File.separator + "bin"
                    + File.separator + "allure.bat";

    // Private constructor to prevent object creation
    private AllureUtils() {
        super();
    }

    /**
     * Generates the Allure report using collected execution results.
     * The method detects the operating system to ensure compatibility.
     */
    public static void generateAllureReport() {

        // Detect operating system
        String osName = System.getProperty("os.name").toLowerCase();

        // Generate Allure report
        TerminalUtils.executeCommand(
                ALLURE_PATH,
                "generate",
                ALLURE_RESULTS_PATH,
                "-o",
                REPORT_PATH,
                "--clean",
                "--single-file"
        );

        LogsUtil.info("Allure report generated successfully on " + osName);
    }

    /**
     * Renames the generated Allure report using a timestamp.
     * This prevents overwriting reports between executions.
     *
     * @return the new report file name
     */
    public static String renameReport() {
        File newName = new File("Report_" + TimestampUtils.getTimestamp() + ".html");
        File oldName = new File(REPORT_PATH + File.separator + "index.html");
        FilesUtils.renameFile(oldName, newName);
        return newName.getName();
    }

    /**
     * Opens the Allure report automatically if enabled
     * through framework properties.
     *
     * @param fileName the report file name to be opened
     */
    public static void openReport(String fileName) {

        String reportPath = REPORT_PATH + File.separator + fileName;

        // Check configuration flag before opening report
        if (PropertiesUtils.getPropertyValue("openAllureAutomatically")
                .equalsIgnoreCase("true")) {

            String osName = System.getProperty("os.name").toLowerCase();

            // Open report based on OS
            if (osName.contains("win")) {
                TerminalUtils.executeCommand("cmd.exe", "/c", "start", reportPath);
            } else {
                TerminalUtils.executeCommand("open", reportPath);
            }
        }
    }

    /**
     * Attaches the latest execution log file to Allure report.
     * This helps debugging test failures directly from the report.
     */
    public static void attacheLogsToAllureReport() {
        try {
            File logFile = FilesUtils.getLatestFile(LogsUtil.LOGS_PATH);

            if (logFile == null || !logFile.exists()) {
                LogsUtil.warn("Log file does not exist: " + LogsUtil.LOGS_PATH);
                return;
            }

            Allure.addAttachment(
                    "Execution Logs",
                    Files.readString(Path.of(logFile.getPath()))
            );

            LogsUtil.info("Logs attached to Allure report");

        } catch (Exception e) {
            LogsUtil.error("Failed to attach logs to Allure report: " + e.getMessage());
        }
    }

    /**
     * Attaches a screenshot file to the Allure report.
     *
     * @param screenshotName logical screenshot name
     * @param screenshotPath physical screenshot file path
     */
    public static void attachScreenshotToAllure(String screenshotName, String screenshotPath) {
        try {
            Allure.addAttachment(
                    screenshotName,
                    Files.newInputStream(Path.of(screenshotPath))
            );
        } catch (Exception e) {
            LogsUtil.error("Failed to attach screenshot to Allure report: " + e.getMessage());
        }
    }
}
