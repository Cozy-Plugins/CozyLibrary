package com.github.cozyplugins.cozylibrary.inventory.action.handler;

import com.github.cozyplugins.cozylibrary.inventory.CozyInventory;
import com.github.cozyplugins.cozylibrary.inventory.action.ActionHandler;
import com.github.cozyplugins.cozylibrary.inventory.action.ActionResult;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ClickAction;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * <h1>Represents the click action handler.</h1>
 */
public class ClickActionHandler implements ActionHandler {

    @Override
    public @NotNull ActionResult onInventoryClick(@NotNull CozyInventory inventoryInterface, @NotNull PlayerUser user, InventoryClickEvent event) {
        List<ClickAction> actionList = inventoryInterface.getActionList(event.getSlot(), ClickAction.class);

        for (ClickAction action : actionList) {
            action.onClick(user, event.getClick(), event.getInventory());
        }

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
