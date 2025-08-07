package com.github.cozyplugins.cozylibrary.inventory_v2.testing;

import com.github.cozyplugins.cozylibrary.inventory_v2.InventoryDisplay;
import com.github.cozyplugins.cozylibrary.inventory_v2.InventoryType;
import com.github.cozyplugins.cozylibrary.inventory_v2.component.*;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TestDisplay extends InventoryDisplay {

    public TestDisplay() {
        super(InventoryType.GENERIC_5X9, "Test Inventory");
    }

    @Override
    public @NotNull View getLayout(@NotNull PlayerUser user) {
        return View.as(

            // Pageable Items.
            View.as("padding-t-1 padding-y-1",
                Pageable.as(List.of(), (t) -> Item.as())
            ).onClick((event) -> {

            }),

            // Footer Navbar
            Footer.as("center",
                Pageable.previous(Item.as()),
                Empty.as(),
                Pageable.next(Item.as())
            )
        );
    }

    private @NotNull View test() {
        return View.as(
            View.as("padding-x-1",
                Pageable.as(List.of(), (t) -> Item.as()),
                Empty.row(),
                Pageable.as(List.of(), (t) -> Item.as())
            ),

            // Footer Navbar
            Footer.as("center",
                Pageable.previous(Item.as()),
                Empty.as(),
                Pageable.next(Item.as())
            )
        );
    }
}
