package com.github.cozyplugins.testplugin;

import com.github.cozyplugins.cozylibrary.CozyLibrary;
import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.cozylibrary.command.command.command.ProgrammableCommand;
import com.github.cozyplugins.cozylibrary.command.command.command.programmable.ProgrammableSuggestions;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandArguments;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandStatus;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandSuggestions;
import com.github.cozyplugins.cozylibrary.inventory.inventory.ConfigurationDirectoryEditor;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.cozyplugins.cozylibrary.user.User;
import com.github.cozyplugins.testplugin.commands.HelloWorldCommand;
import com.github.cozyplugins.testplugin.commands.TestCommandType;
import com.github.cozyplugins.testplugin.inventorys.TestInventory;
import com.github.smuddgge.squishyconfiguration.implementation.yaml.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

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

        this.addCommand(new ProgrammableCommand("test")
                .addSubCommand(new ProgrammableCommand("test2")
                        .setSuggestions((user, arguments) -> new CommandSuggestions().append(new String[]{"test3"}))
                )
        );

        // Editor.
        this.addCommand(new ProgrammableCommand("editor").setPlayer((user, arguments) -> {
            new ConfigurationDirectoryEditor(CozyLibrary.getCommandDirectory()) {
                @Override
                public void onOpenFile(@NotNull File file, @NotNull PlayerUser user) {
                    user.sendMessage("Opened " + file.getAbsolutePath());
                }
            }.open(user.getPlayer());

            return new CommandStatus();
        }));

        // Setup command types.
        this.addCommandType(new TestCommandType());
    }
}
