package com.github.cozyplugins.cozylibrary.commands;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>Represents a set of command aliases</h1>
 */
public class CommandAliases {

    private final @NotNull List<String> aliases;

    /**
     * <h1>Used to create a collection of command aliases</h1>
     */
    public CommandAliases() {
        this.aliases = new ArrayList<>();
    }

    /**
     * <h1>Used to append a alias to the list</h1>
     *
     * @param alias The alias to append.
     * @return This instance.
     */
    public CommandAliases append(String alias) {
        this.aliases.add(alias);
        return this;
    }

    /**
     * <h1>Used to append a list of aliases to the list</h1>
     *
     * @param aliases The aliases to append.
     * @return This instance.
     */
    public CommandAliases append(List<String> aliases) {
        this.aliases.addAll(aliases);
        return this;
    }

    /**
     * <h1>Used to append a list of aliases</h1>
     * @param aliases The aliases to append.
     * @return This instance.
     */
    public CommandAliases append(String[] aliases) {
        this.aliases.addAll(new ArrayList<>(Arrays.stream(aliases).toList()));
        return this;
    }

    /**
     * <h1>Used to get the list of aliases</h1>
     *
     * @return The list of aliases.
     */
    public List<String> get() {
        return this.aliases;
    }
}