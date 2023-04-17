package com.github.cozyplugins.testplugin;

import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.cozylibrary.command.CozyCommandHandler;
import com.github.cozyplugins.cozylibrary.item.CozyItem;
import com.github.cozyplugins.testplugin.commands.HelloWorldCommand;

public final class TestPlugin extends CozyPlugin {

    @Override
    public void onEnable() {
        // Setup commands.
        CozyCommandHandler commandHandler = new CozyCommandHandler();
        commandHandler.add(new HelloWorldCommand());
        commandHandler.registerCommands();
    }
}
