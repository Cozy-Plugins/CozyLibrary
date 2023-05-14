package com.github.cozyplugins.cozylibrary.command.adapter;

import com.github.cozyplugins.cozylibrary.command.command.CommandType;
import com.github.cozyplugins.cozylibrary.command.command.CozyCommand;
import com.github.cozyplugins.cozylibrary.command.datatype.*;
import com.github.cozyplugins.cozylibrary.pool.PermissionPool;
import com.github.cozyplugins.cozylibrary.user.ConsoleUser;
import com.github.cozyplugins.cozylibrary.user.FakeUser;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.cozyplugins.cozylibrary.user.User;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * <h1>Used to adapt a command type to a command</h1>
 */
public class CommandTypeAdapter implements CozyCommand {

    private final @NotNull ConfigurationSection section;
    private final @NotNull CommandType commandType;

    public CommandTypeAdapter(
            @NotNull ConfigurationSection section,
            @NotNull CommandType commandType) {

        this.section = section;
        this.commandType = commandType;
    }

    @Override
    public @NotNull String getName() {
        return this.section.getString("name");
    }

    @Override
    public @Nullable CommandAliases getAliases() {
        return new CommandAliases().append(this.section.getListString("aliases", new ArrayList<>()));
    }

    @Override
    public @Nullable String getDescription() {
        return this.commandType.getDescription();
    }

    @Override
    public @Nullable String getSyntax() {
        return this.commandType.getSyntax();
    }

    @Override
    public @Nullable PermissionPool getPermissionPool() {
        PermissionPool permissionPool = new PermissionPool();

        // Check if there is a required permission.
        if (this.section.getString("permission", null) != null) {
            permissionPool.append(this.section.getString("permission"));
        }

        // Add potential multiple required permissions.
        permissionPool.append(this.section.getListString("permissions", new ArrayList<>()));

        return permissionPool;
    }

    @Override
    public @Nullable CommandPool getSubCommands() {
        if (this.commandType.getSubCommandTypes() == null) return null;
        CommandPool commandPool = new CommandPool();

        for (CommandType commandType : this.commandType.getSubCommandTypes()) {
            CozyCommand command = new CommandTypeAdapter(
                    this.section.getSection(commandType.getIdentifier()),
                    commandType
            );

            commandPool.add(command);
        }

        return commandPool;
    }

    @Override
    public @Nullable CommandSuggestions getSuggestions(@NotNull User user, @NotNull CommandArguments arguments) {
        return this.commandType.getSuggestions(user, this.section, arguments);
    }

    @Override
    public @Nullable CommandStatus onUser(@NotNull User user, @NotNull CommandArguments arguments) {
        return this.commandType.onUser(user, this.section, arguments);
    }

    @Override
    public @Nullable CommandStatus onPlayerUser(@NotNull PlayerUser user, @NotNull CommandArguments arguments, @NotNull CommandStatus status) {
        return this.commandType.onPlayer(user, this.section, arguments);
    }

    @Override
    public @Nullable CommandStatus onFakeUser(@NotNull FakeUser user, @NotNull CommandArguments arguments, @NotNull CommandStatus status) {
        return this.commandType.onFakeUser(user, this.section, arguments);
    }

    @Override
    public @Nullable CommandStatus onConsoleUser(@NotNull ConsoleUser user, @NotNull CommandArguments arguments, @NotNull CommandStatus status) {
        return this.commandType.onConsole(user, this.section, arguments);
    }
}
