package com.github.cozyplugins.cozylibrary;

import com.github.cozyplugins.cozylibrary.command.CozyCommandHandler;
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
    }

    /**
     * <h1>Used to get the command handler</h1>
     * The command handler is used to register cozy
     * commands.
     *
     * @return The command handler.
     */
    public @NotNull CozyCommandHandler getCommandHandler() {
        return CozyLibrary.getCommandHandler();
    }
}
