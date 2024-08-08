package com.github.cozyplugins.cozylibrary.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
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

    /**
     * <h1>Used to get a registered inventory</h1>
     *
     * @param owner The owner.
     * @return The requested inventory instance.
     */
    public static @Nullable CozyInventory getFromOwner(@NotNull Player owner) {
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
     * <h1>Used to get a registered inventory</h1>
     *
     * @param inventory The instance of the inventory.
     * @return The requested inventory interface instance.
     */
    public static @Nullable CozyInventory get(@Nullable org.bukkit.inventory.Inventory inventory) {
        if (inventory == null) return null;
        for (CozyInventory inventoryInterface : InventoryManager.inventoryInterfaceList) {
            org.bukkit.inventory.Inventory compare = inventoryInterface.getInventory();

            if (!inventory.equals(compare)) continue;
            if (compare.getViewers() != inventory.getViewers()) continue;
            if (compare.getHolder() != inventory.getHolder()) continue;
            if (compare.getType() != inventory.getType()) continue;

            return inventoryInterface;
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

    /**
     * <h1>When an inventory is clicked</h1>
     * This will handle the custom inventory's stored
     * in this class.
     *
     * @param event The instance of the event.
     */
    @EventHandler
    private void inventoryClickEvent(InventoryClickEvent event) {
        // Check if it was a player who clicked.
        if (!(event.getWhoClicked() instanceof Player)) return;

        // Attempt to get the inventory interface.
        CozyInventory inventoryInterface = InventoryManager.get(event.getInventory());
        if (inventoryInterface == null) return;

        if (event.getRawSlot() > event.getInventory().getSize()) {

            if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                event.setCancelled(true);
            }
        }
    }
}
