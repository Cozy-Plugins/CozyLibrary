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
 * @param <R> This type of class.
 */
public class Pool<T, R extends Pool<T, R>> extends ArrayList<T> {

    /**
     * <h1>Used to append a item to the pool</h1>
     *
     * @param item The item to append.
     * @return This instance.
     */
    public R append(T item) {
        this.add(item);
        return (R) this;
    }

    /**
     * <h1>Used to append a list to the list</h1>
     *
     * @param list The list to append.
     * @return This instance.
     */
    public R append(List<T> list) {
        this.addAll(list);
        return (R) this;
    }

    /**
     * <h1>Used to append a list of items</h1>
     *
     * @param list The list to append.
     * @return This instance.
     */
    public R append(T[] list) {
        this.addAll(new ArrayList<>(Arrays.stream(list).toList()));
        return (R) this;
    }
}
