package com.github.cozyplugins.cozylibrary.inventory.action.action;

import com.github.cozyplugins.cozylibrary.inventory.action.Action;
import com.github.cozyplugins.cozylibrary.inventory.action.ActionResult;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link ClickAction} but each action can change the {@link ActionResult}.
 */
public interface ClickActionWithResult extends Action {

    /**
     * <h1>Called when someone clicks the item</h1>
     *
     * @param user          The instance of the user.
     * @param type          The type of click.
     * @param inventory     The instance of the inventory.
     * @param currentResult The current instance of the action result.
     * @param slot          The slot clicked.
     * @param event         The instance of the event.
     */
    @NotNull ActionResult onClick(
            @NotNull PlayerUser user,
            ClickType type,
            @NotNull Inventory inventory,
            @NotNull ActionResult currentResult,
            int slot,
            @NotNull InventoryClickEvent event
    );
}
