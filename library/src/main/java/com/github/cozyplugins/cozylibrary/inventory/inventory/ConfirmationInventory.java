package com.github.cozyplugins.cozylibrary.inventory.inventory;

import com.github.cozyplugins.cozylibrary.inventory.CozyInventory;
import com.github.cozyplugins.cozylibrary.inventory.InventoryItem;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ClickAction;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ConfirmAction;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the confirmation inventory.
 * Plugins can use this interface to ask a player for confirmation.
 */
public class ConfirmationInventory extends CozyInventory {

    private final @NotNull List<ConfirmAction> actionList;

    /**
     * Creates a confirmation inventory.
     *
     * @param actionList The instance of the confirmation action list.
     */
    public ConfirmationInventory(@NotNull List<ConfirmAction> actionList) {
        super(27, actionList.get(0).getTitle());

        this.actionList = actionList;
    }

    /**
     * Used to create a confirmation inventory.
     *
     * @param action The instance of the confirmation action.
     *               This will return the result.
     */
    public ConfirmationInventory(@NotNull ConfirmAction action) {
        super(27, action.getTitle());

        this.actionList = new ArrayList<>();
        this.actionList.add(action);
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

                    for (ConfirmAction action : this.actionList) {
                        action.abort(user);
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

                    for (ConfirmAction action : this.actionList) {
                        action.confirm(user);
                    }
                })
        );
    }
}
