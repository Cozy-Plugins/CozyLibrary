package com.github.cozyplugins.cozylibrary.inventory_v2.component;

import com.github.cozyplugins.cozylibrary.inventory_v2.InventoryDisplay;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.jetbrains.annotations.NotNull;

public abstract class Item implements Component {

    public static @NotNull Item as() {
        return new Item() {
            @Override
            public void generate(@NotNull InventoryDisplay inv, @NotNull PlayerUser user) {

            }
        };
    }
}
