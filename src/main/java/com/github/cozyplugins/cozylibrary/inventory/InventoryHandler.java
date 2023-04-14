package com.github.cozyplugins.cozylibrary.inventory;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <h1>Represents the inventory handler</h1>
 */
public class InventoryHandler {

    private static final @NotNull List<InventoryInterface> inventoryInterfaceList = new ArrayList<>();

    /**
     * <h1>Used to add a inventory to the handler</h1>
     * Once added the inventory's actions will be processed.
     *
     * @param inventoryInterface The instance of a inventory.
     */
    public static void add(@NotNull InventoryInterface inventoryInterface) {
        InventoryHandler.inventoryInterfaceList.add(inventoryInterface);
    }

    /**
     * <h1>Used to get a registered inventory</h1>
     *
     * @param uuid The uuid if the inventory.
     * @return The requested inventory instance.
     */
    public static @Nullable InventoryInterface get(@NotNull UUID uuid) {
        for (InventoryInterface inventoryInterface : InventoryHandler.inventoryInterfaceList) {
            if (inventoryInterface.getUuid() == uuid) return inventoryInterface;
        }
        return null;
    }

    /**
     * <h1>Used to remove a inventory from the handler</h1>
     *
     * @param inventoryInterface The instance of the inventory.
     */
    public static void remove(@NotNull InventoryInterface inventoryInterface) {
        InventoryHandler.inventoryInterfaceList.remove(inventoryInterface);
    }

    /**
     * <h1>Used to remove a inventory given the inventory's uuid</h1>
     *
     * @param uuid The uuid of the inventory.
     */
    public static void remove(@NotNull UUID uuid) {
        InventoryInterface inventoryInterface = InventoryHandler.get(uuid);
        if (inventoryInterface == null) return;
        InventoryHandler.remove(inventoryInterface);
    }
}
