package com.github.cozyplugins.cozylibrary.command.command.command;

import com.github.cozyplugins.cozylibrary.command.command.CozyCommand;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandAliases;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandCredentials;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandPool;
import com.github.cozyplugins.cozylibrary.pool.PermissionPool;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Represents a condensed command</h1>
 * Meta values are condensed into a single class.
 */
public interface CondensedCommand extends CozyCommand {

    /**
     * <h1>Used to get the commands credentials</h1>
     *
     * @return The command credentials.
     */
    @Nullable CommandCredentials getCredentials();

    @Override
    default @Nullable CommandAliases getAliases() {
        if (this.getCredentials() == null) return null;
        return this.getCredentials().getAliases();
    }

    @Override
    default @Nullable String getDescription() {
        if (this.getCredentials() == null) return null;
        return this.getCredentials().getDescription();
    }

    @Override
    default @Nullable String getSyntax() {
        if (this.getCredentials() == null) return null;
        return this.getCredentials().getSyntax();
    }

    @Override
    default @Nullable PermissionPool getPermissionPool() {
        if (this.getCredentials() == null) return null;
        return this.getCredentials().getPermissionPool();
    }

    @Override
    default @Nullable CommandPool getSubCommands() {
        if (this.getCredentials() == null) return null;
        return this.getCredentials().getCommandPool();
    }
}
