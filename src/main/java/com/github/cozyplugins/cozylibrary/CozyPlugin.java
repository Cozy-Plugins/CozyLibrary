package com.github.cozyplugins.cozylibrary;

import com.github.cozyplugins.cozylibrary.command.CommandTypeManager;
import com.github.cozyplugins.cozylibrary.command.CozyCommandHandler;
import com.github.cozyplugins.cozylibrary.command.command.CommandType;
import com.github.cozyplugins.cozylibrary.command.command.CozyCommand;
import com.github.cozyplugins.cozylibrary.configuration.CommandDirectory;
import com.github.cozyplugins.cozylibrary.dependency.ProtocolDependency;
import com.github.cozyplugins.cozylibrary.dependency.VaultAPIDependency;
import com.github.cozyplugins.cozylibrary.inventory.InventoryManager;
import com.github.cozyplugins.cozylibrary.inventory.action.ActionManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * <h1>Represents a cozy plugin</h1>
 * Extends {@link JavaPlugin}.
 */
public abstract class CozyPlugin extends JavaPlugin {

    private static CozyPlugin plugin;

    @Override
    public void onEnable() {

        // Set plugin name.
        CozyLibrary.setPluginName(this.getName());

        // Set up the plugin instance.
        CozyPlugin.plugin = this;

        // Set up the dependency's.
        ProtocolDependency.setup();
        VaultAPIDependency.setup();

        // Setup command directory.
        CozyLibrary.setCommandDirectory(new CommandDirectory("commands.yml", this.getClass()));

        // Enable plugin.
        this.onCozyEnable();

        // Reload command directory and add command types.
        CozyLibrary.getCommandDirectory().reload();

        // Register commands that have been added.
        this.registerCommands();

        // Register listeners.
        this.getServer().getPluginManager().registerEvents(new InventoryManager(), this);

        // Register inventory events.
        new ActionManager(this);
    }

    @Override
    public void onDisable() {
        // Unregister commands.
        CozyLibrary.getCommandHandler().unregisterCommands();

        // Unregister listeners.
        InventoryManager.removeAll();
    }

    /**
     * <h1>Returns if there should be a command directory</h1>
     *
     * @return True if the commands directory should be enabled.
     */
    public abstract boolean enableCommandDirectory();

    /**
     * <h1>Called when the plugin is enabled</h1>
     */
    public abstract void onCozyEnable();

    /**
     * <h1>Used to get the command handler</h1>
     * The command handler is used to register cozy
     * commands.
     * You can also get the command handler from
     * the cozy library class.
     *
     * @return The command handler.
     */
    public @NotNull CozyCommandHandler getCommandHandler() {
        return CozyLibrary.getCommandHandler();
    }

    /**
     * <h1>Used to add a command to the command handler</h1>
     *
     * @param command The command to add.
     * @return This instance.
     */
    public @NotNull CozyPlugin addCommand(CozyCommand command) {
        this.getCommandHandler().add(command);
        return this;
    }

    /**
     * <h1>Used to register a command type</h1>
     *
     * @param commandType The command type to register.
     * @return This instance.
     */
    public @NotNull CozyPlugin addCommandType(CommandType commandType) {
        CommandTypeManager.register(commandType);
        return this;
    }

    /**
     * <h1>Used to register the commands.</h1>
     *
     * @return This instance.
     */
    public @NotNull CozyPlugin registerCommands() {
        this.getCommandHandler().registerCommands();
        return this;
    }

    /**
     * Used to get the instance of the plugin.
     *
     * @return The instance of the plugin.
     */
    public static CozyPlugin getPlugin() {
        return CozyPlugin.plugin;
    }
}
