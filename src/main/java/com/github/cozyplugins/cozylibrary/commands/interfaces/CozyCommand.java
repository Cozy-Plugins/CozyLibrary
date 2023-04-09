package com.github.cozyplugins.cozylibrary.commands.interfaces;

import com.github.cozyplugins.cozylibrary.commands.datatypes.*;
import com.github.cozyplugins.cozylibrary.pools.PermissionPool;
import com.github.cozyplugins.cozylibrary.user.ConsoleUser;
import com.github.cozyplugins.cozylibrary.user.FakeUser;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.cozyplugins.cozylibrary.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Represents a command</h1>
 */
public interface CozyCommand {

    /**
     * <h1>Used to get the commands main name</h1>
     * This name will execute the command when typed in chat.
     *
     * @return The name of the command.
     */
    @NotNull String getName();

    /**
     * <h1>Used to get the commands aliases</h1>
     * When null, there are no command aliases.
     *
     * @return The command's aliases.
     */
    @Nullable CommandAliases getAliases();

    /**
     * <h1>Used to get the commands description</h1>
     * This is an optional method and can return null.
     *
     * @return The command's description.
     */
    @Nullable String getDescription();

    /**
     * <h1>Used to get the commands syntax</h1>
     * The syntax describes the arguments you can
     * pass though the command.
     * For example, /name [player]
     *
     * @return The command's syntax.
     */
    @Nullable String getSyntax();

    /**
     * <h1>Used to get the required permissions</h1>
     * The permissions in the pool must all
     * return true when in context of the user.
     * If null, this command will not required any permissions.
     *
     * @return The pool of permissions.
     */
    @Nullable PermissionPool getPermissionPool();

    /**
     * <h1>Used to get the list of subcommands</h1>
     * If null, there will be no sub commands.
     *
     * @return A command pool.
     */
    @Nullable CommandPool getSubCommands();

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

    /**
     * <h1>Run when a user executes the command</h1>
     *
     * @param user      The instance of the user.
     * @param arguments The commands arguments.
     * @return The command status.
     */
    @Nullable CommandStatus onUser(@NotNull User user, @NotNull CommandArguments arguments);

    /**
     * <h1>Run when a fake user executes the command</h1>
     *
     * @param user      The instance of the user.
     * @param arguments The commands arguments.
     * @return The command status.
     */
    @Nullable CommandStatus onFakeUser(@NotNull FakeUser user, @NotNull CommandArguments arguments);

    /**
     * <h1>Run when a console user executes the command</h1>
     *
     * @param user      The instance of the user.
     * @param arguments The commands arguments.
     * @return The command status.
     */
    @Nullable CommandStatus onConsoleUser(@NotNull ConsoleUser user, @NotNull CommandArguments arguments);

    /**
     * <h1>Run when a player user executes the command</h1>
     *
     * @param user      The instance of the user.
     * @param arguments The commands arguments.
     * @return The command status.
     */
    @Nullable CommandStatus onPlayerUser(@NotNull PlayerUser user, @NotNull CommandArguments arguments);
}
