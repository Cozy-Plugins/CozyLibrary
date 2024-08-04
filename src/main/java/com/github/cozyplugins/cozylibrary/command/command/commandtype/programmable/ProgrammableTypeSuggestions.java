package com.github.cozyplugins.cozylibrary.command.command.commandtype.programmable;

import com.github.cozyplugins.cozylibrary.command.command.CommandType;
import com.github.cozyplugins.cozylibrary.command.command.commandtype.ProgrammableCommandType;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandArguments;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandSuggestions;
import com.github.cozyplugins.cozylibrary.user.User;
import com.github.squishylib.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Used to add suggestions to a {@link ProgrammableCommandType}</h1>
 */
public interface ProgrammableTypeSuggestions {

    /**
     * Acts as part of {@link CommandType}
     *
     * @param user      The user who is tab completing.
     * @param section   The configuration section.
     * @param arguments The command arguments.
     * @return The command suggestions.
     */
    @Nullable
    CommandSuggestions getSuggestions(@NotNull User user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments);
}
