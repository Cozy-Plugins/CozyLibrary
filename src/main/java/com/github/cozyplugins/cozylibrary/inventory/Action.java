package com.github.cozyplugins.cozylibrary.inventory;

import com.github.cozyplugins.cozylibrary.pool.Pool;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>Represents a inventory action</h1>
 */
public class Action extends Pool<Integer, Action> {

    /**
     * <h1>Used to create a action</h1>
     *
     * @param slot The slots.
     */
    public Action(Integer... slot) {
        this.addAll(Arrays.stream(slot).toList());
    }
}
