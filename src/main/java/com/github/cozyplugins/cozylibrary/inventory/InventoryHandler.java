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
public class InventoryHandler implements Listener {

    private static final @NotNull List<InventoryInterface> inventoryInterfaceList = new ArrayList<>();

    /**
     * <h1>Used to add a inventory to the handler</h1>
     * Once added the inventory's actions will be processed.
     *
     * @param inventoryInterface The instance of an inventory.
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
     * <h1>Used to get a registered inventory</h1>
     *
     * @param player The player who is using the interface.
     * @return The requested inventory instance.
     */
    public static @Nullable InventoryInterface get(@NotNull Player player) {
        for (InventoryInterface inventoryInterface : InventoryHandler.inventoryInterfaceList) {
            if (inventoryInterface.getOwner().getUniqueId() == player.getUniqueId()) return inventoryInterface;
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
        if (!(event.getWhoClicked() instanceof Player player)) return;

        // Attempt to get the inventory interface.
        InventoryInterface inventoryInterface = InventoryHandler.get(player);
        if (inventoryInterface == null) return;

        if (event.getRawSlot() > event.getInventory().getSize()) {

            if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                event.setCancelled(true);
            }
        }
    }
}
