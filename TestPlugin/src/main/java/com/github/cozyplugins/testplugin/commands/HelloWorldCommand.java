package com.github.cozyplugins.testplugin.commands;

import com.github.cozyplugins.cozylibrary.command.datatypes.*;
import com.github.cozyplugins.cozylibrary.command.interfaces.CozyCommand;
import com.github.cozyplugins.cozylibrary.pool.PermissionPool;
import com.github.cozyplugins.cozylibrary.user.ConsoleUser;
import com.github.cozyplugins.cozylibrary.user.FakeUser;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.cozyplugins.cozylibrary.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HelloWorldCommand implements CozyCommand {

    @Override
    public @NotNull String getName() {
        return "helloworld";
    }

    @Override
    public @Nullable CommandAliases getAliases() {
        return new CommandAliases().append("hw");
    }

    @Override
    public @Nullable String getDescription() {
        return "Sends hello back to the user";
    }

    @Override
    public @Nullable String getSyntax() {
        return "/[name]";
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
        user.sendMessage("Hello!");
        return new CommandStatus();
    }

    @Override
    public @Nullable CommandStatus onFakeUser(@NotNull FakeUser user, @NotNull CommandArguments arguments, @NotNull CommandStatus status) {
        return null;
    }

    @Override
    public @Nullable CommandStatus onConsoleUser(@NotNull ConsoleUser user, @NotNull CommandArguments arguments, @NotNull CommandStatus status) {
        return null;
    }

    @Override
    public @Nullable CommandStatus onPlayerUser(@NotNull PlayerUser user, @NotNull CommandArguments arguments, @NotNull CommandStatus status) {
        return null;
    }
}
