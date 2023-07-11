package com.github.cozyplugins.cozylibrary.inventory.inventory;

import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.inventory.InventoryItem;
import com.github.cozyplugins.cozylibrary.inventory.action.action.AnvilValueAction;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ClickAction;
import com.github.cozyplugins.cozylibrary.inventory.action.action.PlaceAction;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * Represents a file editor.
 * Usually editing files in a {@link ConfigurationDirectoryEditor}.
 */
public class FileEditor extends InventoryInterface {

    private final @NotNull File file;
    private final @NotNull ConfigurationSection folder;
    private final @NotNull ConfigurationSection section;
    private final @NotNull ConfigurationDirectoryEditor editor;

    /**
     * Used to create a file editor.
     *
     * @param file    The instance of the file.
     * @param folder  The configuration instance of the folder the file is in.
     * @param section The instance of the files configuration section.
     * @param editor  The instance of the directory editor.
     */
    public FileEditor(
            @NotNull File file,
            @NotNull ConfigurationSection folder,
            @NotNull ConfigurationSection section,
            @NotNull ConfigurationDirectoryEditor editor) {

        super(27, "&8&lFile Editor");

        this.file = file;
        this.folder = folder;
        this.section = section;
        this.editor = editor;
    }

    @Override
    protected void onGenerate(PlayerUser player) {
        // Replace actions.
        this.removeActionRange(0, 26);

        // Generate background.
        this.setItem(new InventoryItem()
                .setMaterial(Material.GRAY_STAINED_GLASS_PANE)
                .setName("&7")
                .addSlotRange(0, 26)
        );

        // Back button.
        this.setItem(new InventoryItem()
                .setMaterial(Material.LIME_STAINED_GLASS_PANE)
                .setName("&a&lBack")
                .setLore("&7Click to go back to the directory editor.")
                .addSlot(18)
                .addAction((ClickAction) (user, type, inventory) -> {
                    user.getPlayer().closeInventory();
                    this.editor.open(user.getPlayer());
                })
        );

        // Change name.
        this.setItem(new InventoryItem()
                .setMaterial(Material.NAME_TAG)
                .setName("&6&lChange Name")
                .setLore("&7Click to change the name of the file.",
                        "&aCurrent &e" + this.file.getName())
                .addSlot(12)
                .addAction(new AnvilValueAction() {
                    @Override
                    public @NotNull String getAnvilTitle() {
                        return "&8&lChange name";
                    }

                    @Override
                    public void onValue(@Nullable String value, @NotNull PlayerUser user) {
                        String currentName = file.getName();
                        String currentPath = file.getAbsolutePath().substring(0, (file.getAbsolutePath().length() - currentName.length()));

                        // Attempt to rename the file.
                        boolean success;
                        try {
                            success = file.renameTo(new File(currentPath + value));

                        } catch (Exception exception) {
                            exception.printStackTrace();
                            success = false;
                        }

                        if (!success) {
                            user.sendMessage("&7Unable to rename file.");
                        }

                        if (success) {
                            // Set new configuration.
                            folder.set(value + ".slot", section.getString("slot"));
                            folder.set(value + ".material", section.getString("material"));

                            // Remove old configuration.
                            folder.set(currentName, null);
                        }

                        // Re-open inventory.
                        open(user.getPlayer());
                    }
                })
        );

        // Get material.
        Material material = Material.getMaterial(this.section.getString("material", "WHITE_STAINED_GLASS_PANE"));

        // Change material.
        this.setItem(new InventoryItem()
                .setMaterial(material)
                .setName("&6&lChange Material")
                .setLore("&7Replace this item to change the material.")
                .addSlot(14)
                .addAction((PlaceAction) (user, item) -> {
                    user.sendMessage("&7Changed file to " + item.getMaterial());
                    section.set("material", item.getMaterial().toString());
                    this.open(user.getPlayer());
                })
        );
    }
}
