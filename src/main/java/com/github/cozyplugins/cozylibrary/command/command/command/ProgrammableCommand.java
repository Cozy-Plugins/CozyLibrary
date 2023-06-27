package com.github.cozyplugins.cozylibrary.command.command.command;

import com.github.cozyplugins.cozylibrary.command.command.CozyCommand;
import com.github.cozyplugins.cozylibrary.command.command.command.programmable.ProgrammableExecutor;
import com.github.cozyplugins.cozylibrary.command.command.command.programmable.ProgrammableSuggestions;
import com.github.cozyplugins.cozylibrary.command.datatype.*;
import com.github.cozyplugins.cozylibrary.pool.PermissionPool;
import com.github.cozyplugins.cozylibrary.user.ConsoleUser;
import com.github.cozyplugins.cozylibrary.user.FakeUser;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.cozyplugins.cozylibrary.user.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Represents a programmable command</h1>
 */
public class ProgrammableCommand implements CozyCommand {

    private final @NotNull String name;
    private final CommandAliases aliases;
    private String description;
    private String syntax;
    private final PermissionPool permissionPool;
    private final CommandPool commandPool;

    private ProgrammableSuggestions programmableSuggestions;
    private ProgrammableExecutor<User> onUserExecutor;
    private ProgrammableExecutor<PlayerUser> onPlayerExecutor;
    private ProgrammableExecutor<FakeUser> onFakeUserExecutor;
    private ProgrammableExecutor<ConsoleUser> onConsoleExecutor;

    /**
     * <h1>Used to create a programmable command</h1>
     *
     * @param name The name of the command.
     */
    public ProgrammableCommand(@NotNull String name) {
        this.name = name;
        this.aliases = new CommandAliases();
        this.permissionPool = new PermissionPool();
        this.commandPool = new CommandPool();
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @Nullable CommandAliases getAliases() {
        return this.aliases;
    }

    @Override
    public @Nullable String getDescription() {
        return this.description;
    }

    @Override
    public @Nullable String getSyntax() {
        return this.syntax;
    }

    @Override
    public @Nullable PermissionPool getPermissionPool() {
        return this.permissionPool;
    }

    @Override
    public @Nullable CommandPool getSubCommands() {
        return this.commandPool;
    }

    @Override
    public @Nullable CommandSuggestions getSuggestions(@NotNull User user, @NotNull CommandArguments arguments) {
        return this.programmableSuggestions.getSuggestions(user, arguments);
    }

    @Override
    public @Nullable CommandStatus onUser(@NotNull User user, @NotNull CommandArguments arguments) {
        if (this.onUserExecutor == null) return null;
        return this.onUserExecutor.onUser(user, arguments);
    }

    @Override
    public @Nullable CommandStatus onPlayerUser(@NotNull PlayerUser user, @NotNull CommandArguments arguments, @NotNull CommandStatus status) {
        if (this.onPlayerExecutor == null) return null;
        return this.onPlayerExecutor.onUser(user, arguments);
    }

    @Override
    public @Nullable CommandStatus onFakeUser(@NotNull FakeUser user, @NotNull CommandArguments arguments, @NotNull CommandStatus status) {
        if (this.onFakeUserExecutor == null) return null;
        return this.onFakeUserExecutor.onUser(user, arguments);
    }

    @Override
    public @Nullable CommandStatus onConsoleUser(@NotNull ConsoleUser user, @NotNull CommandArguments arguments, @NotNull CommandStatus status) {
        if (this.onConsoleExecutor == null) return null;
        return this.onConsoleExecutor.onUser(user, arguments);
    }

    public @NotNull ProgrammableCommand addAliases(@NotNull String alias) {
        this.aliases.add(alias);
        return this;
    }

    public @NotNull ProgrammableCommand setDescription(@NotNull String description) {
        this.description = description;
        return this;
    }

    public @NotNull ProgrammableCommand setSyntax(@NotNull String syntax) {
        this.syntax = syntax;
        return this;
    }

    public @NotNull ProgrammableCommand addPermission(@NotNull String permission) {
        this.permissionPool.add(permission);
        return this;
    }

    public @NotNull ProgrammableCommand addSubCommand(@NotNull CozyCommand command) {
        this.commandPool.add(command);
        return this;
    }

    public @NotNull ProgrammableCommand setSuggestions(@NotNull ProgrammableSuggestions suggestions) {
        this.programmableSuggestions = suggestions;
        return this;
    }

    public @NotNull ProgrammableCommand setUser(@NotNull ProgrammableExecutor<User> executor) {
        this.onUserExecutor = executor;
        return this;
    }

    public @NotNull ProgrammableCommand setConsole(@NotNull ProgrammableExecutor<ConsoleUser> executor) {
        this.onConsoleExecutor = executor;
        return this;
    }

    public @NotNull ProgrammableCommand setPlayer(@NotNull ProgrammableExecutor<PlayerUser> executor) {
        this.onPlayerExecutor = executor;
        return this;
    }

    public @NotNull ProgrammableCommand setFakeUser(@NotNull ProgrammableExecutor<FakeUser> executor) {
        this.onFakeUserExecutor = executor;
        return this;
    }
}
