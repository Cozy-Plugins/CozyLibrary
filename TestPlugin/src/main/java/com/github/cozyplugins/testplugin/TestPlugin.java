package com.github.cozyplugins.testplugin;

import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.testplugin.commands.HelloWorldCommand;
import com.github.cozyplugins.testplugin.commands.InfoCommandType;

public final class TestPlugin extends CozyPlugin {

    @Override
    public boolean enableCommandDirectory() {
        return true;
    }

    @Override
    public void onCozyEnable() {
        // Setup commands.
        this.addCommand(new HelloWorldCommand());

        // Setup command types.
        this.addCommandType(new InfoCommandType());
    }
}
