package com.github.cozyplugins.cozylibrary.placeholder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the placeholder manager.
 */
public final class CozyPlaceholderManager {

    private static @NotNull List<CozyPlaceholder> placeholderList;

    /**
     * Used to set up the manager.
     */
    public static void setup() {
        CozyPlaceholderManager.placeholderList = new ArrayList<>();
    }

    /**
     * Used to get the list of cozy placeholders
     * registered in the manager.
     *
     * @return The list of placeholders.
     */
    public static List<CozyPlaceholder> get() {
        return CozyPlaceholderManager.placeholderList;
    }

    /**
     * Used to add a placeholder to the manager.
     *
     * @param placeholder The instance of the placeholder.
     */
    public static void add(@NotNull CozyPlaceholder placeholder) {
        CozyPlaceholderManager.placeholderList.add(placeholder);
    }

    /**
     * Used to add a list of placeholders to the manager.
     *
     * @param placeholderList The instance of a placeholder list.
     */
    public static void addAll(@NotNull List<CozyPlaceholder> placeholderList) {
        CozyPlaceholderManager.placeholderList.addAll(placeholderList);
    }

    /**
     * Used to remove a placeholder from the manager.
     *
     * @param placeholder The instance of the placeholder.
     */
    public static void remove(@NotNull CozyPlaceholder placeholder) {
        CozyPlaceholderManager.placeholderList.remove(placeholder);
    }

    /**
     * Used to remove a placeholder given the identifier.
     *
     * @param identifier The placeholders identifier.
     */
    public static void remove(@NotNull String identifier) {
        for (CozyPlaceholder placeholder : CozyPlaceholderManager.placeholderList) {
            if (placeholder.getIdentifier().equals(identifier)) CozyPlaceholderManager.remove(placeholder);
        }
    }

    /**
     * Used to remove all placeholders.
     */
    public static void removeAll() {
        CozyPlaceholderManager.placeholderList = new ArrayList<>();
    }
}
