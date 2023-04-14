package com.github.cozyplugins.cozylibrary.result;

import java.util.Objects;

/**
 * <h2>Represents a result checker</h2>
 * Used to check results to make sure they are correct
 */
public class ResultChecker {

    /**
     * Used to check a {@link Result} type
     *
     * @param value  The value to check
     * @param result The result to check against
     * @return This instance to chain multiple checks
     */
    public ResultChecker expect(Object value, Result result) {
        if (!result.check(value)) {
            throw new RuntimeException(value + " != " + result.getClass().getName());
        }
        return this;
    }

    /**
     * Used to check an exact value
     *
     * @param value The value to check
     * @param match The value to match
     * @return This instance to chain multiple checks
     */
    public ResultChecker expect(Object value, Object match) {
        if (!Objects.equals(value, match)) {
            throw new RuntimeException(value + " != " + match);
        }
        return this;
    }

    /**
     * Used to check if the value is true.
     *
     * @param value The value to check.
     * @return This instance to chain multiple checks.
     */
    public ResultChecker expect(boolean value) {
        if (!value) throw new RuntimeException("Value returned was false");
        return this;
    }
}
