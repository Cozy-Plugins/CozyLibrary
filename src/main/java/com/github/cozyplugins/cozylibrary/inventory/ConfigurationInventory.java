package com.github.cozyplugins.cozylibrary.inventory;

import com.github.cozyplugins.cozylibrary.inventory.slot.SlotParser;
import com.github.cozyplugins.cozylibrary.item.CozyItem;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Represents a configuration inventory.
 * This class takes a configuration section
 * and turns it into an interactive inventory.
 */
public abstract class ConfigurationInventory extends CozyInventory {

    private final @NotNull ConfigurationSection section;

    /**
     * Used to create a configuration inventory.
     *
     * @param section The instance of the configuration
     *                section.
     */
    public ConfigurationInventory(@NotNull ConfigurationSection section) {
        super(
                section.getInteger("size", 54),
                section.getString("title", "&8&lNull Title")
        );

        this.section = section;
    }

    /**
     * Called when an item has a function.
     *
     * @param item The instance of the item.
     * @param functionSection The configuration section that
     *                        contains the item function.
     * @return The item to add to the inventory.
     * If null it will not add it to the inventory.
     */
    public abstract @Nullable InventoryItem onFunction(
            @NotNull InventoryItem item,
            @NotNull ConfigurationSection functionSection
    );

    @Override
    protected void onGenerate(PlayerUser player) {

        // Reset the inventory.
        this.resetInventory();

        // Loop though all the items.
        for (String itemKey : this.section.getSection("items").getKeys()) {

            final List<Integer> slots = SlotParser.parse(itemKey, this.getInventory().getType());

            // Get the configuration section.
            ConfigurationSection itemSection = this.section.getSection("items." + itemKey);

            // Convert it into a cozy item.
            CozyItem item = new CozyItem(Material.BARRIER).convert(itemSection);

            // Create the inventory item.
            InventoryItem inventoryItem = new InventoryItem(item.create());
            inventoryItem.addSlotList(slots);

            // Check if there is an item function.
            if (itemSection.getKeys().contains("function")) {
                InventoryItem result = this.onFunction(
                        inventoryItem,
                        itemSection.getSection("function")
                );

                if (result == null) continue;
                inventoryItem = result;
            }

            // Add the item to the inventory.
            this.setItem(inventoryItem);
        }
    }
}
