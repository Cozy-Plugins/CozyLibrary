package com.github.cozyplugins.cozylibrary.inventory.action.handler;

import com.github.cozyplugins.cozylibrary.inventory.CozyInventory;
import com.github.cozyplugins.cozylibrary.inventory.action.ActionHandler;
import com.github.cozyplugins.cozylibrary.inventory.action.ActionResult;
import com.github.cozyplugins.cozylibrary.inventory.action.action.PlaceAction;
import com.github.cozyplugins.cozylibrary.item.CozyItem;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * <h1>Represents the place action handler.</h1>
 */
public class PlaceActionHandler implements ActionHandler {

    @Override
    public @NotNull ActionResult onInventoryClick(@NotNull CozyInventory inventoryInterface, @NotNull PlayerUser user, InventoryClickEvent event) {
        List<PlaceAction> actionList = inventoryInterface.getActionList(event.getSlot(), PlaceAction.class);

        // Check if there are any actions.
        if (actionList.isEmpty()) return new ActionResult();

        // Get the item in the slot.
        CozyItem item = new CozyItem(Material.AIR);
        if (event.getCursor() != null) {
            item = new CozyItem(event.getCursor());
        }

        // Check if the player did a hotbar swap.
        if (event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD
                || event.getAction() == InventoryAction.HOTBAR_SWAP) {

            item = user.getInventoryItem(event.getHotbarButton());
        }

        // Execute the actions.
        for (PlaceAction action : actionList) {
            action.onPlace(user, item);
        }

        return new ActionResult().setCancelled(false);
    }

    @Override
    public boolean onInventoryClose(@NotNull CozyInventory inventoryInterface, @NotNull PlayerUser user, InventoryCloseEvent event) {
        return false;
    }

    @Override
    public void onAnvilText(@NotNull CozyInventory inventoryInterface, @NotNull String text, @NotNull PlayerUser user) {

    }
}
