package com.github.cozyplugins.cozylibrary.user;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * <h1>Represents a fake user</h1>
 * This user can represent a player that doesnt exist.
 * <ul>
 *     <li>Fake users are always vanished</li>
 *     <li>Fake users contain no permissions</li>
 * </ul>
 */
public class FakeUser implements User {

    private final @NotNull UUID uuid;
    private final @NotNull String name;

    /**
     * Used to create an empty fake user.
     */
    public FakeUser() {
        this.uuid = UUID.randomUUID();
        this.name = "null";
    }

    /**
     * Used to create a fake user with
     * specified values.
     *
     * @param uuid The users uuid.
     * @param name The users name.
     */
    public FakeUser(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public @NotNull UUID getUuid() {
        return this.uuid;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
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
        return false;
    }
}
