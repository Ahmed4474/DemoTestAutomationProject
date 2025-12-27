package com.swaglabs.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

/**
 * PropertiesUtils
 *
 * Utility class responsible for:
 *  - Loading all .properties files from resources directory
 *  - Merging them into System properties
 *  - Providing a centralized way to access configuration values
 *
 * This allows environment-based and flexible configuration management.
 */
public class PropertiesUtils {

    // Prevent instantiation of utility class
    private PropertiesUtils() {
        super();
    }

    // Base path for all properties files
    public static final String PROPERTIES_PATH = "src/main/resources/";

    /**
     * Loads all .properties files under the resources directory.
     * All properties are merged into System properties
     * so they can be accessed globally across the framework.
     *
     * @return Properties object containing loaded data
     */
    public static Properties loadProperties() {
        try {
            Properties properties = new Properties();

            // Retrieve all .properties files recursively
            Collection<File> propertiesFilesList =
                    FileUtils.listFiles(
                            new File(PROPERTIES_PATH),
                            new String[]{"properties"},
                            true
                    );

            // Load each properties file
            propertiesFilesList.forEach(propertyFile -> {
                try {
                    properties.load(new FileInputStream(propertyFile));
                } catch (IOException ioe) {
                    LogsUtil.error(ioe.getMessage());
                }
            });

            // Merge loaded properties with system properties
            properties.putAll(System.getProperties());
            System.getProperties().putAll(properties);

            LogsUtil.info("Properties files loaded successfully");
            return properties;

        } catch (Exception e) {
            LogsUtil.error("Failed to load properties files: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves the value of a property by key
     * from System properties.
     *
     * @param key property name
     * @return property value
     */
    public static String getPropertyValue(String key) {
        try {
            return System.getProperty(key);
        } catch (Exception e) {
            LogsUtil.error(e.getMessage());
            return "";
        }
    }
}
