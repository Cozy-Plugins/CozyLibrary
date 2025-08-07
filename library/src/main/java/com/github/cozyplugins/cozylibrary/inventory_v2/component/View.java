package com.github.cozyplugins.cozylibrary.inventory_v2.component;

import com.github.cozyplugins.cozylibrary.inventory_v2.InventoryDisplay;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.jetbrains.annotations.NotNull;

public abstract class View implements Component {

    public static @NotNull View as(@NotNull Component... views) {
        return new View() {
            @Override
            public void generate(@NotNull InventoryDisplay inv, @NotNull PlayerUser user) {

            }
        };
    }

    public static @NotNull View as(@NotNull String formatting, @NotNull Component... views) {
        return new View() {
            @Override
            public void generate(@NotNull InventoryDisplay inv, @NotNull PlayerUser user) {

            }
        };
    }
}
