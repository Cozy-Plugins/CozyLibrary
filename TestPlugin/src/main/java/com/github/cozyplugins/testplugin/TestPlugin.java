package com.github.cozyplugins.testplugin;

import com.github.cozyplugins.cozylibrary.CozyLibrary;
import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.cozylibrary.command.command.command.ProgrammableCommand;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandStatus;
import com.github.cozyplugins.cozylibrary.inventory.inventory.ConfigurationDirectoryEditor;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.cozyplugins.testplugin.commands.HelloWorldCommand;
import com.github.cozyplugins.testplugin.commands.TestCommandType;
import com.github.cozyplugins.testplugin.inventorys.TestInventory;
import com.github.smuddgge.squishyconfiguration.implementation.yaml.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * <h1>Represents a test plugin</h1>
 */
public final class TestPlugin extends CozyPlugin {

    @Override
    public boolean enableCommandDirectory() {
        return true;
    }

    @Override
    public void onCozyEnable() {
        // Setup commands.
        this.addCommand(new HelloWorldCommand());
        this.addCommand(new ProgrammableCommand("testinventory").setPlayer((user, arguments) -> {
            new TestInventory().open(user.getPlayer());
            return new CommandStatus();
        }));

        // Editor.
        this.addCommand(new ProgrammableCommand("editor").setPlayer((user, arguments) -> {
            new ConfigurationDirectoryEditor(CozyLibrary.getCommandDirectory()) {
                @Override
                public void onOpenFile(@NotNull YamlConfiguration configuration, @NotNull PlayerUser user) {
                    user.sendMessage("Opened " + configuration.getAbsolutePath());
                }
            }.open(user.getPlayer());

            return new CommandStatus();
        }));

        // Setup command types.
        this.addCommandType(new TestCommandType());
    }
}
