package com.github.cozyplugins.cozylibrary.command;

import com.github.cozyplugins.cozylibrary.CozyLibrary;
import com.github.cozyplugins.cozylibrary.command.adapter.BukkitCommandAdapter;
import com.github.cozyplugins.cozylibrary.command.adapter.BukkitCommandHandler;
import com.github.cozyplugins.cozylibrary.command.adapter.CommandTypeAdapter;
import com.github.cozyplugins.cozylibrary.command.command.CozyCommand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Represents the command handler</h1>
 * Uses cozy commands.
 */
public class CozyCommandHandler {

    private final @NotNull BukkitCommandHandler bukkitCommandHandler;
    private @NotNull List<CozyCommand> commandList;

    /**
     * <h1>Used to initiate a command handler</h1>
     */
    public CozyCommandHandler() {
        this.bukkitCommandHandler = new BukkitCommandHandler(CozyLibrary.getPluginName());
        this.commandList = new ArrayList<>();
    }

    /**
     * <h1>Used to add a command</h1>
     *
     * @param command The instance of a command.
     * @return This instance.
     */
    public @NotNull CozyCommandHandler add(@NotNull CozyCommand command) {
        this.commandList.add(command);
        return this;
    }

    /**
     * <h1>Used to remove the command</h1>
     *
     * @param command The instance of the command.
     * @return This instance.
     */
    public @NotNull CozyCommandHandler remove(@NotNull CozyCommand command) {
        this.commandList.remove(command);
        return this;
    }

    /**
     * <h1>Used to remove all command types</h1>
     * If the command is a instance of {@link CommandTypeAdapter}
     * it will be removed.
     *
     * @return This instnace.
     */
    public @NotNull CozyCommandHandler removeCommandTypes() {
        List<CozyCommand> toRemove = new ArrayList<>();

        for (CozyCommand cozyCommand : this.commandList) {
            if (cozyCommand instanceof CommandTypeAdapter) toRemove.add(cozyCommand);
        }

        for (CozyCommand cozyCommand : toRemove) {
            this.remove(cozyCommand);
        }

        return this;
    }

    /**
     * <h1>Used to remove all the commands</h1>
     *
     * @return This instance.
     */
    public @NotNull CozyCommandHandler removeAll() {
        this.commandList = new ArrayList<>();
        return this;
    }

    /**
     * <h1>Used to register all the commands</h1>
     *
     * @return This instance.
     */
    public @NotNull CozyCommandHandler registerCommands() {
        for (CozyCommand command : this.commandList) {
            this.bukkitCommandHandler.add(new BukkitCommandAdapter(command));
        }

        this.bukkitCommandHandler.registerCommands();
        return this;
    }

    /**
     * <h1>Used to unregister all the registered commands</h1>
     *
     * @return This instance.
     */
    public @NotNull CozyCommandHandler unregisterCommands() {
        this.bukkitCommandHandler.unregisterCommands();
        this.bukkitCommandHandler.removeAll();
        return this;
    }
}
