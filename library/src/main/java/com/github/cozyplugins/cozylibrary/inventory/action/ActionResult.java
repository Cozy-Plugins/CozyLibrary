package com.github.cozyplugins.cozylibrary.inventory.action;

import org.jetbrains.annotations.NotNull;

/**
 * <h1>Represents a inventory action result.</h1>
 */
public class ActionResult {

    private boolean changeCancelled = false;
    private boolean cancel = false;

    /**
     * <h1>Used to change the state of the event.</h1>
     *
     * @param cancel True to cancel the event.
     * @return This instance.
     */
    public @NotNull ActionResult setCancelled(boolean cancel) {
        this.changeCancelled = true;
        this.cancel = cancel;
        return this;
    }

    /**
     * <h1>Used to check if set cancelled should be set true.</h1>
     *
     * @return True if the event should be cancelled.
     */
    public boolean isCancelTrue() {
        return this.changeCancelled && this.cancel;
    }

    /**
     * <h1>Used to check if set cancelled should be set false.</h1>
     *
     * @return True if the event should not be cancelled.
     */
    public boolean isCancelFalse() {
        return this.changeCancelled && !this.cancel;
    }
}
