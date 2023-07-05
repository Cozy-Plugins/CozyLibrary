package com.github.cozyplugins.cozylibrary.inventory.inventory;

import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.inventory.InventoryItem;
import com.github.cozyplugins.cozylibrary.inventory.action.action.AnvilValueAction;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ClickAction;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents an anvil input inventory.
 * Used to get input from the user.
 */
public class AnvilInputInventory extends InventoryInterface {

    private final @NotNull List<AnvilValueAction> actionList;
    private String input;

    /**
     * Used to create an anvil input.
     *
     * @param actionList The instance of the anvil action list.
     */
    public AnvilInputInventory(@NotNull List<AnvilValueAction> actionList) {
        super(InventoryType.ANVIL, actionList.get(0).getAnvilTitle());

        this.actionList = actionList;
    }

    @Override
    protected void onGenerate(PlayerUser player) {

        // Back button.
        this.setItem(new InventoryItem()
                .setMaterial(Material.RED_STAINED_GLASS_PANE)
                .setName("&r")
                .addLore("&c&lBack")
                .addLore("&7Click to go back to the previous menu.")
                .addAction((ClickAction) (user, type, inventory) -> {
                    user.getPlayer().closeInventory();
                    for (AnvilValueAction action : this.actionList) {
                        action.onValue(null, user);
                    }
                })
                .addSlot(0)
        );

        this.setItem(new InventoryItem()
                .setMaterial(Material.LIME_STAINED_GLASS_PANE)
                .setName("&r")
                .addLore("&a&lConfirm")
                .addLore("&7Click to confirm the value.")
                .addAction((ClickAction) (user, type, inventory) -> {
                    user.getPlayer().closeInventory();

                    for (AnvilValueAction action : this.actionList) {
                        action.onValue(this.input, user);
                    }
                })
                .addSlot(1)
        );
    }

    /**
     * Used to save what is in the input box.
     * This will not set the text.
     *
     * @param input The input text.
     */
    public void setInput(String input) {
        this.input = input;
    }
}
