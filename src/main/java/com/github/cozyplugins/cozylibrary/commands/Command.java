package com.github.cozyplugins.cozylibrary.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * <h1>Represents a command</h1>
 */
public interface Command {

    /**
     * <h1>Used to get the commands name</h1>
     *
     * @return The commands name.
     */
    @NotNull String getName();

    /**
     * <h1>Used to get a commands aliases</h1>
     * When set to null, the command will have no aliases.
     *
     * @return The command's aliases.
     */
    @Nullable CommandAliases getAliases();


}
