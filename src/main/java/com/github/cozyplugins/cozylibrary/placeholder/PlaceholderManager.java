package com.github.cozyplugins.cozylibrary.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the placeholder manager.
 */
public final class PlaceholderManager<P extends JavaPlugin> extends PlaceholderExpansion {

    private final @NotNull P plugin;
    private final @NotNull List<Placeholder> placeholderList;

    /**
     * Used to create a new placeholder manager.
     *
     * @param plugin The instance of the plugin.
     */
    public PlaceholderManager(@NotNull P plugin) {
        this.plugin = plugin;
        this.placeholderList = new ArrayList<>();
    }

    @Override
    public @NotNull String getIdentifier() {
        return this.plugin.getName();
    }

    @Override
    public @NotNull String getAuthor() {
        return this.plugin.getDescription().getAuthors().get(0);
    }

    @Override
    public @NotNull String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        // Check if the params is total.
        for (Placeholder placeholder : this.getAllPlaceholders()) {
            if (params.contains(placeholder.getIdentifier()))
                return placeholder.getValue(player, params);
        }

        return null;
    }

    public List<Placeholder> getAllPlaceholders() {
        return this.placeholderList;
    }

    public void addPlaceholder(@NotNull Placeholder placeholder) {
        this.placeholderList.add(placeholder);
    }

    public void addPlaceholderList(@NotNull List<Placeholder> placeholderList) {
        this.placeholderList.addAll(placeholderList);
    }

    public void removePlaceholder(@NotNull Placeholder placeholder) {
        this.placeholderList.remove(placeholder);
    }

    public void remove(@NotNull String identifier) {
        for (Placeholder placeholder : this.placeholderList) {
            if (placeholder.getIdentifier().equals(identifier)) this.removePlaceholder(placeholder);
        }
    }

    public void removeAll() {
        this.placeholderList.clear();
    }
}
