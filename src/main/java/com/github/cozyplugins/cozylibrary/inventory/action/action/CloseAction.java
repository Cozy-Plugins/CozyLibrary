package com.github.cozyplugins.cozylibrary.inventory.action.action;

import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a player closes the inventory.
 */
public interface CloseAction {

    /**
     * Called when a player closes the inventory.
     *
     * @param user      The instance of the user.
     * @param inventory The instance of the inventory.
     * @return True if the inventory should not be un-registered.
     */
    boolean onClose(@NotNull PlayerUser user, @NotNull Inventory inventory);
}
