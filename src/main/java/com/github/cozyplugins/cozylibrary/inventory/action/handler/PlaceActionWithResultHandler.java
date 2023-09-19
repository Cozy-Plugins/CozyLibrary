package com.github.cozyplugins.cozylibrary.inventory.action.handler;

import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.inventory.action.ActionHandler;
import com.github.cozyplugins.cozylibrary.inventory.action.ActionResult;
import com.github.cozyplugins.cozylibrary.inventory.action.action.PlaceActionWithResult;
import com.github.cozyplugins.cozylibrary.item.CozyItem;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents the place action with result handler.
 */
public class PlaceActionWithResultHandler implements ActionHandler {

    @Override
    public @NotNull ActionResult onInventoryClick(@NotNull InventoryInterface inventoryInterface, @NotNull PlayerUser user, InventoryClickEvent event) {
        List<PlaceActionWithResult> actionList = inventoryInterface.getActionList(event.getSlot(), PlaceActionWithResult.class);

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
        for (PlaceActionWithResult action : actionList) {
            action.onPlace(user, item, event);
        }

        return new ActionResult().setCancelled(false);
    }

    @Override
    public boolean onInventoryClose(@NotNull InventoryInterface inventoryInterface, @NotNull PlayerUser user, InventoryCloseEvent event) {
        return false;
    }

    @Override
    public void onAnvilText(@NotNull InventoryInterface inventoryInterface, @NotNull String text, @NotNull PlayerUser user) {

    }
}
