package com.github.cozyplugins.user;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents methods you can use on a user.
 * A user is a collective term for player, console or fake player.
 */
public interface UserInterface {

    /**
     * Used to get the users uuid.
     * If a uuid was not give, it will create a new one with
     * the creation of the user.
     *
     * @return The users uuid.
     */
    @NotNull UUID getUuid();

    /**
     * Used to get the users name.
     * If a name was not given a default name will be chosen.
     *
     * @return The users name.
     */
    @NotNull String getName();

    /**
     * Used to send a message to the user.
     *
     * @param message The message to send.
     */
    void sendMessage(String message);

    /**
     * Used to check if a user is vanished.
     *
     * @return True if the user is vanished.
     */
    boolean isVanished();

    /**
     * Used to check if a user has a permission.
     *
     * @param permission The permission to check.
     * @return True if the user has permission.
     */
    boolean hasPermission(String permission);
}
