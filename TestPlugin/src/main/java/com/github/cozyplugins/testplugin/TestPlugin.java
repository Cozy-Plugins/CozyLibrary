package com.github.cozyplugins.testplugin;

import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.cozylibrary.command.CozyCommandHandler;
import com.github.cozyplugins.testplugin.commands.HelloWorldCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Setup cozy plugin.
        CozyPlugin.setPluginName("TestPlugin");

        // Setup commands.
        CozyCommandHandler commandHandler = new CozyCommandHandler();
        commandHandler.add(new HelloWorldCommand());
        commandHandler.registerCommands();
    }
}
