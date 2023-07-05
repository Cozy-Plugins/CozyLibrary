package com.github.cozyplugins.cozylibrary.inventory.action;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.cozylibrary.dependency.ProtocolDependency;
import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.inventory.InventoryManager;
import com.github.cozyplugins.cozylibrary.inventory.action.handler.AnvilValueActionHandler;
import com.github.cozyplugins.cozylibrary.inventory.action.handler.ClickActionHandler;
import com.github.cozyplugins.cozylibrary.inventory.action.handler.PlaceActionHandler;
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
public class ActionManager implements Listener {

    private static @NotNull List<ActionHandler> actionHandlerList;

    /**
     * <h1>Used to create a action manager</h1>
     *
     * @param plugin The instance of the base plugin.
     */
    public ActionManager(CozyPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        ActionManager.actionHandlerList = new ArrayList<>();
        ActionManager.actionHandlerList.add(new ClickActionHandler());
        ActionManager.actionHandlerList.add(new PlaceActionHandler());
        ActionManager.actionHandlerList.add(new AnvilValueActionHandler());

        if (!ProtocolDependency.isEnabled()) return;

        // Setup packet listeners.
        ProtocolDependency.get().addPacketListener(new PacketAdapter(
                CozyPlugin.getPlugin(),
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
                InventoryInterface inventoryInterface = InventoryManager.getFromViewer(user.getPlayer());
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
        InventoryInterface inventory = InventoryManager.get(event.getInventory());
        if (inventory == null) return;

        // Check if the slot is in the player's inventory.
        if (event.getRawSlot() > event.getInventory().getSize()
                && !(event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)) {

            return;
        }

        // Cancel event by default.
        event.setCancelled(true);

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
        InventoryInterface inventory = InventoryManager.get(event.getInventory());
        if (inventory == null) return;

        // Get the user.
        PlayerUser user = new PlayerUser((Player) event.getPlayer());

        // Call the method on inventory click for each action handler.
        for (ActionHandler actionHandler : ActionManager.actionHandlerList) {
            actionHandler.onInventoryClose(inventory, user, event);
        }

        // If there are no viewers, close the inventory.
        if (event.getInventory().getViewers().isEmpty() && !inventory.getStayActive()) {
            inventory.close();
        }
    }
}
