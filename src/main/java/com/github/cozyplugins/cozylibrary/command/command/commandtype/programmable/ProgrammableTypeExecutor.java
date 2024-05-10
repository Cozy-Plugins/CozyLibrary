package com.github.cozyplugins.cozylibrary.command.command.commandtype.programmable;

import com.github.cozyplugins.cozylibrary.command.command.CommandType;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandArguments;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandStatus;
import com.github.cozyplugins.cozylibrary.user.User;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Represents a command type executor</h1>
 * Can be used for when a type of user executes a command type.
 *
 * @param <T> The type of user.
 */
public interface ProgrammableTypeExecutor<T extends User> {

    /**
     * Acts as part of {@link CommandType}
     *
     * @param user      The instance of the user executing the command.
     * @param section   The command types configuration section.
     * @param arguments The command arguments.
     * @return The command status.
     */
    @Nullable
    CommandStatus onExecute(@NotNull T user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments);
}
