package com.github.cozyplugins.cozylibrary.inventory_v2;

import com.github.cozyplugins.cozylibrary.inventory_v2.component.View;
import com.github.cozyplugins.cozylibrary.task.TaskContainer;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public abstract class InventoryDisplay extends TaskContainer {

    private final @NotNull Inventory inventory;
    private final @NotNull InventoryType type;
    private final @NotNull String title;

    public InventoryDisplay(final @NotNull InventoryType type, final @NotNull String title) {
        this.inventory = type.createInventory(title);
        this.type = type;
        this.title = title;
    }

    public abstract @NotNull View getLayout(@NotNull PlayerUser user);

    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    public @NotNull InventoryType getType() {
        return this.type;
    }

    public @NotNull String getTitle() {
        return this.title;
    }
}
