package com.github.cozyplugins.cozylibrary.placeholder;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a placeholder in this plugin.
 */
public interface Placeholder {

    /**
     * Used to get the identifier of the placeholder.
     *
     * @return The placeholder identifier.
     */
    @NotNull
    String getIdentifier();

    /**
     * Used to get the value of the placeholder.
     *
     * @param player The instance of the player.
     * @param params The full placeholder parameters.
     * @return The value to replace with.
     */
    @NotNull
    String getValue(@Nullable Player player, @NotNull String params);
}
