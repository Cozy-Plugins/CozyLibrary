package com.github.cozyplugins.testplugin.inventorys;

import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.inventory.InventoryItem;
import com.github.cozyplugins.cozylibrary.inventory.action.action.AnvilValueAction;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InputInventory extends InventoryInterface {

    private final String text;

    public InputInventory(String text) {
        super(InventoryType.BARREL, "&8&lInput Inventory Test");
        this.text = text;
    }

    @Override
    protected void onGenerate(PlayerUser player) {
        this.setItem(new InventoryItem()
                .setMaterial(Material.LIME_STAINED_GLASS_PANE)
                .setName(this.text)
                .addAction(new AnvilValueAction() {
                    @Override
                    public @NotNull String getAnvilTitle() {
                        return "&8&lChange the text";
                    }

                    @Override
                    public void onValue(@Nullable String value, @NotNull PlayerUser user) {
                        if (value == null) value = text;

                        InputInventory inventory = new InputInventory(value);
                        inventory.open(user.getPlayer());
                    }
                })
        );
    }
}
