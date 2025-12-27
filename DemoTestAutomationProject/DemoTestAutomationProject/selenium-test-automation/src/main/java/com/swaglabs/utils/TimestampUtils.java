package com.swaglabs.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TimestampUtils
 *
 * Utility class for generating formatted timestamps.
 * Used to:
 *  - Name screenshots
 *  - Name reports
 *  - Avoid file overwriting
 */
public class TimestampUtils {

    /**
     * Generates current timestamp in a readable format.
     *
     * Example:
     * 2025-02-28-17-20-59
     *
     * @return formatted timestamp
     */
    public static String getTimestamp() {
        Date date = new Date();
        SimpleDateFormat formatter =
                new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return formatter.format(date);
    }
}
