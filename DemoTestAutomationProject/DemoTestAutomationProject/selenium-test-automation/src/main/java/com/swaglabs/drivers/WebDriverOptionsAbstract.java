package com.swaglabs.drivers;

/**
 * Generic interface for browser options abstraction.
 * Allows each browser to define its own WebDriver options
 * while keeping driver initialization logic consistent.
 */
public interface WebDriverOptionsAbstract<T> {

    /**
     * Builds and returns browser-specific WebDriver options.
     */
    T getOptions();
}
