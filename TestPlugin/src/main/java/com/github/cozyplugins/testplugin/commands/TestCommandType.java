package com.github.cozyplugins.testplugin.commands;

import com.github.cozyplugins.cozylibrary.command.command.CommandType;
import com.github.cozyplugins.cozylibrary.command.command.commandtype.ProgrammableCommandType;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandArguments;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandStatus;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandSuggestions;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandTypePool;
import com.github.cozyplugins.cozylibrary.user.ConsoleUser;
import com.github.cozyplugins.cozylibrary.user.FakeUser;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.cozyplugins.cozylibrary.user.User;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Demonstrates a simple cozy command type</h1>
 * Has two types of subcommands using programmable command types.
 */
public class TestCommandType implements CommandType {

    @Override
    public @NotNull String getIdentifier() {
        return "test_command_type";
    }

    @Override
    public @Nullable String getSyntax() {
        return null;
    }

    @Override
    public @Nullable String getDescription() {
        return null;
    }

    @Override
    public @Nullable CommandTypePool getSubCommandTypes() {
        CommandTypePool commandTypePool = new CommandTypePool();

        commandTypePool.add(new ProgrammableCommandType("1")
                .setDescription("First type of test")
                .setUser((user, section, arguments) -> {
                    user.sendMessage(section.getString("message"));
                    return new CommandStatus();
                })
                .addSubCommandType(new ProgrammableCommandType("response"))
        );

        commandTypePool.add(new ProgrammableCommandType("2")
                .setDescription("First type of test")
                .setUser((user, section, arguments) -> {
                    user.sendMessage(section.getString("message"));
                    return new CommandStatus();
                })
        );

        return commandTypePool;
    }

    @Override
    public @Nullable CommandSuggestions getSuggestions(@NotNull User user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {
        return null;
    }

    @Override
    public @Nullable CommandStatus onUser(@NotNull User user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {
        user.sendMessage(section.getString("message"));
        return new CommandStatus();
    }

    @Override
    public @Nullable CommandStatus onConsole(@NotNull ConsoleUser user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {
        return null;
    }

    @Override
    public @Nullable CommandStatus onPlayer(@NotNull PlayerUser user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {
        return null;
    }

    @Override
    public @Nullable CommandStatus onFakeUser(@NotNull FakeUser user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {
        return null;
    }
}
