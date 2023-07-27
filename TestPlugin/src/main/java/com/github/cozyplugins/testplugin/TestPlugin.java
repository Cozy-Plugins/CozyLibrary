package com.github.cozyplugins.testplugin;

import com.github.cozyplugins.cozylibrary.CozyLibrary;
import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.cozylibrary.command.command.command.ProgrammableCommand;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandStatus;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandSuggestions;
import com.github.cozyplugins.cozylibrary.inventory.inventory.ConfigurationDirectoryEditor;
import com.github.cozyplugins.cozylibrary.placeholder.CozyPlaceholder;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.cozyplugins.testplugin.commands.HelloWorldCommand;
import com.github.cozyplugins.testplugin.commands.TestCommandType;
import com.github.cozyplugins.testplugin.inventorys.RewardInventory;
import com.github.cozyplugins.testplugin.inventorys.TestInventory;
import org.bukkit.entity.Player;
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

        // Reward bundle.
        this.addCommand(new ProgrammableCommand("rewardbundle").setPlayer((user, arguments) -> {

            RewardInventory inventory = new RewardInventory();
            inventory.open(user.getPlayer());

            return new CommandStatus();
        }));

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

        // Add placeholder.
        this.addPlaceholder(new CozyPlaceholder() {
            @Override
            public @NotNull String getIdentifier() {
                return "test";
            }

            @Override
            public @NotNull String getValue(@Nullable Player player, @NotNull String params) {
                return "testing 123";
            }
        });
    }
}
