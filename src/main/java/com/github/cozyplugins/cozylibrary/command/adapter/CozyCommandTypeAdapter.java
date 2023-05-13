package com.github.cozyplugins.cozylibrary.command.adapter;

import com.github.cozyplugins.cozylibrary.command.command.CozyCommand;
import com.github.cozyplugins.cozylibrary.command.command.CozyCommandType;
import com.github.cozyplugins.cozylibrary.command.datatype.*;
import com.github.cozyplugins.cozylibrary.pool.PermissionPool;
import com.github.cozyplugins.cozylibrary.user.ConsoleUser;
import com.github.cozyplugins.cozylibrary.user.FakeUser;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.cozyplugins.cozylibrary.user.User;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CozyCommandTypeAdapter implements CozyCommand {

    private final @NotNull ConfigurationSection section;
    private final @NotNull CozyCommandType commandType;

    public CozyCommandTypeAdapter(
            @NotNull ConfigurationSection section,
            @NotNull CozyCommandType commandType) {
        this.commandType = commandType;
    }

    @Override
    public @NotNull String getName() {
        return ;
    }

    @Override
    public @Nullable CommandAliases getAliases() {
        return null;
    }

    @Override
    public @Nullable String getDescription() {
        return null;
    }

    @Override
    public @Nullable String getSyntax() {
        return null;
    }

    @Override
    public @Nullable PermissionPool getPermissionPool() {
        return null;
    }

    @Override
    public @Nullable CommandPool getSubCommands() {
        return null;
    }

    @Override
    public @Nullable CommandSuggestions getSuggestions(@NotNull User user, @NotNull CommandArguments arguments) {
        return null;
    }

    @Override
    public @Nullable CommandStatus onUser(@NotNull User user, @NotNull CommandArguments arguments) {
        return null;
    }

    @Override
    public @Nullable CommandStatus onPlayerUser(@NotNull PlayerUser user, @NotNull CommandArguments arguments, @NotNull CommandStatus status) {
        return null;
    }

    @Override
    public @Nullable CommandStatus onFakeUser(@NotNull FakeUser user, @NotNull CommandArguments arguments, @NotNull CommandStatus status) {
        return null;
    }

    @Override
    public @Nullable CommandStatus onConsoleUser(@NotNull ConsoleUser user, @NotNull CommandArguments arguments, @NotNull CommandStatus status) {
        return null;
    }
}
