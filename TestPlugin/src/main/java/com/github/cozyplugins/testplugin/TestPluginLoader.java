package com.github.cozyplugins.testplugin;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * <h1>Represents a test plugin</h1>
 */
public final class TestPluginLoader extends JavaPlugin {

    private TestPlugin plugin;

    @Override
    public void onEnable() {
        this.plugin = new TestPlugin(this);
        this.plugin.enable();
    }

    @Override
    public void onDisable() {
        this.plugin.disable();
    }
}
