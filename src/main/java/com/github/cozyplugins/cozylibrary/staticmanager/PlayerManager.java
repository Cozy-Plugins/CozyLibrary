package com.github.cozyplugins.cozylibrary.staticmanager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <h1>Represents the player manager</h1>
 * Contains player utility methods.
 */
public class PlayerManager {

    /**
     * Used to get the list of online players.
     * This returns a List instead of a collection provided by bukkit.
     *
     * @return The list of online players.
     */
    public static @NotNull List<Player> getOnlinePlayers() {
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }

    /**
     * Used to get the first player in
     * the online players list.
     *
     * @return The first online player.
     */
    public static @Nullable Player getFirstOnlinePlayer() {
        if (PlayerManager.getOnlinePlayers().isEmpty()) return null;
        return PlayerManager.getOnlinePlayers().get(0);
    }

    /**
     * Used to get a random online player.
     *
     * @return A random player who is online.
     */
    public static @Nullable Player getRandomOnlinePlayer() {
        if (PlayerManager.getOnlinePlayers().isEmpty()) return null;
        List<Player> playerList = PlayerManager.getOnlinePlayers();

        int randomIndex = ThreadLocalRandom.current().nextInt(0, playerList.size() + 1);
        return playerList.get(randomIndex);
    }
}
