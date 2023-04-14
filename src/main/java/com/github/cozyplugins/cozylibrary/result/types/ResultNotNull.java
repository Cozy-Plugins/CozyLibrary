package com.github.cozyplugins.cozylibrary.result.types;

import com.github.cozyplugins.cozylibrary.result.Result;
import com.github.cozyplugins.cozylibrary.result.ResultChecker;

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
