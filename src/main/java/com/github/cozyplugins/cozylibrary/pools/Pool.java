package com.github.cozyplugins.cozylibrary.pools;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>Represents a pool of values</h1>
 * A pool extends the functionality of a {@link List}
 *
 * @param <T> The type that is stored in the pool.
 */
public class Pool<T> {

    private final @NotNull List<T> list;

    /**
     * <h1>Used to initiate a pool</h1>
     */
    public Pool() {
        this.list = new ArrayList<>();
    }

    /**
     * <h1>Used to append a item to the pool</h1>
     *
     * @param item The item to append.
     * @return This instance.
     */
    public Pool<T> append(T item) {
        this.list.add(item);
        return this;
    }

    /**
     * <h1>Used to append a list to the list</h1>
     *
     * @param list The list to append.
     * @return This instance.
     */
    public Pool<T> append(List<T> list) {
        this.list.addAll(list);
        return this;
    }

    /**
     * <h1>Used to append a list of items</h1>
     *
     * @param list The list to append.
     * @return This instance.
     */
    public Pool<T> append(T[] list) {
        this.list.addAll(new ArrayList<>(Arrays.stream(list).toList()));
        return this;
    }

    /**
     * <h1>Used to get the list</h1>
     *
     * @return The list.
     */
    public List<T> get() {
        return this.list;
    }
}
