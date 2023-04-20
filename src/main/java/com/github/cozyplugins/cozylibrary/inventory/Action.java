package com.github.cozyplugins.cozylibrary.inventory;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Represents a inventory action</h1>
 */
public class Action {

    private final @NotNull List<Integer> slotList;

    /**
     * <h1>Used to create a action</h1>
     */
    public Action() {
        this.slotList = new ArrayList<>();
    }
}
