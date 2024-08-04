package com.github.cozyplugins.cozylibrary.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * <h1>Represents the inventory handler</h1>
 */
public class InventoryManager implements Listener {

    private static @NotNull List<CozyInventory> inventoryInterfaceList = new ArrayList<>();

    /**
     * <h1>Used to add a inventory to the handler</h1>
     * Once added the inventory's actions will be processed.
     *
     * @param inventoryInterface The instance of an inventory.
     */
    public static void add(@NotNull CozyInventory inventoryInterface) {
        InventoryManager.inventoryInterfaceList.add(inventoryInterface);
    }

    /**
     * <h1>Used to get a registered inventory</h1>
     *
     * @param uuid The uuid if the inventory.
     * @return The requested inventory instance.
     */
    public static @Nullable CozyInventory get(@NotNull UUID uuid) {
        for (CozyInventory inventoryInterface : InventoryManager.inventoryInterfaceList) {
            if (inventoryInterface.getUuid() == uuid) return inventoryInterface;
        }
        return null;
    }

    public static @NotNull Optional<CozyInventory> get(@NotNull Inventory inventory) {
        for (CozyInventory cozyInventory : InventoryManager.inventoryInterfaceList) {
            if (cozyInventory.getInventory().equals(inventory)) return Optional.of(cozyInventory);
        }
        return Optional.empty();
    }

    /**
     * <h1>Used to get a registered inventory</h1>
     *
     * @param owner The owner.
     * @return The requested inventory instance.
     */
    public static @Nullable @Deprecated CozyInventory getFromOwner(@NotNull Player owner) {
        for (CozyInventory inventoryInterface : InventoryManager.inventoryInterfaceList) {
            if (inventoryInterface.getOwner() == null) continue;
            if (inventoryInterface.getOwner().getUniqueId() == owner.getUniqueId()) return inventoryInterface;
        }
        return null;
    }

    /**
     * Used to get a registered inventory the player is viewing.
     *
     * @param viewer The instance of the viewer.
     * @return The inventory.
     */
    public static @Nullable CozyInventory getFromViewer(@NotNull Player viewer) {
        for (CozyInventory inventoryInterface : InventoryManager.inventoryInterfaceList) {
            if (inventoryInterface.getInventory().getViewers().contains(viewer)) return inventoryInterface;
        }
        return null;
    }

    /**
     * <h1>Used to remove a inventory from the handler</h1>
     *
     * @param inventoryInterface The instance of the inventory.
     */
    public static void remove(@NotNull CozyInventory inventoryInterface) {
        InventoryManager.inventoryInterfaceList.remove(inventoryInterface);
    }

    /**
     * <h1>Used to remove a inventory given the inventory's uuid</h1>
     *
     * @param uuid The uuid of the inventory.
     */
    public static void remove(@NotNull UUID uuid) {
        CozyInventory inventoryInterface = InventoryManager.get(uuid);
        if (inventoryInterface == null) return;
        InventoryManager.remove(inventoryInterface);
    }

    /**
     * Used to clear the active inventory's.
     */
    public static void removeAll() {
        InventoryManager.inventoryInterfaceList = new ArrayList<>();
    }
}
