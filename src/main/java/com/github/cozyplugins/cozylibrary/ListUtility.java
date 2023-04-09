package com.github.cozyplugins.cozylibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Contains useful list utility methods</h1>
 */
public class ListUtility {

    /**
     * <h1>Used to remove the first items from the list</h1>
     *
     * @param list The instance of a list.
     * @param amount The amount of items to remove.
     * @return The new instance of the list.
     */
    public static List<String> removeTheFirst(List<String> list, int amount) {
        List<String> clone = new ArrayList<>(list);

        for (int index = 0; index < amount; index++) {
            if (clone.isEmpty()) continue;
            clone.remove(0);
        }

        return clone;
    }
}
