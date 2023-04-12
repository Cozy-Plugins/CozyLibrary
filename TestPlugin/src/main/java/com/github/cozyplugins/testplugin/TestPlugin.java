package com.github.cozyplugins.testplugin;

import com.github.cozyplugins.cozylibrary.CozyPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        CozyPlugin.setPluginName("TestPlugin");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
