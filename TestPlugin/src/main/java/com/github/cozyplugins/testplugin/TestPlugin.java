package com.github.cozyplugins.testplugin;

import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.testplugin.commands.HelloWorldCommand;

public final class TestPlugin extends CozyPlugin {

    @Override
    public void onEnable() {
        // Setup commands.
        this.getCommandHandler().add(new HelloWorldCommand());
        this.getCommandHandler().registerCommands();
    }
}
