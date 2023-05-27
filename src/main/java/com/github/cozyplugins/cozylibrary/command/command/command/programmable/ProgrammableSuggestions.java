package com.github.cozyplugins.cozylibrary.command.command.command.programmable;

import com.github.cozyplugins.cozylibrary.command.command.command.ProgrammableCommand;
import com.github.cozyplugins.cozylibrary.command.command.commandtype.ProgrammableCommandType;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandArguments;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandSuggestions;
import com.github.cozyplugins.cozylibrary.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Used to add suggestions to a {@link ProgrammableCommand}</h1>
 */
public interface ProgrammableSuggestions {

    /**
     * <h1>Used to get the command's suggestions</h1>
     * When tab completing a command, these
     * suggestions will be shown.
     * If null, nothing will be suggested.
     *
     * @param arguments The current command arguments.
     * @return The requested command suggestions.
     */
    @Nullable CommandSuggestions getSuggestions(@NotNull User user, @NotNull CommandArguments arguments);
}
