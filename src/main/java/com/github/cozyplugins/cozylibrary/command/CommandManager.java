/*
 * CozyGamesAPI - The api used to interface with the cozy game system.
 * Copyright (C) 2024 Smuddgge
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.cozyplugins.cozylibrary.command;

import com.github.cozyplugins.cozylibrary.command.adapter.BukkitCommandAdapter;
import com.github.cozyplugins.cozylibrary.command.adapter.BukkitCommandHandler;
import com.github.cozyplugins.cozylibrary.command.adapter.CommandTypeAdapter;
import com.github.cozyplugins.cozylibrary.command.command.CommandType;
import com.github.cozyplugins.cozylibrary.command.command.CozyCommand;
import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a command manager.
 * <p>
 * Used to register bukkit and cozy commands
 * with the plugin name.
 */
public class CommandManager {

    private final @NotNull CommandTypeManager commandTypeManager;
    private final @NotNull BukkitCommandHandler bukkitCommandHandler;
    private final @NotNull List<CozyCommand> cozyCommandList;
    private final @NotNull List<Command> commandList;

    /**
     * Used to create a new command manager
     * for a plugin.
     *
     * @param pluginName The plugin's name.
     */
    public CommandManager(@NotNull String pluginName) {
        this.commandTypeManager = new CommandTypeManager();
        this.bukkitCommandHandler = new BukkitCommandHandler(pluginName);
        this.cozyCommandList = new ArrayList<>();
        this.commandList = new ArrayList<>();
    }

    /**
     * The command type manager, used to register command types that can
     * be used within the command configuration if provided.
     *
     * @return The command type manager.
     */
    public @NotNull CommandTypeManager getTypeManager() {
        return this.commandTypeManager;
    }

    public @NotNull List<Command> getCommandList() {
        return this.commandList;
    }

    public @NotNull CommandManager addCommand(@NotNull Command command) {
        this.commandList.add(command);
        return this;
    }

    public @NotNull CommandManager addCommand(@NotNull CozyCommand command) {
        this.cozyCommandList.add(command);
        this.commandList.add(new BukkitCommandAdapter(command));
        return this;
    }

    public @NotNull CommandManager addCommandList(@NotNull List<CozyCommand> commandList) {
        this.cozyCommandList.addAll(commandList);
        commandList.forEach(command -> this.commandList.add(new BukkitCommandAdapter(command)));
        return this;
    }

    public @NotNull CommandManager removeCommand(@NotNull String name) {
        this.cozyCommandList.removeIf(command -> {
            if (command.getName().equalsIgnoreCase(name)) return true;
            if (command.getAliases() == null) return false;
            return command.getAliases().contains(name);
        });
        this.commandList.removeIf(command -> {
            if (command.getName().equalsIgnoreCase(name)) return true;
            return command.getAliases().contains(name);
        });
        return this;
    }

    public @NotNull CommandManager removeCommand(@NotNull Command command) {
        this.removeCommand(command.getName());
        return this;
    }

    public @NotNull CommandManager removeCommand(@NotNull CozyCommand command) {
        this.removeCommand(command.getName());
        return this;
    }

    public @NotNull CommandManager removeCommandTypes() {
        List<String> commandNamesToRemove = new ArrayList<>();

        // Loop though each cozy command.
        for (CozyCommand command : this.cozyCommandList) {
            if (!(command instanceof CommandTypeAdapter)) continue;
            commandNamesToRemove.add(command.getName());
        }

        // Remove the command types.
        commandNamesToRemove.forEach(this::removeCommand);
        return this;
    }

    public @NotNull CommandManager removeAll() {
        this.commandList.clear();
        return this;
    }

    /**
     * Used to register the commands that have been added.
     *
     * @return This instance.
     */
    public @NotNull CommandManager registerCommands() {
        for (Command command : this.commandList) {
            this.bukkitCommandHandler.add(command);
        }

        this.bukkitCommandHandler.registerCommands();
        return this;
    }

    /**
     * Used to unregister the commands that were registered.
     *
     * @return This instance.
     */
    public @NotNull CommandManager unregisterCommands() {
        this.bukkitCommandHandler.unregisterCommands();
        this.bukkitCommandHandler.removeAll();
        return this;
    }
}
