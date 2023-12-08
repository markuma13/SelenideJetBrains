package com.example.selenidejetbrains;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AssertionHelper {
    public static void assertEqualsWithMessage(Object expected, Object actual, String message) {
        try {
            assertEquals(expected, actual, message);
        } catch (AssertionError e) {
            System.err.println("Assertion failed: " + e.getMessage());
            throw e;
        }
    }

    public static void assertTrueWithMessage(boolean condition, String message) {
        assertTrue(condition, message);
    }

    public static void assertNotEmptyWithMessage(Collection<?> collection, String message) {
        assertFalse(collection.isEmpty(), message);
    }
}
