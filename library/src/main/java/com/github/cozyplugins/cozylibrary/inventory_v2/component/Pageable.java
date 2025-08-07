package com.github.cozyplugins.cozylibrary.inventory_v2.component;

import com.github.cozyplugins.cozylibrary.inventory_v2.InventoryDisplay;
import com.github.cozyplugins.cozylibrary.inventory_v2.listener.ClickListener;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public abstract class Pageable implements Component {

    public interface Handler<T> {
        @NotNull Item handle(@NotNull T t);
    }

    public static <T> @NotNull Pageable as(@NotNull List<T> list, @NotNull Handler<T> handler) {
        return new Pageable() {
            @Override
            public void generate(@NotNull InventoryDisplay inv, @NotNull PlayerUser user) {

            }

            @Override
            public @NotNull Component onClick(@NotNull ClickListener listener) {
                return null;
            }
        };
    }
}
