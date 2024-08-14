package com.github.cozyplugins.cozylibrary.command.command.command;

import com.github.cozyplugins.cozylibrary.command.command.CozyCommand;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandArguments;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandStatus;
import com.github.cozyplugins.cozylibrary.user.ConsoleUser;
import com.github.cozyplugins.cozylibrary.user.FakeUser;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Similar to {@link CondensedCommand} but
 * removes the methods
 * {@link CozyCommand#onPlayerUser},
 * {@link CozyCommand#onFakeUser} and
 * {@link CozyCommand#onConsoleUser}
 */
public interface CondensedUserCommand extends CondensedCommand {

    @Override
    default @Nullable CommandStatus onPlayerUser(@NotNull PlayerUser user, @NotNull CommandArguments arguments, @NotNull CommandStatus status) {
        return null;
    }


    @Override
    default @Nullable CommandStatus onConsoleUser(@NotNull ConsoleUser user, @NotNull CommandArguments arguments, @NotNull CommandStatus status) {
        return null;
    }

    @Override
    default @Nullable CommandStatus onFakeUser(@NotNull FakeUser user, @NotNull CommandArguments arguments, @NotNull CommandStatus status) {
        return null;
    }
}
