package com.github.cozyplugins.cozylibrary.commands.interfaces;

import com.github.cozyplugins.cozylibrary.commands.CommandArguments;
import com.github.cozyplugins.cozylibrary.commands.CommandStatus;
import com.github.cozyplugins.cozylibrary.commands.CommandSuggestions;
import com.github.cozyplugins.cozylibrary.user.ConsoleUser;
import com.github.cozyplugins.cozylibrary.user.FakeUser;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.cozyplugins.cozylibrary.user.User;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Represents a command type</h1>
 * The server operator can use the command types
 * in the plugin to customise the commands.
 * A command type represents how the command
 * will function.
 */
public interface CommandType {

    /**
     * <h1>Used to get the commands type identifier</h1>
     * This identifier will be matched in the configuration.
     *
     * @return The commands name.
     */
    @NotNull String getIdentifier();

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
    @NotNull String getSyntax();

    /**
     * <h1>Used to get the command's suggestions</h1>
     * When tab completing a command, these
     * suggestions will be shown.
     * If null, nothing will be suggested.
     *
     * @param arguments The current command arguments.
     * @return The requested command suggestions.
     */
    @Nullable CommandSuggestions getSuggestions(CommandArguments arguments);

    /**
     * <h1>Ran when any user executes this command</h1>
     * If this returns null, it will attempt to run as
     * the specific type of user.
     *
     * @param user      The user who executed the command.
     * @param section   The configuration section of the command.
     * @param arguments The commands arguments.
     * @return The command status.
     */
    @Nullable CommandStatus onUser(User user, ConfigurationSection section, CommandArguments arguments);

    /**
     * <h1>Ran when the console executes this command</h1>
     * If this and {@link CommandType#onUser}  is null, it is not a console command.
     *
     * @param user      The user who executed the command.
     * @param section   The configuration section of the command.
     * @param arguments The commands arguments.
     * @return The command status.
     */
    @Nullable CommandStatus onConsole(ConsoleUser user, ConfigurationSection section, CommandArguments arguments);

    /**
     * <h1>Ran when the console executes this command</h1>
     * If this and {@link CommandType#onUser}  is null, this is not a player command.
     *
     * @param user      The user who executed the command.
     * @param section   The configuration section of the command.
     * @param arguments The commands arguments.
     * @return The command status.
     */
    @Nullable CommandStatus onPlayer(PlayerUser user, ConfigurationSection section, CommandArguments arguments);

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
    @Nullable CommandStatus onFakeUser(FakeUser user, ConfigurationSection section, CommandArguments arguments);
}
