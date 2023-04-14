package com.github.cozyplugins.cozylibrary.result.types;


import com.github.cozyplugins.cozylibrary.result.Result;
import com.github.cozyplugins.cozylibrary.result.ResultChecker;

/**
 * Used in a {@link ResultChecker} to check if the
 * value is an instance of another class
 */
public class ResultInstanceOf implements Result {

    /**
     * The class used to check the instance of the value
     */
    private final Class object;

    /**
     * Used to create a new {@link ResultInstanceOf}
     *
     * @param object The class
     * @param <T>    The type of class
     */
    public <T> ResultInstanceOf(Class<T> object) {
        this.object = object;
    }

    @Override
    public boolean check(Object value) {
        return object.isAssignableFrom(value.getClass());
    }
}
