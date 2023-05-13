package com.github.cozyplugins.cozylibrary.dependency;

import com.github.cozyplugins.cozylibrary.staticmanager.PlayerManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Represents the placeholder API</h1>
 */
public class PlaceholderAPIDependency {

    /**
     * Used to check if the placeholder API
     * is enabled on the server.
     *
     * @return True if the placeholder API is enabled.
     */
    public static boolean isEnabled() {
        try {
            return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * Used to parse a messages placeholders.
     * If the player is null it will attempt
     * to fine a random player online, otherwise
     * the message will not be parsed.
     *
     * @param message The message to parse.
     * @param player  The player context. This can be null.
     * @return The parsed message.
     */
    public static String parse(@NotNull String message, @Nullable Player player) {
        if (player == null) {
            Player randomPlayer = PlayerManager.getFirstOnlinePlayer();
            if (randomPlayer == null) return message;

            return PlaceholderAPI.setPlaceholders(randomPlayer, message);
        }
        return PlaceholderAPI.setPlaceholders(player, message);
    }
}
