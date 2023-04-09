package com.github.cozyplugins.cozylibrary.results.types;

import com.github.cozyplugins.cozylibrary.results.Result;
import com.github.cozyplugins.cozylibrary.results.ResultChecker;

/**
 * Used in a {@link ResultChecker} to check
 * if a result is not null
 */
public class ResultNotNull implements Result {

    @Override
    public boolean check(Object value) {
        return value != null;
    }
}
