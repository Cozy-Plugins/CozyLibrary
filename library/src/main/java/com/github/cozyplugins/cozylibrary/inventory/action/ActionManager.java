package com.github.cozyplugins.cozylibrary.inventory.action;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.cozylibrary.dependency.ProtocolDependency;
import com.github.cozyplugins.cozylibrary.inventory.CozyInventory;
import com.github.cozyplugins.cozylibrary.inventory.InventoryManager;
import com.github.cozyplugins.cozylibrary.inventory.action.handler.*;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Represents the action manager</h1>
 * Used to register the inventory action events.
 */
public final class ActionManager implements Listener {

    private static @NotNull List<ActionHandler> actionHandlerList;

    /**
     * <h1>Used to create a action manager</h1>
     *
     * @param plugin The instance of the base plugin.
     */
    public ActionManager(@NotNull CozyPlugin<?> plugin) {
        plugin.getPlugin().getServer().getPluginManager().registerEvents(this, plugin.getPlugin());

        ActionManager.actionHandlerList = new ArrayList<>();
        ActionManager.addActionHandler(new ClickActionHandler());
        ActionManager.addActionHandler(new PlaceActionHandler());
        ActionManager.addActionHandler(new AnvilValueActionHandler());
        ActionManager.addActionHandler(new ConfirmActionHandler());
        ActionManager.addActionHandler(new ClickActionWithResultHandler());
        ActionManager.addActionHandler(new CloseActionHandler());

        if (!ProtocolDependency.isEnabled()) return;

        // Setup packet listeners.
        ProtocolDependency.get().addPacketListener(new PacketAdapter(
                plugin.getPlugin(),
                ListenerPriority.NORMAL,
                PacketType.Play.Client.ITEM_NAME
        ) {
            @Override
            public void onPacketSending(PacketEvent event) {
            }

            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer container = event.getPacket();
                String text = container.getStrings().read(0);
                PlayerUser user = new PlayerUser(event.getPlayer());

                // Get inventory the player is viewing.
                CozyInventory inventoryInterface = InventoryManager.getFromViewer(user.getPlayer());
                if (inventoryInterface == null) return;

                for (ActionHandler actionHandler : ActionManager.actionHandlerList) {
                    actionHandler.onAnvilText(inventoryInterface, text, user);
                }
            }
        });
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    private void onInventoryClick(InventoryClickEvent event) {

        // Attempt to get the inventory as a registered inventory interface.
        CozyInventory inventory = InventoryManager.get(event.getInventory()).orElse(null);
        if (inventory == null) return;

        // Check if the slot is in the player's inventory.
        if (event.getRawSlot() > event.getInventory().getSize()
                && !(event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)) {

            return;
        }

        // Cancel event by default if nto placeable.
        if (!inventory.isPlaceable()) event.setCancelled(true);

        // Get the user.
        PlayerUser user = new PlayerUser((Player) event.getWhoClicked());

        // Call the method on inventory click for each action handler.
        for (ActionHandler actionHandler : ActionManager.actionHandlerList) {
            ActionResult result = actionHandler.onInventoryClick(inventory, user, event);

            if (result.isCancelTrue()) event.setCancelled(true);
            if (result.isCancelFalse()) event.setCancelled(false);
        }
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event) {
        
        // Attempt to get the inventory as a registered inventory interface.
        CozyInventory inventory = InventoryManager.get(event.getInventory()).orElse(null);
        if (inventory == null) return;

        // Get the user.
        PlayerUser user = new PlayerUser((Player) event.getPlayer());

        boolean notUnregister = false;

        // Call the method on inventory click for each action handler.
        for (ActionHandler actionHandler : ActionManager.actionHandlerList) {
            if (actionHandler.onInventoryClose(inventory, user, event)) notUnregister = true;
        }

        // If it can be unregistered and there are no viewers, close the inventory.
        if (!notUnregister && event.getInventory().getViewers().isEmpty() && !inventory.getStayActive()) {
            inventory.close();
        }
    }

    /**
     * Used to add an action handler to the action manager.
     *
     * @param actionHandler The instance of the action handler.
     */
    public static void addActionHandler(@NotNull ActionHandler actionHandler) {
        ActionManager.actionHandlerList.add(actionHandler);
    }
}
