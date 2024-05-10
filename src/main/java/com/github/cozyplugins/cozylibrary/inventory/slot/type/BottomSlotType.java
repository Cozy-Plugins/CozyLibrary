package com.github.cozyplugins.cozylibrary.inventory.slot.type;

import com.github.cozyplugins.cozylibrary.inventory.slot.SlotType;
import org.bukkit.event.inventory.InventoryType;

public class BottomSlotType implements SlotType {

    @Override
    public boolean match(String slot) {
        return slot.matches("^bottom$");
    }

    @Override
    public int[] parse(String slot, InventoryType inventoryType) {
        return switch (inventoryType) {
            case CHEST -> this.chest(inventoryType);
            case PLAYER -> new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
            case LOOM -> new int[]{2};
            default -> new int[]{45, 46, 47, 48, 49, 50, 51, 52, 53};
        };
    }

    private int[] chest(InventoryType type) {
        return switch (type.getDefaultSize()) {
            case 9 -> new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
            case 18 -> new int[]{9, 10, 11, 12, 13, 14, 15, 16, 17};
            case 27 -> new int[]{18, 19, 20, 21, 22, 23, 24, 25, 26};
            case 36 -> new int[]{27, 28, 29, 30, 31, 32, 33, 34, 35};
            case 45 -> new int[]{36, 37, 38, 39, 40, 41, 42, 43, 44};
            default -> new int[]{45, 46, 47, 48, 49, 50, 51, 52, 53};
        };
    }
}
