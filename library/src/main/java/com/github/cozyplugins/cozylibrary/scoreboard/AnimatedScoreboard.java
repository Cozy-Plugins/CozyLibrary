package com.github.cozyplugins.cozylibrary.scoreboard;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an animated scoreboard.
 */
public interface AnimatedScoreboard {

    /**
     * Called every 20 ticks when the scoreboard is updated.
     *
     * @return The instance of the scoreboard to set.
     */
    @NotNull
    Scoreboard onUpdate();
}
