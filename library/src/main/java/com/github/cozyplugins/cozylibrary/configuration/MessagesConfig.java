package com.github.cozyplugins.cozylibrary.configuration;

import com.github.squishylib.configuration.implementation.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class MessagesConfig extends YamlConfiguration {

    public enum Message {
        MESSAGE("&a"),
        ERROR("&7"),
        ERROR_NOT_PLAYER_COMMAND( "{error} This command cannot be run by a player."),
        ERROR_NOT_CONSOLE_COMMAND("{error} This command cannot be run by the console."),
        ERROR_NOT_FAKE_PLAYER_COMMAND("{error} This command cannot be run by a fake player."),

        ERROR_NO_PERMISSION("{error} You lack permissions to execute this command."),
        ERROR_INCORRECT_ARGUMENTS("{error} This command requires more arguments. {syntax}");

        private final @NotNull String alternative;

        Message(@NotNull String alternative) {
            this.alternative = alternative;
        }

        public @NotNull String getAlternative() {
            return this.alternative;
        }
    }

    public MessagesConfig(@NotNull File file) {
        super(file, MessagesConfig.class);
    }

    public MessagesConfig(@NotNull File file, @NotNull String path) {
        super(file, path, MessagesConfig.class);
    }

    /**
     * Gets the value from the messages.yml configuration.
     * <p>
     * If the value doesn't exist it will return the alternative value.
     *
     * @param message The message to get.
     * @return The message value.
     */
    public @NotNull String get(@NotNull Message message) {
        for (String key : this.getKeys()) {
            if (key.equalsIgnoreCase(message.name())) {
                return this.getString(key, message.getAlternative());
            }
        }
        return message.getAlternative();
    }
}
