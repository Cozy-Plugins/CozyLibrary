package com.github.cozyplugins.cozylibrary.inventory_v2.component;

import com.github.cozyplugins.cozylibrary.inventory_v2.InventoryDisplay;
import com.github.cozyplugins.cozylibrary.inventory_v2.listener.ClickListener;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.jetbrains.annotations.NotNull;

public interface Component {

    void generate(@NotNull InventoryDisplay inv, @NotNull PlayerUser user);

    @NotNull Component onClick(@NotNull ClickListener listener);
}
