package com.github.cozyplugins.cozylibrary.inventory.inventory;

import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.jetbrains.annotations.NotNull;

public class FileEditor extends InventoryInterface {

    public FileEditor(int size, @NotNull String title) {
        super(size, title);
    }

    @Override
    protected void onGenerate(PlayerUser player) {

    }
}
