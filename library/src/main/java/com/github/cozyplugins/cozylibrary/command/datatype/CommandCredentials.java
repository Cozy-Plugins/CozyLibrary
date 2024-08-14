package com.github.cozyplugins.cozylibrary.command.datatype;

import com.github.cozyplugins.cozylibrary.command.command.CozyCommand;
import com.github.cozyplugins.cozylibrary.pool.PermissionPool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Represents a set of command credentials</h1>
 */
public class CommandCredentials {

    private @Nullable CommandAliases aliases;
    private @Nullable String description;
    private @Nullable String syntax;
    private @Nullable PermissionPool permissionPool;
    private @Nullable CommandPool commandPool;

    public @Nullable CommandAliases getAliases() {
        return this.aliases;
    }

    public @Nullable String getDescription() {
        return this.description;
    }

    public @Nullable String getSyntax() {
        return this.syntax;
    }

    public @Nullable PermissionPool getPermissionPool() {
        return this.permissionPool;
    }

    public @Nullable CommandPool getCommandPool() {
        return this.commandPool;
    }

    /**
     * <h1>Used to set the command's aliases</h1>
     *
     * @param aliases The aliases to set.
     * @return This instance.
     */
    public @NotNull CommandCredentials setAliases(@Nullable CommandAliases aliases) {
        this.aliases = aliases;
        return this;
    }

    /**
     * <h1>Used to add a command alias</h1>
     *
     * @param alias A alias to add.
     * @return This instance.
     */
    public @NotNull CommandCredentials addAlias(@NotNull String alias) {
        if (this.aliases == null) this.aliases = new CommandAliases();
        this.aliases.add(alias);
        return this;
    }

    /**
     * <h1>Used to set the command's description</h1>
     *
     * @param description The description.
     * @return This instance.
     */
    public @NotNull CommandCredentials setDescription(@NotNull String description) {
        this.description = description;
        return this;
    }

    /**
     * <h1>Used to set the command syntax</h1>
     *
     * @param syntax The syntax.
     * @return This instance.
     */
    public @NotNull CommandCredentials setSyntax(@NotNull String syntax) {
        this.syntax = syntax;
        return this;
    }

    /**
     * <h1>Used to set the command's permission pool</h1>
     *
     * @param permissionPool The permission pool.
     * @return This instance.
     */
    public @NotNull CommandCredentials setPermissionPool(@NotNull PermissionPool permissionPool) {
        this.permissionPool = permissionPool;
        return this;
    }

    /**
     * <h1>Used to add a permission</h1>
     *
     * @param permission A permission to add.
     * @return This instance.
     */
    public @NotNull CommandCredentials addPermission(@NotNull String permission) {
        if (this.permissionPool == null) this.permissionPool = new PermissionPool();
        this.permissionPool.add(permission);
        return this;
    }

    /**
     * <h1>Used to set the command pool</h1>
     *
     * @param subCommandPool The command pool.
     * @return This instance.
     */
    public @NotNull CommandCredentials setSubCommandPool(@NotNull CommandPool subCommandPool) {
        this.commandPool = subCommandPool;
        return this;
    }

    /**
     * <h1>Used to add a sub command</h1>
     *
     * @param cozyCommand A sub command to add.
     * @return This instance.
     */
    public @NotNull CommandCredentials addSubCommand(@NotNull CozyCommand cozyCommand) {
        if (this.commandPool == null) this.commandPool = new CommandPool();
        this.commandPool.add(cozyCommand);
        return this;
    }
}
