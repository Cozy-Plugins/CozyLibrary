package com.github.cozyplugins.cozylibrary.commands;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>Represents a collection of suggestions</h1>
 */
public class CommandSuggestions {

    private final @NotNull List<List<String>> suggestions;

    /**
     * Used to create a new collection of suggestions.
     */
    public CommandSuggestions() {
        this.suggestions = new ArrayList<>();
    }

    /**
     * Used to append a list of suggestions.
     *
     * @param suggestions The list of suggestions.
     * @return This instance.
     */
    public CommandSuggestions append(List<String> suggestions) {
        this.suggestions.add(suggestions);
        return this;
    }

    /**
     * Used to append a list of suggestions.
     *
     * @param suggestions The list of suggestions.
     * @return This instance.
     */
    public CommandSuggestions append(String[] suggestions) {
        this.suggestions.add(new ArrayList<>(Arrays.stream(suggestions).toList()));
        return this;
    }

    /**
     * Used to get the list of command suggestions.
     *
     * @return The list of command suggestions.
     */
    public List<List<String>> get() {
        return this.suggestions;
    }

    /**
     * Used to get a list of suggestions.
     *
     * @param index The argument index.
     * @return The list of suggestions.
     */
    public List<String> get(int index) {
        return this.suggestions.get(index);
    }
}
