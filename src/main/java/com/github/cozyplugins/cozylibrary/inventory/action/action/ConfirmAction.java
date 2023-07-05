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
public interface ConfirmAction extends Action {

    /**
     * Used to get the title of the confirmation inventory.
     *
     * @return The title.
     */
    @NotNull String getTitle();

    /**
     * Called when the player chooses confirm.
     *
     * @param user The instance of the user.
     */
    void onConfirm(@NotNull PlayerUser user);

    /**
     * Called when the player chooses abort.
     *
     * @param user The instance of the user.
     */
    void onAbort(@NotNull PlayerUser user);
}
