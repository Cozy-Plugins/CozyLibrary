package com.github.cozyplugins.cozylibrary.inventory.action;

import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.inventory.InventoryManager;
import com.github.cozyplugins.cozylibrary.inventory.action.handler.AnvilValueActionHandler;
import com.github.cozyplugins.cozylibrary.inventory.action.handler.ClickActionHandler;
import com.github.cozyplugins.cozylibrary.inventory.action.handler.PlaceActionHandler;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Represents the action manager</h1>
 * Used to register the inventory action events.
 */
public class ActionManager implements Listener {

    private final @NotNull List<ActionHandler> actionHandlerList;

    /**
     * <h1>Used to create a action manager</h1>
     *
     * @param plugin The instance of the base plugin.
     */
    public ActionManager(CozyPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        this.actionHandlerList = new ArrayList<>();
        this.actionHandlerList.add(new ClickActionHandler());
        this.actionHandlerList.add(new PlaceActionHandler());
        this.actionHandlerList.add(new AnvilValueActionHandler());
    }

    @EventHandler(ignoreCancelled = true)
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
        for (ActionHandler actionHandler : this.actionHandlerList) {
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
        for (ActionHandler actionHandler : this.actionHandlerList) {
            actionHandler.onInventoryClose(inventory, user, event);
        }

        // If there are no viewers, close the inventory.
        if (event.getInventory().getViewers().isEmpty() && !inventory.getStayActive()) {
            inventory.close();
        }
    }
}
