package com.github.cozyplugins.cozylibrary.inventory.action.handler;

import com.github.cozyplugins.cozylibrary.inventory.CozyInventory;
import com.github.cozyplugins.cozylibrary.inventory.action.ActionHandler;
import com.github.cozyplugins.cozylibrary.inventory.action.ActionResult;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ConfirmAction;
import com.github.cozyplugins.cozylibrary.inventory.inventory.ConfirmationInventory;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents the confirmation action.
 */
public class ConfirmActionHandler implements ActionHandler {

    @Override
    public @NotNull ActionResult onInventoryClick(@NotNull CozyInventory inventoryInterface, @NotNull PlayerUser user, InventoryClickEvent event) {
        List<ConfirmAction> actionList = inventoryInterface.getActionList(event.getSlot(), ConfirmAction.class);
        if (actionList.isEmpty()) return new ActionResult();

        new ConfirmationInventory(actionList).open(user.getPlayer());

        return new ActionResult();
    }

    @Override
    public boolean onInventoryClose(@NotNull CozyInventory inventoryInterface, @NotNull PlayerUser user, InventoryCloseEvent event) {
        return false;
    }

    @Override
    public void onAnvilText(@NotNull CozyInventory inventoryInterface, @NotNull String text, @NotNull PlayerUser user) {

    }
}
