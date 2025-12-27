package com.swaglabs.utils;

import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

/**
 * CustomSoftAssertion
 *
 * Custom implementation of TestNG SoftAssert.
 * This class ensures:
 *  - All soft assertions are collected during test execution
 *  - Test status is marked as FAILED if any assertion fails
 *  - Soft assertions are reset after each test to avoid leakage
 */
public class CustomSoftAssertion extends SoftAssert {

    /*
     * Shared SoftAssert instance used during test execution.
     * It is reinitialized after each test.
     */
    public static CustomSoftAssertion softAssertion = new CustomSoftAssertion();

    /**
     * Executes all collected soft assertions.
     * If any assertion fails, the test result is marked as FAILURE
     * and the exception is attached to the TestNG result.
     *
     * @param result current TestNG test result
     */
    public static void customAssertAll(ITestResult result) {
        try {
            softAssertion.assertAll("Custom Soft Assertion Failed");
        } catch (AssertionError e) {
            LogsUtil.error("Custom Soft Assertion Failed: " + e.getMessage());
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(e);
        } finally {
            reInitializeSoftAssert();
        }
    }

    /**
     * Reinitializes the SoftAssert instance.
     * This prevents assertion data from one test
     * affecting subsequent tests.
     */
    private static void reInitializeSoftAssert() {
        softAssertion = new CustomSoftAssertion();
    }
}
