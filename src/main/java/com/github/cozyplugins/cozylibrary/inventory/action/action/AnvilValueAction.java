package com.github.cozyplugins.cozylibrary.inventory.action.action;

import com.github.cozyplugins.cozylibrary.inventory.action.Action;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an anvil value action.
 * <p>
 * When the item is clicked, an anvil inventory will open
 * </p>
 */
public interface AnvilValueAction extends Action {

    /**
     * Used to get the title of the anvil.
     *
     * @return The title of the inventory.
     */
    @NotNull String getAnvilTitle();

    /**
     * Called when the anvil has been exited. Ether by
     * confirmation or clicking the back button.
     * <p>
     * If the back button is pressed, the value will be null.
     * </p>
     *
     * @param value The value that was given.
     */
    void onValue(@Nullable String value, @NotNull PlayerUser user);
}
