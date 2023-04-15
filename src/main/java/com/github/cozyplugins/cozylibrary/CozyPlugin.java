package com.github.cozyplugins.cozylibrary;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * <h1>Represents a cozy plugin</h1>
 */
public abstract class CozyPlugin extends JavaPlugin {

    public CozyPlugin() {
        System.out.println(this.getName());
        CozyLibrary.setPluginName(this.getName());
    }
}
