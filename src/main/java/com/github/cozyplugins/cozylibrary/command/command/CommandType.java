package com.github.cozyplugins.cozylibrary.command.command;

import com.github.cozyplugins.cozylibrary.command.datatype.CommandArguments;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandStatus;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandSuggestions;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandTypePool;
import com.github.cozyplugins.cozylibrary.user.ConsoleUser;
import com.github.cozyplugins.cozylibrary.user.FakeUser;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.cozyplugins.cozylibrary.user.User;
import com.github.squishylib.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Represents a command type</h1>
 * The server operator can use the command types
 * in the plugin to customize the commands.
 * A command type represents how the command
 * will function.
 *
 * <h1>Configuration keys</h1>
 * <li>type ◆ Returns the identifier</li>
 * <li>name ◆ Returns the command name</li>
 * <li>aliases ◆ Returns the command aliases</li>
 * <li>permission ◆ Returns the required permission</li>
 * <li>permissions ◆ Returns a list of required permissions</li>
 */
public interface CommandType {

    /**
     * <h1>Used to get the commands type identifier</h1>
     * This identifier will be matched in the configuration.
     *
     * @return The command name.
     */
    @NotNull
    String getIdentifier();

    /**
     * <h1>Used to get the commands syntax</h1>
     * When the incorrect arguments are given it
     * will suggest the syntax to the player.
     * <ul>
     *     <li>[name] will be replaced with the commands name</li>
     * </ul>
     *
     * @return The command syntax.
     */
    @Nullable
    String getSyntax();


    /**
     * <h1>Used to get the description of the command type</h1>
     *
     * @return The description.
     */
    @Nullable
    String getDescription();

    /**
     * <h1>Used to get the sub command types</h1>
     *
     * @return The sub command types.
     */
    @Nullable
    CommandTypePool getSubCommandTypes();

    /**
     * <h1>Used to get the command's suggestions</h1>
     * When tab completing a command, these
     * suggestions will be shown.
     * If null, nothing will be suggested.
     *
     * @param arguments The current command arguments.
     * @return The requested command suggestions.
     */
    @Nullable
    CommandSuggestions getSuggestions(@NotNull User user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments);

    /**
     * <h1>Ran when any user executes this command</h1>
     *
     * @param user      The user who executed the command.
     * @param section   The configuration section of the command.
     * @param arguments The commands arguments.
     * @return The command status.
     */
    @Nullable
    CommandStatus onUser(@NotNull User user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments);

    /**
     * <h1>Ran when the console executes this command</h1>
     * If this and {@link CommandType#onUser}  is null, this is not a player command.
     *
     * @param user      The user who executed the command.
     * @param section   The configuration section of the command.
     * @param arguments The commands arguments.
     * @return The command status.
     */
    @Nullable
    CommandStatus onPlayer(@NotNull PlayerUser user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments);

    /**
     * <h1>Ran when a fake user executes this command</h1>
     * If this and {@link CommandType#onUser}
     * return null, this is not a fake user command.
     *
     * @param user      The user who executed the command.
     * @param section   The configuration section of the command.
     * @param arguments The commands arguments.
     * @return The command status.
     */
    @Nullable
    CommandStatus onFakeUser(@NotNull FakeUser user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments);

    /**
     * <h1>Ran when the console executes this command</h1>
     * If this and {@link CommandType#onUser}  is null, it is not a console command.
     *
     * @param user      The user who executed the command.
     * @param section   The configuration section of the command.
     * @param arguments The commands arguments.
     * @return The command status.
     */
    @Nullable
    CommandStatus onConsole(@NotNull ConsoleUser user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments);
}
