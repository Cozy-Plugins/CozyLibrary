package com.github.cozyplugins.testplugin.inventorys;

import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.inventory.InventoryItem;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ClickAction;
import com.github.cozyplugins.cozylibrary.inventory.action.action.PlaceAction;
import com.github.cozyplugins.cozylibrary.item.CozyItem;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;

public class TestInventory extends InventoryInterface {

    public TestInventory() {
        super(InventoryType.CHEST, "&8&lTest Inventory");
    }

    @Override
    public void onGenerate(PlayerUser player) {
        this.setItem(new CozyItem()
                .setMaterial(Material.ALLIUM)
                .setName("test")
        );

        this.setItem(new InventoryItem()
                .addAction((ClickAction) (user, type, inventory) -> user.sendMessage("&f-> &6Hi there!"))
                .setMaterial(Material.LIME_STAINED_GLASS_PANE)
                .setName("&6&lClick to send a message")
                .addLore("&7Sends you a message.")
        );

        this.setItem(new InventoryItem()
                .setMaterial(Material.GOLDEN_HELMET)
                .addAction((PlaceAction) (user, item) -> System.out.println(item.getMaterial()))
                .setName("Replaceable")
        );
    }
}
