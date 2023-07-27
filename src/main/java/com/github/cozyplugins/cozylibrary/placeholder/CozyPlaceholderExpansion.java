package com.github.cozyplugins.cozylibrary.placeholder;

import com.github.cozyplugins.cozylibrary.CozyLibrary;
import com.github.cozyplugins.cozylibrary.CozyPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents the cozy plugin placeholder expansion.
 */
public class CozyPlaceholderExpansion extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return CozyLibrary.getPluginName();
    }

    @Override
    public @NotNull String getAuthor() {
        return CozyPlugin.getPlugin().getDescription().getAuthors().get(0);
    }

    @Override
    public @NotNull String getVersion() {
        return CozyPlugin.getPlugin().getDescription().getVersion();
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        // Check if the params is total.
        for (CozyPlaceholder placeholder : CozyPlaceholderManager.get()) {
            if (params.contains(placeholder.getIdentifier()))
                return placeholder.getValue(player, params);
        }

        return null;
    }
}
