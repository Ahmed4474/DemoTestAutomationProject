package com.swaglabs.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * FilesUtils
 *
 * Utility class responsible for all file system operations
 * such as:
 *  - Cleaning directories
 *  - Deleting files
 *  - Renaming files
 *  - Creating directories
 *
 * Used mainly for logs, screenshots, and reports handling.
 */
public class FilesUtils {

    // Prevent instantiation
    private FilesUtils() {
        super();
    }

    /**
     * Retrieves the latest modified file from a given directory.
     *
     * @param folderPath directory path
     * @return latest file
     */
    public static File getLatestFile(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files == null || files.length == 0) {
            LogsUtil.warn("No files found in directory: " + folderPath);
            return null;
        }

        File latestFile = files[0];
        for (File file : files) {
            if (file.lastModified() > latestFile.lastModified()) {
                latestFile = file;
            }
        }
        return latestFile;
    }

    /**
     * Deletes all files inside a directory recursively.
     *
     * @param dirPath directory to clean
     */
    public static void deleteFiles(File dirPath) {
        if (dirPath == null || !dirPath.exists()) {
            LogsUtil.warn("Directory does not exist: " + dirPath);
            return;
        }

        File[] filesList = dirPath.listFiles();
        if (filesList == null) return;

        for (File file : filesList) {
            if (file.isDirectory()) {
                deleteFiles(file);
            } else {
                try {
                    Files.delete(file.toPath());
                } catch (IOException e) {
                    LogsUtil.error("Failed to delete file: " + file.getName());
                }
            }
        }
    }

    /**
     * Deletes directory quietly (used before execution).
     *
     * @param file directory
     */
    public static void cleanDirectory(File file) {
        try {
            FileUtils.deleteQuietly(file);
        } catch (Exception e) {
            LogsUtil.error(e.getMessage());
        }
    }

    /**
     * Renames a file by copying it with a new name
     * and deleting the old one.
     *
     * @param oldName original file
     * @param newName new file name
     */
    public static void renameFile(File oldName, File newName) {
        try {
            File targetDir = oldName.getParentFile();
            File targetFile = new File(targetDir, newName.getName());
            FileUtils.copyFile(oldName, targetFile);
            FileUtils.deleteQuietly(oldName);
            LogsUtil.info("File renamed to: " + newName.getName());
        } catch (Exception e) {
            LogsUtil.error("Failed to rename file: " + e.getMessage());
        }
    }

    /**
     * Creates directory if it does not exist.
     *
     * @param path directory path
     */
    public static void createDirectory(File path) {
        if (!path.exists()) {
            try {
                Files.createDirectories(path.toPath());
                LogsUtil.info("Directory created: " + path);
            } catch (IOException e) {
                LogsUtil.error("Failed to create directory: " + e.getMessage());
            }
        }
    }
}
