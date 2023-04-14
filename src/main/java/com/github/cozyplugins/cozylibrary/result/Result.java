package com.github.cozyplugins.cozylibrary.result;

/**
 * <h2>Represents a result type</h2>
 * Used to create complex result matchers
 * that cart be done by comparing to an object
 */
public interface Result {

    /**
     * Used to check a value
     *
     * @return True if correct
     */
    boolean check(Object value);
}
