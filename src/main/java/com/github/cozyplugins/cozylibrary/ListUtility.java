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
     * @param list   The instance of a list.
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

    /**
     * <h1>Used to reduce a list</h1>
     * <li>Matches the pattern to the items in the list.</li>
     * <li>If the pattern doesnt match, it will be removed from the list.</li>
     *
     * @param list The list to reduce.
     * @param pattern The pattern to match.
     * @return The instance of the list.
     */
    public static List<String> reduce(List<String> list, String pattern) {
        List<String> toRemove = new ArrayList<>();

        // Find items that don't match the pattern.
        for (String item : list) {
            if (item.contains(pattern)) continue;
            toRemove.add(item);
        }

        // Remove all the outliers.
        list.removeAll(toRemove);
        return list;
    }
}
