package com.github.cozyplugins.cozylibrary.inventory.action.handler;

import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.inventory.action.ActionHandler;
import com.github.cozyplugins.cozylibrary.inventory.action.ActionResult;
import com.github.cozyplugins.cozylibrary.inventory.action.action.AnvilValueAction;
import com.github.cozyplugins.cozylibrary.inventory.inventory.AnvilInputInventory;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents the anvil value action handler.
 * The anvil value action will open an anvil inventory.
 * The player will then be instructed to type the value and grab the item.
 * This value will be returned to the developer.
 */
public class AnvilValueActionHandler implements ActionHandler {

    @Override
    public @NotNull ActionResult onInventoryClick(@NotNull InventoryInterface inventory, @NotNull PlayerUser user, InventoryClickEvent event) {
        List<AnvilValueAction> actionList = inventory.getActionList(event.getSlot(), AnvilValueAction.class);

        new AnvilInputInventory(actionList).open(user.getPlayer());

        return new ActionResult();
    }

    @Override
    public void onInventoryClose(@NotNull InventoryInterface inventory, @NotNull PlayerUser user, InventoryCloseEvent event) {

    }
}
