package com.github.cozyplugins.cozylibrary.command.adapter;

import com.github.cozyplugins.cozylibrary.ConsoleManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Represents a command handler</h1>
 * A command handler for bukkit commands.
 * Only supports use for {@link Command}
 */
public class BukkitCommandHandler {

    private final @NotNull String pluginName;
    private @NotNull List<Command> commandList;

    /**
     * <h1>Used to create a bukkit command handler</h1>
     *
     * @param pluginName The plugins name.
     */
    public BukkitCommandHandler(@NotNull String pluginName) {
        this.pluginName = pluginName;
        this.commandList = new ArrayList<>();
    }

    /**
     * <h1>Used to get the bukkit command map</h1>
     * If an error occurs it will return null.
     *
     * @return The instance of the bukkit command map.
     */
    public @Nullable CommandMap getCommandMap() {
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            return (CommandMap) field.get(Bukkit.getServer());

        } catch (NoSuchFieldException | IllegalAccessException exception) {
            ConsoleManager.error("Could not find the bukkit command map.");
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * <h1>Used to add a command to the list</h1>
     *
     * @param command The instance of a bukkit command.
     * @return This instance.
     */
    public @NotNull BukkitCommandHandler add(@NotNull Command command) {
        this.commandList.add(command);
        return this;
    }

    /**
     * <h1>Used to remove a command from the list</h1>
     *
     * @param command The instance of the bukkit command.
     * @return This instance.
     */
    public @NotNull BukkitCommandHandler remove(@NotNull Command command) {
        this.commandList.remove(command);
        return this;
    }

    /**
     * <h1>Used to remove all the commands from the handler</h1>
     *
     * @return This instance.
     */
    public @NotNull BukkitCommandHandler removeAll() {
        this.commandList = new ArrayList<>();
        return this;
    }

    /**
     * <h1>Used to register the bukkit commands</h1>
     *
     * @return This instance.
     */
    public @NotNull BukkitCommandHandler registerCommands() {
        CommandMap commandMap = this.getCommandMap();
        if (commandMap == null) return this;

        // Register commands.
        for (Command command : this.commandList) {
            commandMap.register(this.pluginName, command);
            ConsoleManager.log("[CommandHandler] &aRegistered command &f" + command.getName());
        }
        return this;
    }

    /**
     * <h1>Used to unregister the bukkit commands</h1>
     *
     * @return This instance.
     */
    public @NotNull BukkitCommandHandler unregisterCommands() {
        CommandMap commandMap = this.getCommandMap();
        if (commandMap == null) return this;

        // Register commands.
        for (Command command : this.commandList) {
            command.unregister(commandMap);
            ConsoleManager.log("[CommandHandler] &eUnRegistered command &f" + command.getName());
        }
        return this;
    }
}
