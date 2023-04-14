package com.github.cozyplugins.cozylibrary.result.types;


import com.github.cozyplugins.cozylibrary.result.Result;
import com.github.cozyplugins.cozylibrary.result.ResultChecker;

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
