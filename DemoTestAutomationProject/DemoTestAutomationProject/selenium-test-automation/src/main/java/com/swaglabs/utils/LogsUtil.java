package com.swaglabs.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * LogsUtil
 *
 * Central logging utility using Log4j2.
 * Automatically detects caller class.
 */
public class LogsUtil {

    public static final String LOGS_PATH = "test-outputs/Logs";

    private LogsUtil() {
        super();
    }

    private static Logger logger() {
        return LogManager.getLogger(
                Thread.currentThread().getStackTrace()[3].getClassName()
        );
    }

    public static void info(String... message) {
        logger().info(String.join(" ", message));
    }

    public static void warn(String... message) {
        logger().warn(String.join(" ", message));
    }

    public static void error(String... message) {
        logger().error(String.join(" ", message));
    }
}
