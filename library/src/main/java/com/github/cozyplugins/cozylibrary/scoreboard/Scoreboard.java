package com.github.cozyplugins.cozylibrary.scoreboard;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a custom scoreboard.
 */
public class Scoreboard {

    private @NotNull String title;
    private @NotNull List<String> lines;

    /**
     * Used to create a scoreboard.
     */
    public Scoreboard() {
        this.title = "Title";
        this.lines = new ArrayList<>();
    }

    /**
     * Used to get the scoreboard's title.
     *
     * @return The scoreboard's title.
     */
    public @NotNull String getTitle() {
        return this.title;
    }

    /**
     * Used to get the scoreboard lines.
     *
     * @return The scoreboard's lines.
     */
    public @NotNull List<String> getLines() {
        return this.lines;
    }

    /**
     * Used to set the scoreboard's title.
     *
     * @param title The title to set the scoreboard.
     * @return This instance.
     */
    public @NotNull Scoreboard setTitle(@NotNull String title) {
        this.title = title;
        return this;
    }

    /**
     * Used to set the scoreboard's lines.
     *
     * @param lines The scoreboard's lines to set.
     * @return This instance.
     */
    public @NotNull Scoreboard setLines(@NotNull List<String> lines) {
        this.lines = lines;
        return this;
    }

    /**
     * Used to set the scoreboard's lines.
     *
     * @param line The list of lines to set.
     * @return This instance.
     */
    public @NotNull Scoreboard setLines(@NotNull String... line) {
        this.lines = new ArrayList<>(Arrays.asList(line));
        return this;
    }

    /**
     * Used to add a new line to the scoreboard.
     *
     * @param line The new line to add.
     * @return This instance.
     */
    public @NotNull Scoreboard addLines(@NotNull String line) {
        this.lines.add(line);
        return this;
    }

    /**
     * Used to add lines to the scoreboard.
     *
     * @param line The lines to add.
     * @return This instance.
     */
    public @NotNull Scoreboard addLines(@NotNull String... line) {
        this.lines.addAll(new ArrayList<>(Arrays.asList(line)));
        return this;
    }

    /**
     * Used to add multiple lines to the scoreboard.
     *
     * @param lines The instance of the lines.
     * @return This instance.
     */
    public @NotNull Scoreboard addLines(@NotNull List<String> lines) {
        this.lines.addAll(lines);
        return this;
    }

    /**
     * Used to check if there are no lines.
     *
     * @return True if there are no lines.
     */
    public boolean isEmpty() {
        return this.lines.isEmpty();
    }
}
