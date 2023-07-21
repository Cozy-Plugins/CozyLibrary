package com.github.cozyplugins.cozylibrary.inventory.action.handler;

import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.inventory.action.ActionHandler;
import com.github.cozyplugins.cozylibrary.inventory.action.ActionResult;
import com.github.cozyplugins.cozylibrary.inventory.action.action.CloseAction;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the close action handler.
 */
public class CloseActionHandler implements ActionHandler {

    @Override
    public @NotNull ActionResult onInventoryClick(@NotNull InventoryInterface inventoryInterface, @NotNull PlayerUser user, InventoryClickEvent event) {
        return new ActionResult();
    }

    @Override
    public boolean onInventoryClose(@NotNull InventoryInterface inventoryInterface, @NotNull PlayerUser user, InventoryCloseEvent event) {
        boolean notUnregister = false;

        for (CloseAction closeAction : inventoryInterface.getCloseActionList()) {
            if (closeAction.onClose(user, event.getInventory())) notUnregister = true;
        }

        return notUnregister;
    }

    @Override
    public void onAnvilText(@NotNull InventoryInterface inventoryInterface, @NotNull String text, @NotNull PlayerUser user) {

    }
}
