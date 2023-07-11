package com.github.cozyplugins.cozylibrary.inventory.inventory;

import com.github.cozyplugins.cozylibrary.ConsoleManager;
import com.github.cozyplugins.cozylibrary.configuration.ConfigurationDirectory;
import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.inventory.InventoryItem;
import com.github.cozyplugins.cozylibrary.inventory.action.action.AnvilValueAction;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ClickAction;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ClickActionWithResult;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ConfirmAction;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.smuddgge.squishyconfiguration.implementation.yaml.YamlConfiguration;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Represents an inventory interface for a configuration directory.
 */
public abstract class ConfigurationDirectoryEditor extends InventoryInterface {

    private final @NotNull ConfigurationDirectory directory;
    private final @NotNull YamlConfiguration store;
    private @NotNull String path;

    /**
     * Used to create a configuration directory editor.
     *
     * @param directory The directory being represented.
     */
    public ConfigurationDirectoryEditor(@NotNull ConfigurationDirectory directory) {
        super(54, "&8&l" + directory.getDirectoryName());

        this.directory = directory;

        // Get or create if the store file doesn't exist.
        // This file represents the folder's settings.
        this.store = directory.createStore();

        // Make sure the path is not null.
        this.path = "";
    }

    /**
     * Used to create a configuration directory in a certain path.
     *
     * @param directory The instance of the directory.
     * @param path      The path.
     */
    public ConfigurationDirectoryEditor(@NotNull ConfigurationDirectory directory, @NotNull String path) {
        this(directory);

        this.path = path;
    }

    /**
     * Called when a file is opened.
     *
     * @param configuration The instance of the configuration file.
     */
    public abstract void onOpenFile(@NotNull YamlConfiguration configuration, @NotNull PlayerUser user);

    @Override
    protected void onGenerate(PlayerUser player) {
        // Clear
        this.removeActionRange(0, 53);
        this.setItem(new InventoryItem().setMaterial(Material.AIR).addSlotRange(0, 53));

        File folder = this.directory.getDirectory(this.path);
        if (folder == null) {
            ConsoleManager.error("Unable to get directory &f" + this.path + " &cfrom &f" + this.directory.getDirectoryName());
            return;
        }

        // Check if the folder is a configuration file.
        if (folder.getName().contains(".yml")) {
            player.getPlayer().closeInventory();
            this.onOpenFile(new YamlConfiguration(folder), player);
            return;
        }

        // Get the files and folders, in this folder.
        File[] fileList = folder.listFiles();

        // Check if this is not a folder.
        if (fileList == null) {
            player.sendMessage("&7This location is not a folder or configuration file.");
            player.sendMessage("&7Returning to the main editor page.");
            this.path = "";
            try {
                this.onGenerate(player);
            } catch (StackOverflowError error) {
                ConsoleManager.error("Configuration directory is not a directory. Directory is named &f" + this.directory.getDirectoryName());
            }
            return;
        }

        // Set background.
        this.setItem(new InventoryItem()
                .setMaterial(Material.GRAY_STAINED_GLASS_PANE)
                .setName("&7")
                .addSlotRange(45, 53)
                .addSlotRange(9, 17)
        );
        this.setItem(new InventoryItem()
                .setMaterial(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                .setName("&7")
                .addSlotRange(0, 8)
        );

        // Back button.
        if (this.path.length() > 0) {
            this.setItem(new InventoryItem()
                    .setMaterial(Material.YELLOW_STAINED_GLASS_PANE)
                    .setName("&e&lBack")
                    .setLore("&7Click to go back to the previously viewed folder.")
                    .addSlot(45)
                    .addAction((ClickAction) (user, type, inventory) -> {
                        String[] folderNames = this.path.split("\\.");
                        String current = folderNames[folderNames.length - 1];
                        String previous = this.path.substring(0, this.path.length() - current.length());
                        this.path = previous;
                        this.onGenerate(player);
                    })
            );
        }

        // Create folder button.
        this.setItem(new InventoryItem()
                .setMaterial(Material.LIME_STAINED_GLASS_PANE)
                .setName("&a&lCreate Folder")
                .setLore("&7Click to create a new folder.")
                .addSlot(48)
                .addAction(new AnvilValueAction() {
                    @Override
                    public @NotNull String getAnvilTitle() {
                        return "&8&lFolder name";
                    }

                    @Override
                    public void onValue(@Nullable String value, @NotNull PlayerUser user) {
                        File newFolder = new File(folder.getAbsolutePath(), "new_folder");
                        newFolder.mkdir();

                        open(user.getPlayer());
                    }
                })
        );

        // Create file button.
        this.setItem(new InventoryItem()
                .setMaterial(Material.LIME_STAINED_GLASS_PANE)
                .setName("&a&lCreate Configuration File")
                .setLore("&7Used to create a new configuration file.")
                .addSlot(50)
                .addAction(new AnvilValueAction() {
                    @Override
                    public @NotNull String getAnvilTitle() {
                        return "&8&lFile name";
                    }

                    @Override
                    public void onValue(@Nullable String value, @NotNull PlayerUser user) {
                        YamlConfiguration configuration = new YamlConfiguration(folder, value + ".yml");
                        configuration.load();

                        open(user.getPlayer());
                    }
                })
        );

        // File Location bar.
        this.generateLocationBar();

        // File items.
        this.generateFiles(fileList);
    }

    /**
     * Used to create the location bar.
     */
    public void generateLocationBar() {
        // Get location item names in reverse order.
        List<String> locationItemNames = new ArrayList<>(Arrays.asList(this.path.split("\\.")));
        Collections.reverse(locationItemNames);

        int slot = 9;
        for (String name : locationItemNames) {
            // Start slots at 8.
            slot -= 1;
            if (slot < 0) return;

            int finalSlot = slot;
            this.setItem(new InventoryItem()
                    .setMaterial(Material.BOOK)
                    .setName("&6&l" + name)
                    .setLore("&7Click to go back to this location.")
                    .addSlot(slot)
                    .addAction((ClickAction) (user, type, inventory) -> {
                        int maxIndex = locationItemNames.size() - 1;
                        int fromTheRight = Math.abs(finalSlot - 8);

                        // Repeat fromTheRight number of times.
                        for (int i = 0; i <= fromTheRight; i++) {
                            locationItemNames.remove(locationItemNames.size() - 1);
                        }

                        // Reverse back.
                        Collections.reverse(locationItemNames);

                        // Get the new path.
                        this.path = String.join(".", locationItemNames);

                        // Generate.
                        onGenerate(user);
                    })
            );
        }
    }

    /**
     * Used to create the file items.
     *
     * @param fileList The list of files.
     */
    public void generateFiles(File[] fileList) {
        // This will hold the current item being moved in the inventory.
        AtomicReference<String> nameClicked = new AtomicReference<>(null);

        // When an item is moved.
        this.setItem(new InventoryItem()
                .setMaterial(Material.AIR)
                .addSlotRange(19, 43)
                .addAction((ClickActionWithResult) (user, type, inventory, currentResult, slot) -> {
                    if (nameClicked.get() == null) return currentResult;

                    // Get and save the new file slot.
                    String name = nameClicked.get();
                    ConfigurationSection section = this.store.getSection("base." + this.path + "." + name);
                    section.set("slot", slot);
                    this.store.save();

                    user.sendMessage("&7Changed location of " + name + " to " + slot);

                    // Re-place items.
                    this.onGenerate(user);

                    // Return false.
                    return currentResult.setCancelled(false);
                })
        );

        for (File file : fileList) {
            // Ignore system files.
            if (file.getName().contains("cozystore")) continue;
            if (file.getName().contains("DS_Store")) continue;

            ConfigurationSection section = this.store.getSection("base." + this.path + "." + file.getName());

            // Material.
            Material defaultMaterial = Material.PAPER;
            if (file.listFiles() != null) {
                defaultMaterial = Material.BOOK;
            }

            Material material = Material.getMaterial(section.getString("material", defaultMaterial.toString()));
            if (material == null) {
                ConsoleManager.warn("Material doesnt exist for " + file.getName() + " in " + this.path + " in " + this.directory.getDirectoryName());
                material = defaultMaterial;
            }

            section.set("material", material.toString());

            // Slot.
            int defaultSlot = this.getInventory().firstEmpty();

            // Set the item representing the file or folder.
            this.setItem(new InventoryItem()
                    .setMaterial(material)
                    .setName("&6&l" + file.getName())
                    .setLore("&eMove file &fLeft Click",
                            "&eEnter file &fRight click",
                            "&eDelete file &fShift Left Click",
                            "&eEdit file &fShift Right Click",
                            "&7When moving a file, you can click another",
                            "&7folder to change its location.")
                    .setAmount(1)
                    .addSlot(section.getInteger("slot", defaultSlot))
                    .addAction((ClickActionWithResult) (user, type, inventory, actionResult, slot) -> {
                        // Check if the player wants to move the item.
                        if (type == ClickType.LEFT) {
                            // Check if there is already an item being processed.
                            if (nameClicked.get() != null) {
                                // Check if the item has not moved.
                                if (slot == section.getInteger("slot", defaultSlot)) {
                                    nameClicked.set(null);
                                    return actionResult.setCancelled(false);
                                }

                                // Attempting to put the file into a file.
                                if (file.listFiles() == null) {
                                    user.sendMessage("&7You cannot put a file into a configuration file.");
                                    return actionResult.setCancelled(true);
                                }

                                // Attempting to put a file into a folder.
                                

                                return actionResult.setCancelled(true);
                            }

                            nameClicked.set(file.getName());
                            return actionResult.setCancelled(false);
                        }

                        // Check if the player wants to enter the file or folder.
                        if (type == ClickType.RIGHT) {
                            // Check if this is a file.
                            if (file.getName().contains(".yml") || file.getName().contains(".yaml")) {
                                user.getPlayer().closeInventory();
                                this.onOpenFile(new YamlConfiguration(file), user);
                                return actionResult.setCancelled(true);
                            }

                            // Check if the path is not defined.
                            if (!this.path.equals("")) {
                                this.path = this.path + "." + file.getName();
                                this.onGenerate(user);
                                return actionResult.setCancelled(true);
                            }

                            this.path = file.getName();
                            this.onGenerate(user);
                            return actionResult.setCancelled(true);
                        }

                        // Check if the player wants to delete this file or folder.
                        if (type == ClickType.SHIFT_LEFT) {
                            // Ask for confirmation.
                            new ConfirmationInventory(new ConfirmAction() {
                                @Override
                                public @NotNull String getTitle() {
                                    return "&8&lDelete " + file.getName();
                                }

                                @Override
                                public void onConfirm(@NotNull PlayerUser user) {
                                    file.delete();
                                    open(user.getPlayer());
                                }

                                @Override
                                public void onAbort(@NotNull PlayerUser user) {
                                    open(user.getPlayer());
                                }
                            }).open(user.getPlayer());
                            return actionResult.setCancelled(true);
                        }

                        // Check if the player wants to edit the file or folder.
                        if (type == ClickType.SHIFT_RIGHT) {
                            FileEditor editor = new FileEditor(
                                    file, this.directory.getSection("base." + this.path), section, this
                            );
                            editor.open(user.getPlayer());
                            return actionResult.setCancelled(true);
                        }

                        return actionResult;
                    })
            );
        }
    }
}