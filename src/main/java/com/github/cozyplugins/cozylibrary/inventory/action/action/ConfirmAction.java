package com.github.cozyplugins.cozylibrary.inventory.action.action;

import com.github.cozyplugins.cozylibrary.inventory.action.Action;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a confirmation action.
 * <p>
 * The player will be asked to confirm something
 * and the decision will be returned.
 * </p>
 */
public class ConfirmAction implements Action {

    private @NotNull String title;
    private @NotNull ValueAction confirm;
    private @NotNull ValueAction abort;

    public ConfirmAction() {
        this.title = "&7";
        this.confirm = (user) -> {};
        this.abort = (user) -> {};
    }

    public interface ValueAction {

        /**
         * When a value is entered.
         *
         * @param user The instance of the user.
         */
        void onValue(@NotNull PlayerUser user);
    }

    /**
     * Used to get the title of the confirmation inventory.
     *
     * @return The title.
     */
    public @NotNull String getTitle() {
        return title;
    }

    /**
     * Used to set the anvil's title.
     *
     * @param title The new title of the anvil.
     * @return This instance.
     */
    public @NotNull ConfirmAction setAnvilTitle(@NotNull String title) {
        this.title = title;
        return this;
    }

    /**
     * Used to set the confirmation action.
     *
     * @param valueAction The instance of the confirmation action.
     * @return This instance.
     */
    public @NotNull ConfirmAction setConfirm(@NotNull ValueAction valueAction) {
        this.confirm = valueAction;
        return this;
    }

    /**
     * Used to set the abort action.
     *
     * @param valueAction The instance of the abort action.
     * @return This instance.
     */
    public @NotNull ConfirmAction setAbort(@NotNull ValueAction valueAction) {
        this.abort = valueAction;
        return this;
    }

    /**
     * Called when the player chooses confirm.
     *
     * @param user The instance of the user.
     */
    public void confirm(@NotNull PlayerUser user) {
        this.confirm.onValue(user);
    }

    /**
     * Called when the player chooses abort.
     *
     * @param user The instance of the user.
     */
    public void abort(@NotNull PlayerUser user) {
        this.abort.onValue(user);
    }
}
