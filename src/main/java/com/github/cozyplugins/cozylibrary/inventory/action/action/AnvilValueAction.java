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
public class AnvilValueAction implements Action {

    private @NotNull String anvilTitle;
    private @NotNull ValueAction valueAction;

    /**
     * Used to create an anvil value action.
     */
    public AnvilValueAction() {
        this.anvilTitle = "&7";
        this.valueAction = (value, user) -> {};
    }

    /**
     * Represents a value action interface for
     * this anvil value action.
     */
    public interface ValueAction {

        /**
         * Called when the anvil has been exited. Ether by
         * confirmation or clicking the back button.
         * <p>
         * If the back button is pressed, the value will be null.
         * </p>
         *
         * @param value The value that was given.
         * @param user The instance of the user.
         */
        void onValue(@Nullable String value, @NotNull PlayerUser user);
    }

    /**
     * Used to get the title of the anvil.
     *
     * @return The title of the inventory.
     */
    public @NotNull String getAnvilTitle() {
        return this.anvilTitle;
    }

    /**
     * Used to set the anvil's title.
     *
     * @param title The new title of the anvil.
     * @return This instance.
     */
    public @NotNull AnvilValueAction setAnvilTitle(@NotNull String title) {
        this.anvilTitle = title;
        return this;
    }

    /**
     * Used to set the action when the anvil action is called.
     *
     * @param valueAction The instance of a value action.
     * @return This instance.
     */
    public @NotNull AnvilValueAction setAction(@NotNull ValueAction valueAction) {
        this.valueAction = valueAction;
        return this;
    }

    /**
     * Called when the anvil has been exited. Ether by
     * confirmation or clicking the back button.
     * <p>
     * If the back button is pressed, the value will be null.
     * </p>
     *
     * @param value The value that was given.
     * @param user The instance of the user.
     */
    public void call(@Nullable String value, @NotNull PlayerUser user) {
        this.valueAction.onValue(value, user);
    }
}
