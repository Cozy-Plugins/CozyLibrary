package com.github.cozyplugins.cozylibrary.inventory.action.action;

import com.github.cozyplugins.cozylibrary.inventory.action.Action;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * <h1>Represents a click action</h1>
 * This action is triggered when a player clicks the item
 * with any type of click.
 */
public interface ClickAction extends Action {

    /**
     * <h1>Called when someone clicks the item</h1>
     *
     * @param user      The instance of the user.
     * @param type      The type of click.
     * @param inventory The instance of the inventory.
     */
    void onClick(@NotNull PlayerUser user, ClickType type, @NotNull Inventory inventory);
}
