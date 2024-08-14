package com.github.cozyplugins.cozylibrary.pool;

/**
 * <h1>Represents a distinct pool</h1>
 * <li>Every value is different.</li>
 * <li>When adding values, it will check if it already exists</li>
 *
 * @param <T> The type that is stored in the pool.
 * @param <R> This type of class.
 */
public class DistinctPool<T, R extends Pool<T, R>> extends Pool<T, R> {

    @Override
    public boolean add(T t) {
        if (this.contains(t)) return false;
        return super.add(t);
    }
}
