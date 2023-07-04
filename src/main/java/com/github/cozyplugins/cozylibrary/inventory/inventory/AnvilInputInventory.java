package com.github.cozyplugins.cozylibrary.inventory.inventory;

import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.inventory.InventoryItem;
import com.github.cozyplugins.cozylibrary.inventory.action.action.AnvilValueAction;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ClickAction;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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

        // Back button.
        this.setItem(new InventoryItem()
                .setMaterial(Material.ENCHANTED_BOOK)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
                .setName("")
                .addLore("&c&lBack")
                .addLore("&7Click to go back to the previous menu.")
                .addAction((ClickAction) (user, type) -> {
                    user.getPlayer().closeInventory();
                    for (AnvilValueAction action : this.actionList) {
                        action.onValue(null, user);
                    }
                })
                .addSlot(0)
        );

        // Confirm button.
        this.setItem(new InventoryItem()
                .setMaterial(Material.ENCHANTED_BOOK)
                .addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                .setName("")
                .addLore("&a&lConfirm")
                .addLore("&7Click to confirm the value.")
                .addAction((ClickAction) (user, type) -> {
                    user.getPlayer().closeInventory();

                    if (this.getInventory().getItem(2) == null) return;
                    if (this.getInventory().getItem(2).getItemMeta() == null) return;
                    String name = this.getInventory().getItem(2).getItemMeta().getDisplayName();

                    for (AnvilValueAction action : this.actionList) {
                        action.onValue(name, user);
                    }
                })
                .addSlot(1)
        );
    }
}
