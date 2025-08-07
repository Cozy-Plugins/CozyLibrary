package com.github.cozyplugins.cozylibrary.inventory_v2;

import com.github.cozyplugins.cozylibrary.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum InventoryType {
    GENERIC_1X9,
    GENERIC_2X9,
    GENERIC_3X9,
    GENERIC_4X9,
    GENERIC_5X9,
    CHEST,
    DISPENSER,
    DROPPER,
    FURNACE,
    WORKBENCH,
    CRAFTING,
    ENCHANTING,
    BREWING,
    PLAYER,
    CREATIVE,
    MERCHANT,
    ENDER_CHEST,
    ANVIL,
    SMITHING,
    BEACON,
    HOPPER,
    SHULKER_BOX,
    BARREL,
    BLAST_FURNACE,
    LECTERN,
    SMOKER,
    LOOM,
    CARTOGRAPHY,
    GRINDSTONE,
    STONECUTTER,
    COMPOSTER,
    CHISELED_BOOKSHELF,
    JUKEBOX,
    CRAFTER;

    public @NotNull Inventory createInventory(final @NotNull String title) {
        return switch (this) {
            case GENERIC_1X9 -> Bukkit.createInventory(null, 9, MessageManager.parse(title));
            case GENERIC_2X9 -> Bukkit.createInventory(null, 9 * 2, MessageManager.parse(title));
            case GENERIC_3X9 -> Bukkit.createInventory(null, 9 * 3, MessageManager.parse(title));
            case GENERIC_4X9 -> Bukkit.createInventory(null, 9 * 4, MessageManager.parse(title));
            case GENERIC_5X9 -> Bukkit.createInventory(null, 9 * 5, MessageManager.parse(title));
            default -> Bukkit.createInventory(
                    null,
                    org.bukkit.event.inventory.InventoryType.valueOf(this.name()),
                    MessageManager.parse(title)
            );
        };
    }

    public @Nullable org.bukkit.event.inventory.InventoryType asBukkit() {
        return switch (this) {
            case GENERIC_1X9, GENERIC_2X9, GENERIC_3X9, GENERIC_4X9, GENERIC_5X9 -> null;
            default -> org.bukkit.event.inventory.InventoryType.valueOf(this.name());
        };
    }
}
