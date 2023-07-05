package com.github.cozyplugins.cozylibrary.inventory.action;

import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
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
     * @param inventoryInterface The instance of the inventory.
     * @param user               The instance of the user.
     * @param event              The instance of the event.
     * @return The Action result.
     */
    @NotNull
    ActionResult onInventoryClick(@NotNull InventoryInterface inventoryInterface, @NotNull PlayerUser user, InventoryClickEvent event);

    /**
     * Called when an inventory is closed.
     *
     * @param inventoryInterface The instance of the inventory.
     * @param user               The instance of the user.
     * @param event              The instance of the event.
     */
    void onInventoryClose(@NotNull InventoryInterface inventoryInterface, @NotNull PlayerUser user, InventoryCloseEvent event);

    /**
     * Called everytime a player edits a character in an anvil.
     *
     * @param inventoryInterface The instance of the inventory.
     * @param text               The text changed to.
     * @param user               The instance of the user.
     */
    void onAnvilText(@NotNull InventoryInterface inventoryInterface, @NotNull String text, @NotNull PlayerUser user);
}
