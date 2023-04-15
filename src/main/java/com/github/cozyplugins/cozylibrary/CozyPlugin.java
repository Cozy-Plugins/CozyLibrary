package com.github.cozyplugins.cozylibrary;

import org.bukkit.plugin.java.JavaPlugin;

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
}
