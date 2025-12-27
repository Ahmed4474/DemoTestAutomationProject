package com.swaglabs.utils;

/**
 * TerminalUtils
 *
 * Utility class responsible for executing terminal/command-line commands.
 * Mainly used for:
 *  - Generating Allure reports
 *  - Opening files or reports via OS commands
 *
 * Supports multi-argument command execution.
 */
public class TerminalUtils {

    /**
     * Executes a terminal command using ProcessBuilder.
     * The command is passed as varargs to support flexible usage.
     *
     * Example:
     * executeCommand("allure", "generate", "path", "-o", "output");
     *
     * @param command command and arguments
     */
    public static void executeCommand(String... command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);

            // Redirects process input/output to current console
            processBuilder.inheritIO();

            Process process = processBuilder.start();
            process.waitFor();

            LogsUtil.info("Command executed successfully: ",
                    String.join(" ", command));

        } catch (Exception e) {
            LogsUtil.error("Failed to execute command: " + e.getMessage());
        }
    }
}
