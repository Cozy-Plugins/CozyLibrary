package com.github.cozyplugins.cozylibrary.inventory.action.handler;

import com.github.cozyplugins.cozylibrary.inventory.CozyInventory;
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
    public @NotNull ActionResult onInventoryClick(@NotNull CozyInventory inventoryInterface, @NotNull PlayerUser user, InventoryClickEvent event) {
        List<AnvilValueAction> actionList = inventoryInterface.getActionList(event.getSlot(), AnvilValueAction.class);
        if (actionList.isEmpty()) return new ActionResult();

        new AnvilInputInventory(actionList).open(user.getPlayer());

        return new ActionResult();
    }

    @Override
    public boolean onInventoryClose(@NotNull CozyInventory inventoryInterface, @NotNull PlayerUser user, InventoryCloseEvent event) {
        return false;
    }

    @Override
    public void onAnvilText(@NotNull CozyInventory inventoryInterface, @NotNull String text, @NotNull PlayerUser user) {
        AnvilInputInventory anvilInputInventory = (AnvilInputInventory) inventoryInterface;

        // Set input value.
        anvilInputInventory.setInput(text);
    }
}
