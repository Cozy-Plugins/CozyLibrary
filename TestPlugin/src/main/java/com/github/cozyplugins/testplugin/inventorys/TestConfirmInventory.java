package com.github.cozyplugins.testplugin.inventorys;

import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.inventory.InventoryItem;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ConfirmAction;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

public class TestConfirmInventory extends InventoryInterface {

    public TestConfirmInventory() {
        super(InventoryType.BARREL, "&8&lTest Inventory");
    }

    @Override
    protected void onGenerate(PlayerUser player) {

        this.setItem(new InventoryItem()
                .setMaterial(Material.LIME_STAINED_GLASS_PANE)
                .setName("&a&lConfirm")
                .addAction(new ConfirmAction() {
                    @Override
                    public @NotNull String getTitle() {
                        return "&8&lConfirm Action";
                    }

                    @Override
                    public void onConfirm(@NotNull PlayerUser user) {
                        user.sendMessage("Confirmed");
                        TestConfirmInventory testConfirmInventory = new TestConfirmInventory();
                        testConfirmInventory.open(user.getPlayer());
                    }

                    @Override
                    public void onAbort(@NotNull PlayerUser user) {
                        user.sendMessage("Aborted");
                        TestConfirmInventory testConfirmInventory = new TestConfirmInventory();
                        testConfirmInventory.open(user.getPlayer());
                    }
                })
        );
    }
}
