package com.github.cozyplugins.cozylibrary.inventory_v2.listener;

import com.github.cozyplugins.cozylibrary.inventory_v2.action.ClickEvent;
import org.jetbrains.annotations.NotNull;

public interface ClickListener {

    void onClick(final @NotNull ClickEvent event);
}
