package com.github.cozyplugins.cozylibrary.command.command.commandtype;

import com.github.cozyplugins.cozylibrary.command.command.CommandType;
import com.github.cozyplugins.cozylibrary.command.command.commandtype.programmable.ProgrammableExecutor;
import com.github.cozyplugins.cozylibrary.command.command.commandtype.programmable.ProgrammableSuggestions;
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
 * <h1>Represents a command type</h1>
 * Lets developers create a command type by calling setter methods.
 */
public class ProgrammableCommandType implements CommandType {

    private final @NotNull String identifier;
    private String syntax;
    private String description;
    private final CommandTypePool commandTypePool;
    private ProgrammableSuggestions programmableSuggestions;

    private ProgrammableExecutor onUserExecutor;
    private ProgrammableExecutor onConsoleExecutor;
    private ProgrammableExecutor onPlayerExecutor;
    private ProgrammableExecutor onFakeUserExecutor;

    public ProgrammableCommandType(@NotNull String identifier) {
        this.identifier = identifier;
        this.commandTypePool = new CommandTypePool();
    }

    @Override
    public @NotNull String getIdentifier() {
        return this.identifier;
    }

    @Override
    public @Nullable String getSyntax() {
        return this.syntax;
    }

    @Override
    public @Nullable String getDescription() {
        return this.description;
    }

    @Override
    public @Nullable CommandTypePool getSubCommandTypes() {
        return this.commandTypePool;
    }

    @Override
    public @Nullable CommandSuggestions getSuggestions(@NotNull User user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {
        if (this.programmableSuggestions == null) return null;
        return this.programmableSuggestions.getSuggestions(user, section, arguments);
    }

    @Override
    public @Nullable CommandStatus onUser(@NotNull User user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {
        if (this.onUserExecutor == null) return null;
        return this.onUserExecutor.onUser(user, section, arguments);
    }

    @Override
    public @Nullable CommandStatus onConsole(@NotNull ConsoleUser user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {
        if (this.onConsoleExecutor == null) return null;
        return this.onConsoleExecutor.onUser(user, section, arguments);
    }

    @Override
    public @Nullable CommandStatus onPlayer(@NotNull PlayerUser user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {
        if (this.onPlayerExecutor == null) return null;
        return this.onPlayerExecutor.onUser(user, section, arguments);
    }

    @Override
    public @Nullable CommandStatus onFakeUser(@NotNull FakeUser user, @NotNull ConfigurationSection section, @NotNull CommandArguments arguments) {
        if (this.onFakeUserExecutor == null) return null;
        return this.onFakeUserExecutor.onUser(user, section, arguments);
    }

    public @NotNull ProgrammableCommandType setSyntax(@Nullable String syntax) {
        this.syntax = syntax;
        return this;
    }

    public @NotNull ProgrammableCommandType setDescription(@Nullable String description) {
        this.description = description;
        return this;
    }

    public @NotNull ProgrammableCommandType addSubCommandType(@Nullable CommandType commandType) {
        this.commandTypePool.add(commandType);
        return this;
    }

    public @NotNull ProgrammableCommandType setSuggestions(@NotNull ProgrammableSuggestions suggestions) {
        this.programmableSuggestions = suggestions;
        return this;
    }

    public @NotNull ProgrammableCommandType setUser(@NotNull ProgrammableExecutor executor) {
        this.onUserExecutor = executor;
        return this;
    }

    public @NotNull ProgrammableCommandType setConsole(@NotNull ProgrammableExecutor executor) {
        this.onConsoleExecutor = executor;
        return this;
    }

    public @NotNull ProgrammableCommandType setPlayer(@NotNull ProgrammableExecutor executor) {
        this.onPlayerExecutor = executor;
        return this;
    }

    public @NotNull ProgrammableCommandType setFakeUser(@NotNull ProgrammableExecutor executor) {
        this.onFakeUserExecutor = executor;
        return this;
    }
}
