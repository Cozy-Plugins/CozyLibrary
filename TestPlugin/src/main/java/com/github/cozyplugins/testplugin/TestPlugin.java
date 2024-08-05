package com.github.cozyplugins.testplugin;

import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.cozylibrary.command.CommandManager;
import com.github.cozyplugins.cozylibrary.command.command.command.ProgrammableCommand;
import com.github.cozyplugins.cozylibrary.command.command.command.programmable.ProgrammableExecutor;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandArguments;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandStatus;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandSuggestions;
import com.github.cozyplugins.cozylibrary.inventory.inventory.ConfigurationDirectoryEditor;
import com.github.cozyplugins.cozylibrary.item.CozyItem;
import com.github.cozyplugins.cozylibrary.placeholder.Placeholder;
import com.github.cozyplugins.cozylibrary.placeholder.PlaceholderManager;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.cozyplugins.cozylibrary.user.User;
import com.github.cozyplugins.testplugin.commands.HelloWorldCommand;
import com.github.cozyplugins.testplugin.commands.TestCommandType;
import com.github.cozyplugins.testplugin.inventorys.RewardInventory;
import com.github.cozyplugins.testplugin.inventorys.TestInventory;
import com.github.squishylib.configuration.ConfigurationSection;
import com.github.squishylib.configuration.implementation.MemoryConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;

public class TestPlugin extends CozyPlugin<TestLoader> {

    /**
     * Used to create a new cozy plugin instance.
     *
     * @param plugin The instance of the plugin loader.
     */
    public TestPlugin(@NotNull TestLoader plugin) {
        super(plugin);
    }

    @Override
    public boolean isCommandTypesEnabled() {
        return true;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onLoadCommands(@NotNull CommandManager commandManager) {
        commandManager.addCommand(new HelloWorldCommand());

        // Inventory.
        commandManager.addCommand(new ProgrammableCommand("test_inventory").setPlayer((user, arguments) -> {
            new TestInventory().open(user);
            return new CommandStatus();
        }));

        // Sub commands.
        commandManager.addCommand(new ProgrammableCommand("test_subcommands")
                .addSubCommand(new ProgrammableCommand("test2")
                        .setSuggestions((user, arguments) -> new CommandSuggestions().append(new String[]{"test3"}))
                        .setUser(new ProgrammableExecutor<>() {
                            @Override
                            public @NotNull CommandStatus onUser(@NotNull User user, @NotNull CommandArguments arguments) {
                                user.sendMessage("arguments: " + String.join(", ", arguments.getArguments()));
                                return new CommandStatus();
                            }
                        })
                )
        );

        commandManager.addCommand(new ProgrammableCommand("test_item_give5")
                .setPlayer((user, arguments) -> {
                    Player player = user.getPlayer();

                    ConfigurationSection section = new MemoryConfigurationSection();
                    /*
                    material: PAPER
                    custom_model_data: 3002
                    name: "&6&lSkin Removing Token"
                    lore:
                      - "&bUse this token in the /tokencompressor"
                      - "&bto reset an item back to its vanilla model"
                      - "&7"
                      - "&7Applies to &fAll Tools"
                    enchanted: true
                     */
                    section.set("material", "PAPER");
                    section.set("custom_model_data", 3002);
                    section.set("name", "&6Test");
                    section.set("lore", List.of(
                            "&bUse this token in the /tokencompressor",
                            "&bto reset an item back to its vanilla model",
                            "&7",
                            "&7Applies to &fAll Tools"
                    ));
                    section.set("enchanted", true);
                    section.set("nbt.test", "test1441512");

                    player.getInventory().addItem(new CozyItem()
                            .convert(section)
                            .updateNBT(nbt -> {
                                nbt.setString("test", "test231");
                            })
                            .create()
                    );

                    return new CommandStatus();
                })
        );

        // Reward bundle.
        commandManager.addCommand(new ProgrammableCommand("test_reward_bundle").setPlayer((user, arguments) -> {
            RewardInventory inventory = new RewardInventory();
            inventory.open(user.getPlayer());
            return new CommandStatus();
        }));

        // Editor.
        commandManager.addCommand(new ProgrammableCommand("test_editor").setPlayer((user, arguments) -> {
            new ConfigurationDirectoryEditor(this.getCommandDirectory()) {
                @Override
                public void onOpenFile(@NotNull File file, @NotNull PlayerUser user) {
                    user.sendMessage("Opened " + file.getAbsolutePath());
                }
            }.open(user.getPlayer());
            return new CommandStatus();
        }));

        // Command type.
        commandManager.getTypeManager().registerCommandType(new TestCommandType());
    }

    @Override
    public void onLoadPlaceholders(@NotNull PlaceholderManager<TestLoader> placeholderManager) {
        placeholderManager.addPlaceholder(new Placeholder() {
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
