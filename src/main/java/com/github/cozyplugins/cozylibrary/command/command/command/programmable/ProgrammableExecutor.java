package com.github.cozyplugins.cozylibrary.command.command.command.programmable;

import com.github.cozyplugins.cozylibrary.command.datatype.CommandArguments;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandStatus;
import com.github.cozyplugins.cozylibrary.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Represents a command executor</h1>
 * Can be used for when a type of user executes a command.
 *
 * @param <T> The type of user.
 */
public interface ProgrammableExecutor<T extends User> {

    /**
     * <h1>Run when a user executes the command</h1>
     *
     * @param user      The instance of the user.
     * @param arguments The commands arguments.
     * @return The command status.
     */
    @Nullable CommandStatus onUser(@NotNull T user, @NotNull CommandArguments arguments);
}
