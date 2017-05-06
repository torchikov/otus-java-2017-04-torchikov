package ru.otus.torchikov;

import ru.otus.torchikov.exceptions.MyAssertException;

import java.util.Objects;

/**
 * Created by Torchikov Sergei on 06.05.2017.
 * Helper class with static methods for test result assertion
 */
public class MyAssertions {
    /**
     * Assert that an object isn't a null. If it is a {@link MyAssertException} is thrown.
     *
     * @param object to check
     */
    public static void assertNotNull(Object object) {
        if (Objects.isNull(object)) {
            throw new MyAssertException("Assertion error! Result is null!");
        }
    }

    /**
     * Assert that expression is true. If it isn't a {@link MyAssertException} is thrown.
     *
     * @param expression to check
     */
    public static void assertTrue(boolean expression) {
        if (!expression) {
            throw new MyAssertException("Assertion error! Result isn't a true");
        }
    }

    /**
     * Assert that expression is false. If isn't a {@link MyAssertException} is thrown.
     *
     * @param expression to check
     */
    public static void assertFalse(boolean expression) {
        if (expression) {
            throw new MyAssertException("Assertion error! Rusult isn't a false");
        }
    }
}
