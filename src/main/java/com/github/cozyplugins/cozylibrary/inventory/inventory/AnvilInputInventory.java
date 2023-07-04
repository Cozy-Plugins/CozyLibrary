package com.github.cozyplugins.cozylibrary.inventory.inventory;

import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.inventory.InventoryItem;
import com.github.cozyplugins.cozylibrary.inventory.action.action.AnvilValueAction;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ClickAction;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents an anvil input inventory.
 * Used to get input from the user.
 */
public class AnvilInputInventory extends InventoryInterface {

    private final @NotNull List<AnvilValueAction> actionList;

    public AnvilInputInventory(@NotNull List<AnvilValueAction> actionList) {
        super(InventoryType.ANVIL, actionList.get(0).getAnvilTitle());

        this.actionList = actionList;
    }

    @Override
    protected void onGenerate(PlayerUser player) {

        // Text box.
        this.setItem(new InventoryItem()
                .setMaterial(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                .setName(actionList.get(0).getAnvilText())
        );

        // Back button.
        this.setItem(new InventoryItem()
                .setMaterial(Material.RED_STAINED_GLASS_PANE)
                .setName("&c&lBack")
                .addLore("&7Click to go back to the previous menu.")
                .addAction((ClickAction) (user, type) -> {
                    user.getPlayer().closeInventory();
                    for (AnvilValueAction action : this.actionList) {
                        action.onValue(null, user);
                    }
                })
        );

        // Confirm button.
        this.setItem(new InventoryItem()
                .setMaterial(Material.LIME_STAINED_GLASS_PANE)
                .setName("&a&lConfirm")
                .addLore("&7Click to confirm the value.")
                .addAction((ClickAction) (user, type) -> {
                    user.getPlayer().closeInventory();
                    AnvilInventory inventory = (AnvilInventory) this.getInventory();
                    for (AnvilValueAction action : this.actionList) {
                        action.onValue(inventory.getRenameText(), user);
                    }
                })
        );
    }
}
