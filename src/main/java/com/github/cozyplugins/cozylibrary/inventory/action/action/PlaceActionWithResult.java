package com.github.cozyplugins.cozylibrary.inventory.action.action;

import com.github.cozyplugins.cozylibrary.inventory.action.Action;
import com.github.cozyplugins.cozylibrary.item.CozyItem;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public interface PlaceActionWithResult extends Action {

    /**
     * Called when someone places an item in the slot.
     *
     * @param user The instance of the user.
     * @param item The instance of the item.
     * @param event The instance of the event.
     */
    void onPlace(@NotNull PlayerUser user, @NotNull CozyItem item, @NotNull InventoryClickEvent event);
}
