package com.github.cozyplugins.testplugin.inventorys;

import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.item.CozyItem;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class TestInventory extends InventoryInterface {

    public TestInventory() {
        super(InventoryType.ANVIL, "&8&lTest Inventory");
    }

    @Override
    public void onGenerate(PlayerUser player) {
        this.setItem(new CozyItem()
                .setMaterial(Material.ALLIUM)
                .setName("test")
        );
    }
}
