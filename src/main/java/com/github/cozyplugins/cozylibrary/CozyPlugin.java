package com.github.cozyplugins.cozylibrary;

import com.github.cozyplugins.cozylibrary.command.CommandTypeManager;
import com.github.cozyplugins.cozylibrary.command.CozyCommandHandler;
import com.github.cozyplugins.cozylibrary.command.command.CozyCommand;
import com.github.cozyplugins.cozylibrary.command.command.CozyCommandType;
import com.github.cozyplugins.cozylibrary.configuration.CommandDirectory;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * <h1>Represents a cozy plugin</h1>
 * Extends {@link JavaPlugin}.
 */
public abstract class CozyPlugin extends JavaPlugin {

    /**
     * <h1>Used to initialise the cozy plugin</h1>
     */
    public CozyPlugin() {
        CozyLibrary.setPluginName(this.getName());

        // Setup command directory.
        CozyLibrary.setCommandDirectory(new CommandDirectory("commands.yml"));
        CozyLibrary.getCommandDirectory().reload();
    }

    /**
     * <h1>Returns if there should be a command directory</h1>
     *
     * @return True if the commands directory should be enabled.
     */
    public abstract boolean enableCommandDirectory();

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
    public @NotNull CozyPlugin addCommandType(CozyCommandType commandType) {
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
}
