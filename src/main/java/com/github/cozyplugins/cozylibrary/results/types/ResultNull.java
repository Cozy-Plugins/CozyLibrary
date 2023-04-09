package com.github.cozyplugins.cozylibrary.results.types;


import com.github.cozyplugins.cozylibrary.results.Result;
import com.github.cozyplugins.cozylibrary.results.ResultChecker;

/**
 * Used in a {@link ResultChecker} to check
 * if a result is null
 */
public class ResultNull implements Result {

    @Override
    public boolean check(Object value) {
        return value == null;
    }
}
