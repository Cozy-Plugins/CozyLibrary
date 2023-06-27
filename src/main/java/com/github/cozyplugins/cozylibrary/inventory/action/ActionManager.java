package com.github.cozyplugins.cozylibrary.inventory.action;

import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.inventory.InventoryManager;
import com.github.cozyplugins.cozylibrary.inventory.action.handler.ClickActionHandler;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
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
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {

        // Attempt to get the inventory as a registered inventory interface.
        InventoryInterface inventory = InventoryManager.get(event.getInventory());
        if (inventory == null) return;

        // Cancel event by default.
        event.setCancelled(true);

        // Get the user.
        PlayerUser user = new PlayerUser((Player) event.getWhoClicked());

        // Call the method on inventory click for each action handler.
        for (ActionHandler actionHandler : this.actionHandlerList) {
            ActionResult result = actionHandler.onInventoryClick(inventory, event.getSlot(), event.getClick(), user);

            if (result.isCancelTrue()) event.setCancelled(true);
            if (result.isCancelFalse()) event.setCancelled(false);
        }
    }
}
