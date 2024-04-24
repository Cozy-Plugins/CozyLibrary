package com.github.cozyplugins.testplugin.commands;

import com.github.cozyplugins.cozylibrary.command.command.command.CondensedUserCommand;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandArguments;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandCredentials;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandStatus;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandSuggestions;
import com.github.cozyplugins.cozylibrary.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Demonstrates a simple cozy command</h1>
 */
public class HelloWorldCommand implements CondensedUserCommand {

    @Override
    public @NotNull String getName() {
        return "test_helloworld";
    }

    @Override
    public @Nullable CommandSuggestions getSuggestions(@NotNull User user, @NotNull CommandArguments arguments) {
        return null;
    }

    @Override
    public @Nullable CommandStatus onUser(@NotNull User user, @NotNull CommandArguments arguments) {
        user.sendMessage("Hello world!");
        return new CommandStatus();
    }

    @Override
    public @Nullable CommandCredentials getCredentials() {
        return new CommandCredentials()
                .setDescription("Responds with hello world.")
                .setSyntax("/[name]");
    }
}
