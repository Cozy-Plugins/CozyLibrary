package com.github.cozyplugins.cozylibrary.messages;

import org.jetbrains.annotations.NotNull;

/**
 * <h1>Represents the default messages</h1>
 */
public enum DefaultMessage {
    MESSAGE("&8&l[&f&lCozyLibrary&8&l]"),
    ERROR("&7"),
    ERROR_NOT_PLAYER_COMMAND("{error} This command cannot be run by a player."),
    ERROR_NOT_CONSOLE_COMMAND("{error} This command cannot be run by the console."),
    ERROR_NOT_FAKE_PLAYER_COMMAND("{error} This command cannot be run by a fake player.");

    private final String message;

    /**
     * Used to create a message enum.
     *
     * @param message The default message.
     */
    DefaultMessage(String message) {
        this.message = message;
    }

    /**
     * <h1>Used to get the default message</h1>
     *
     * @return The default message.
     */
    public @NotNull String getMessage() {
        return this.message;
    }
}
