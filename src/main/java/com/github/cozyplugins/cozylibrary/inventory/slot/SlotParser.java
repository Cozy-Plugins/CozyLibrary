package com.github.cozyplugins.cozylibrary.inventory.slot;

import com.github.cozyplugins.cozylibrary.inventory.slot.type.*;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the slot manager.
 */
public class SlotParser {

    private static SlotParser instance;

    private final List<SlotType> slotTypeList = new ArrayList<>();

    private SlotParser() {
        this.slotTypeList.add(new BottomSlotType());
        this.slotTypeList.add(new CenterSlotType());
        this.slotTypeList.add(new RangeSlotType());
        this.slotTypeList.add(new RowSlotType());
        this.slotTypeList.add(new TopSlotType());
    }

    /**
     * Used to parse a slot into individual slots.
     *
     * @param slot          The slot to parse.
     * @param inventoryType The inventory type the slot is in.
     * @return The requested slots.
     */
    public static @NotNull List<Integer> parse(String slot, InventoryType inventoryType) {

        // Initialize singleton instance.
        if (SlotParser.instance == null) {
            SlotParser.instance = new SlotParser();
        }

        for (SlotType slotType : SlotParser.instance.slotTypeList) {
            if (!slotType.match(slot)) continue;

            return Arrays.stream(slotType.parse(slot, inventoryType)).boxed().collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
