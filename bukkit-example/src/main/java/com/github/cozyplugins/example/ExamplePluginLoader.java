package com.github.cozyplugins.example;

import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePluginLoader extends JavaPlugin {

    private ExamplePlugin plugin;

    @Override
    public void onEnable() {
        this.plugin = new ExamplePlugin(this);
        this.plugin.enable();
    }

    @Override
    public void onDisable() {
        this.plugin.disable();
    }
}
