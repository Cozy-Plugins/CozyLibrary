package com.github.cozyplugins.cozylibrary.inventory.slot.type;

import com.github.cozyplugins.cozylibrary.inventory.slot.SlotType;
import org.bukkit.event.inventory.InventoryType;

public class TopSlotType implements SlotType {

    @Override
    public boolean match(String slot) {
        return slot.matches("^top$");
    }

    @Override
    public int[] parse(String slot, InventoryType inventoryType) {
        return switch (inventoryType) {
            case CRAFTING -> new int[]{0, 1, 2};
            case PLAYER -> new int[]{9, 10, 11, 12, 13, 14, 15, 16, 17};
            case LOOM -> new int[]{0, 1};
            default -> new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        };
    }
}
