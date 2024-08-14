package com.github.cozyplugins.cozylibrary.inventory.action.handler;

import com.github.cozyplugins.cozylibrary.inventory.CozyInventory;
import com.github.cozyplugins.cozylibrary.inventory.action.ActionHandler;
import com.github.cozyplugins.cozylibrary.inventory.action.ActionResult;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ClickActionWithResult;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents the click action with result handler.
 * Handles the {@link ClickActionWithResult} actions.
 */
public class ClickActionWithResultHandler implements ActionHandler {

    @Override
    public @NotNull ActionResult onInventoryClick(@NotNull CozyInventory inventoryInterface, @NotNull PlayerUser user, InventoryClickEvent event) {
        List<ClickActionWithResult> actionList = inventoryInterface.getActionList(event.getSlot(), ClickActionWithResult.class);

        ActionResult actionResult = new ActionResult();

        for (ClickActionWithResult action : actionList) {
            actionResult = action.onClick(user, event.getClick(), event.getInventory(), actionResult, event.getSlot(), event);
        }

        return actionResult;
    }

    @Override
    public boolean onInventoryClose(@NotNull CozyInventory inventoryInterface, @NotNull PlayerUser user, InventoryCloseEvent event) {
        return false;
    }

    @Override
    public void onAnvilText(@NotNull CozyInventory inventoryInterface, @NotNull String text, @NotNull PlayerUser user) {

    }
}
