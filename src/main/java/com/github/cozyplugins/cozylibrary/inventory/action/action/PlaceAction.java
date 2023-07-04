package com.github.cozyplugins.cozylibrary.inventory.action.action;

import com.github.cozyplugins.cozylibrary.inventory.action.Action;
import com.github.cozyplugins.cozylibrary.item.CozyItem;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.jetbrains.annotations.NotNull;

/**
 * <h1>Represents a place action</h1>
 * This action is triggered when a item
 * is placed in the slot.
 */
public interface PlaceAction extends Action {

    /**
     * <h1>Called when someone places an item in the slot</h1>
     *
     * @param user The instance of the user.
     * @param item The instance of the item.
     */
    void onPlace(@NotNull PlayerUser user, @NotNull CozyItem item);
}
