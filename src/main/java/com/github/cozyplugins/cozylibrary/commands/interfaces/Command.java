package com.github.cozyplugins.cozylibrary.commands.interfaces;

import org.jetbrains.annotations.NotNull;

/**
 * <h1>Represents a command</h1>
 */
public interface Command {

    /**
     * <h1>Used to get the commands main name</h1>
     * This name will execute the command when typed in chat.
     *
     * @return The name of the command.
     */
    @NotNull String getName();
}
