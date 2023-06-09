package com.github.cozyplugins.cozylibrary.user;

import com.github.cozyplugins.cozylibrary.MessageManager;
import com.github.cozyplugins.cozylibrary.item.CozyItem;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * <h1>Represents a player</h1>
 * Uses methods defined in the user interface.
 */
public class PlayerUser implements User {

    private final @NotNull Player player;

    /**
     * Used to create a new player user.
     *
     * @param player The instance of a player.
     */
    public PlayerUser(@NotNull Player player) {
        this.player = player;
    }

    @Override
    public @NotNull UUID getUuid() {
        return this.player.getUniqueId();
    }

    @Override
    public @NotNull String getName() {
        return this.player.getName();
    }

    @Override
    public void sendMessage(@NotNull String message) {
        this.player.sendMessage(MessageManager.parse(message));
    }

    @Override
    public void sendMessage(@NotNull List<String> messageList) {
        this.player.sendMessage(MessageManager.parse(messageList));
    }

    @Override
    public boolean isVanished() {
        for (MetadataValue meta : this.player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return this.player.hasPermission(permission);
    }

    /**
     * <h1>Used to get the player instance</h1>
     *
     * @return The instance of the player.
     */
    public @NotNull Player getPlayer() {
        return this.player;
    }

    /**
     * <h1>Used to get a item from the players inventory</h1>
     *
     * @param index The slot index.
     * @return The requested item.
     */
    public @NotNull CozyItem getInventoryItem(int index) {
        if (this.player.getInventory().getItem(index) == null) return new CozyItem();
        return new CozyItem(this.player.getInventory().getItem(index));
    }
}
