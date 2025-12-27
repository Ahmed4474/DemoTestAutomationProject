package com.swaglabs.drivers;

import org.openqa.selenium.WebDriver;

/**
 * Base abstraction for all browser driver factories.
 * This class defines a unified contract for starting WebDriver instances,
 * enabling the framework to support multiple browsers in a scalable way.
 */
public abstract class AbstractDriver {

    /**
     * Starts and returns a WebDriver instance.
     * Each browser factory provides its own implementation.
     */
    public abstract WebDriver startDriver();
}
