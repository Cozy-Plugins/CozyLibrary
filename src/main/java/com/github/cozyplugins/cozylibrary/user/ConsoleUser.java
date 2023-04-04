package com.github.cozyplugins.cozylibrary.user;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * <h1>Represents the console</h1>
 * Uses methods defined in the user interface.
 */
public class ConsoleUser implements User {

    private final @NotNull UUID uuid;

    /**
     * <h1>Used to create a console user</h1>
     */
    public ConsoleUser() {
        this.uuid = UUID.randomUUID();
    }

    @Override
    public @NotNull UUID getUuid() {
        return this.uuid;
    }

    @Override
    public @NotNull String getName() {
        return "Console";
    }

    @Override
    public void sendMessage(@NotNull String message) {

    }

    @Override
    public void sendMessage(@NotNull List<String> messageList) {

    }

    @Override
    public boolean isVanished() {
        return true;
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return true;
    }
}
