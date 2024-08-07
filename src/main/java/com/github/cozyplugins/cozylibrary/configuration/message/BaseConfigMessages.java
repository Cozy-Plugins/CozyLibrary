package com.github.cozyplugins.cozylibrary.configuration.message;

import com.github.cozyplugins.cozylibrary.CozyPluginProvider;
import com.github.squishylib.configuration.Configuration;
import com.github.squishylib.configuration.implementation.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * <h1>Represents the base config for messages</h1>
 * This class can be inherited in a plugin to
 * extend the message options.
 */
public class BaseConfigMessages {

    protected Configuration configuration;

    /**
     * Used to initialize the message configuration file.
     */
    public BaseConfigMessages() {
        this.configuration = new YamlConfiguration(
                CozyPluginProvider.getCozyPlugin().getPlugin().getDataFolder(),
                "messages.yml"
        );
        this.configuration.load();
    }

    /**
     * <h1>Used to get a default message from the configuration</h1>
     *
     * @param message The default message to get.
     * @return The message.
     */
    public @NotNull String get(DefaultMessage message) {
        return this.configuration.getString(message.toString(), message.getMessage());
    }

    /**
     * <h1>Used to get a default message from the configuration</h1>
     *
     * @param message The default message to get.
     * @return The message.
     */
    public static @NotNull String getMessage(DefaultMessage message) {
        return new BaseConfigMessages().get(message);
    }
}
