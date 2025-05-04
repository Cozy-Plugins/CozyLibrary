package com.github.cozyplugins.cozylibrary.location;

import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public enum Direction {
    UP(new Vector(0, 1, 0)),
    DOWN(new Vector(0, -1, 0)),
    NORTH(new Vector(0, 0, -1)),
    EAST(new Vector(1, 0, 0)),
    SOUTH(new Vector(0, 0, 1)),
    WEST(new Vector(-1, 0, 0));

    private final @NotNull Vector vector;

    Direction(@NotNull Vector vector) {
        this.vector = vector;
    }

    public @NotNull Vector getVector() {
        return this.vector;
    }
}
