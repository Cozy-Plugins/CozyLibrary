package com.github.cozyplugins.cozylibrary.inventory.slot.type;

import com.github.cozyplugins.cozylibrary.ConsoleManager;
import com.github.cozyplugins.cozylibrary.inventory.slot.SlotType;
import org.bukkit.event.inventory.InventoryType;

import java.util.stream.IntStream;

public class RangeSlotType implements SlotType {

    @Override
    public boolean match(String slot) {
        return slot.matches("^[0-9]+-[0-9]+$");
    }

    @Override
    public int[] parse(String slot, InventoryType inventoryType) {
        try {
            int start = Integer.parseInt(slot.split("-")[0]);
            int end = Integer.parseInt(slot.split("-")[1]);

            return IntStream.range(start, end + 1).toArray();
        } catch (Exception exception) {
            ConsoleManager.warn("Could not parse slot in inventory : &f" + slot);
            return new int[0];
        }
    }
}
