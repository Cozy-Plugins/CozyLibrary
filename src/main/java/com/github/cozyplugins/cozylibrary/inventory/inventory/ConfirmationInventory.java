package com.github.cozyplugins.cozylibrary.inventory.inventory;

import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.inventory.InventoryItem;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ClickAction;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ConfirmAction;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents the confirmation inventory.
 */
public class ConfirmationInventory extends InventoryInterface {

    private List<ConfirmAction> actionList;
    private final ConfirmResult result;

    /**
     * Creates a confirmation inventory.
     *
     * @param actionList The instance of the confirmation action list.
     */
    public ConfirmationInventory(@NotNull List<ConfirmAction> actionList) {
        super(27, actionList.get(0).getTitle());

        this.actionList = actionList;
        this.result = null;
    }

    public ConfirmationInventory(@NotNull ConfirmResult result) {
        super(27, "&8&lConfirm");

        this.result = result;
    }

    /**
     * Represents a confirm result.
     */
    public interface ConfirmResult {

        /**
         * Called when a player has chosen a button.
         *
         * @param confirm True if the player has pressed the confirmation button.
         */
        void onConfirm(boolean confirm);
    }

    @Override
    protected void onGenerate(PlayerUser player) {
        this.setItem(new InventoryItem()
                .setMaterial(Material.RED_STAINED_GLASS_PANE)
                .setName("&c&lAbort")
                .addLore("&7Click to abort confirmation.")
                .addSlotRange(0, 2)
                .addSlotRange(9, 11)
                .addSlotRange(18, 20)
                .addAction((ClickAction) (user, type, inventory) -> {
                    user.getPlayer().closeInventory();

                    if (this.result != null) {
                        this.result.onConfirm(false);
                        return;
                    }

                    for (ConfirmAction action : this.actionList) {
                        action.onAbort(user);
                    }
                })
        );

        this.setItem(new InventoryItem()
                .setMaterial(Material.LIME_STAINED_GLASS_PANE)
                .setName("&a&lConfirm")
                .addLore("&7Click to confirm.")
                .addSlotRange(6, 8)
                .addSlotRange(15, 17)
                .addSlotRange(24, 26)
                .addAction((ClickAction) (user, type, inventory) -> {
                    user.getPlayer().closeInventory();

                    if (this.result != null) {
                        this.result.onConfirm(true);
                        return;
                    }

                    for (ConfirmAction action : this.actionList) {
                        action.onConfirm(user);
                    }
                })
        );
    }
}
