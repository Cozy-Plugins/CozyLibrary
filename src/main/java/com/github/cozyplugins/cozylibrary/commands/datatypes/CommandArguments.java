package com.github.cozyplugins.cozylibrary.commands.datatypes;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * <h1>Represents a command arguments</h1>
 */
public class CommandArguments {

    private final @NotNull String name;
    private final @NotNull List<String> subCommandNameList;
    private final @NotNull List<String> arguments;


    /**
     * Creates a command argument class.
     *
     * @param name The name of the command.
     * @param subCommandNameList The names of the sub commands.
     * @param arguments The command's arguments.
     */
    public CommandArguments(@NotNull String name, @NotNull List<String> subCommandNameList, @NotNull List<String> arguments) {
        this.name = name;
        this.subCommandNameList = subCommandNameList;
        this.arguments = arguments;
    }

    public CommandArguments() {

    }

    /**
     * Retrieves the command name.
     *
     * @return The command name.
     */
    public @NotNull String getCommandName() {
        return this.name;
    }

    /**
     * Retrieves the sub command name list.
     *
     * @return The sub command name list.
     */
    public @NotNull List<String> getSubCommandNameList() {
        return this.subCommandNameList;
    }

    /**
     * Retrieves the command's arguments.
     *
     * @return The command's arguments.
     */
    public @NotNull List<String> getArguments() {
        return this.arguments;
    }
}
