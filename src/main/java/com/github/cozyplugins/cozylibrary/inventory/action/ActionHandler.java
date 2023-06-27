package com.github.cozyplugins.cozylibrary.inventory.action;

import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;

/**
 * <h1>Represents an action handler</h1>
 * Provides a handler with the inventory events.
 */
public interface ActionHandler {

    /**
     * <h1>Called when a inventory is clicked on</h1>
     * <li>Defaults to always cancel the event.</li>
     *
     * @param inventory The instance of the inventory.
     * @param slot      The slot that was clicked.
     * @param type      The type of click.
     * @return The Action result.
     */
    @NotNull
    ActionResult onInventoryClick(@NotNull InventoryInterface inventory, int slot, ClickType type, @NotNull PlayerUser user);
}
