package com.github.cozyplugins.cozylibrary.inventory.slot.type;

import com.github.cozyplugins.cozylibrary.ConsoleManager;
import com.github.cozyplugins.cozylibrary.inventory.slot.SlotType;
import org.bukkit.event.inventory.InventoryType;

public class CenterSlotType implements SlotType {

    @Override
    public boolean match(String slot) {
        return slot.matches("^center[0-9]$");
    }

    @Override
    public int[] parse(String slot, InventoryType inventoryType) {
        String argument = slot.replace("center", "");

        try {
            int col = Integer.parseInt(argument);
            return new int[]{4 + (col * 9)};

        } catch (NumberFormatException exception) {
            ConsoleManager.warn("Invalid center slot type : &f" + slot);
            return new int[]{};
        }
    }
}
