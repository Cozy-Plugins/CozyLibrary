package com.github.cozyplugins.cozylibrary.scoreboard;

import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.cozylibrary.MessageManager;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Represents the score board manager.
 * Handles players scoreboards.
 */
public final class ScoreboardManager implements Listener {

    private static @NotNull BukkitTask task;
    private static @NotNull Map<UUID, FastBoard> scoreboardMap;
    private static @NotNull Map<UUID, AnimatedScoreboard> animatedScoreboardMap;

    /**
     * Used to set up the scoreboard manager.
     */
    public static void setup() {

        // Set up the scoreboard maps.
        ScoreboardManager.scoreboardMap = new HashMap<>();
        ScoreboardManager.animatedScoreboardMap = new HashMap<>();

        // Start scheduler.
        BukkitScheduler scheduler = CozyPlugin.getPlugin().getServer().getScheduler();
        ScoreboardManager.task = scheduler.runTaskTimer(CozyPlugin.getPlugin(), () -> {

            // Loop though animated scoreboards.
            for (Map.Entry<UUID, AnimatedScoreboard> entry : ScoreboardManager.animatedScoreboardMap.entrySet()) {

                // Get the fast board.
                FastBoard fastBoard = ScoreboardManager.scoreboardMap.get(entry.getKey());

                // Check if the fast board exists.
                if (fastBoard == null) {
                    Player player = Bukkit.getPlayer(entry.getKey());
                    if (player == null) continue;

                    fastBoard = new FastBoard(player);
                }

                // Get update.
                Scoreboard scoreboard = entry.getValue().onUpdate();

                // Apply update.
                fastBoard.updateTitle(MessageManager.parse(scoreboard.getTitle()));
                fastBoard.updateLines(MessageManager.parse(scoreboard.getLines()));
            }

        }, 40, 40);
    }

    /**
     * Used to stop the scoreboard manager tasks.
     */
    public static void stop() {
        // Cancel task.
        ScoreboardManager.task.cancel();

        // Delete boards.
        for (FastBoard fastBoard : ScoreboardManager.scoreboardMap.values()) {
            fastBoard.delete();
        }

        ScoreboardManager.scoreboardMap = new HashMap<>();
        ScoreboardManager.animatedScoreboardMap = new HashMap<>();
    }

    /**
     * Used to set a user's scoreboard.
     * SEt to null to remove.
     *
     * @param user The instance of the user.
     * @param scoreboard The instance of the scoreboard.
     *                   Or null to remove.
     */
    public static void setScoreboard(@NotNull PlayerUser user, @Nullable Scoreboard scoreboard) {

        // Check if the scoreboard should be removed.
        if (scoreboard == null) {
            ScoreboardManager.removeScoreboard(user);
            return;
        }

        // Check if the player doesn't have a scoreboard.
        if (!ScoreboardManager.scoreboardMap.containsKey(user.getUuid())) {

            // Create new scoreboard.
            FastBoard fastBoard = new FastBoard(user.getPlayer());
            fastBoard.updateTitle(MessageManager.parse(scoreboard.getTitle()));
            fastBoard.updateLines(MessageManager.parse(scoreboard.getLines()));

            ScoreboardManager.scoreboardMap.put(user.getUuid(), fastBoard);
            return;
        }

        // Otherwise already has a scoreboard.
        FastBoard fastBoard = ScoreboardManager.scoreboardMap.get(user.getUuid());
        fastBoard.updateTitle(scoreboard.getTitle());
        fastBoard.updateLines(scoreboard.getLines());
    }

    /**
     * Used to set an animated scoreboard.
     *
     * @param user The instance of the user.
     * @param scoreboard The instance of the animated scoreboard.
     */
    public static void setScoreboard(@NotNull PlayerUser user, @Nullable AnimatedScoreboard scoreboard) {
        // Check if the scoreboard should be removed.
        if (scoreboard == null) {
            ScoreboardManager.removeScoreboard(user);
            return;
        }

        // Check if the player doesn't have a scoreboard.
        if (!ScoreboardManager.scoreboardMap.containsKey(user.getUuid())) {

            // Create new scoreboard.
            FastBoard fastBoard = new FastBoard(user.getPlayer());
            fastBoard.updateTitle(scoreboard.onUpdate().getTitle());
            fastBoard.updateLines(scoreboard.onUpdate().getLines());

            ScoreboardManager.scoreboardMap.put(user.getUuid(), fastBoard);
            ScoreboardManager.animatedScoreboardMap.put(user.getUuid(), scoreboard);
            return;
        }

        // Otherwise already has a scoreboard.
        ScoreboardManager.animatedScoreboardMap.put(user.getUuid(), scoreboard);
    }

    /**
     * Used to remove a player's scoreboard.
     *
     * @param user The instance of the user.
     */
    private static void removeScoreboard(@NotNull PlayerUser user) {

        // Attempt to remove the animated scoreboard.
        ScoreboardManager.animatedScoreboardMap.remove(user.getUuid());

        // Get the scoreboard.
        FastBoard fastBoard = ScoreboardManager.scoreboardMap.getOrDefault(user.getUuid(), null);
        if (fastBoard == null) return;

        // Delete the scoreboard.
        fastBoard.delete();

        // Remove the scoreboard.
        ScoreboardManager.scoreboardMap.remove(
                user.getUuid()
        );
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {

        // Remove scoreboard if it exists.
        ScoreboardManager.removeScoreboard(
                new PlayerUser(event.getPlayer())
        );
    }
}
