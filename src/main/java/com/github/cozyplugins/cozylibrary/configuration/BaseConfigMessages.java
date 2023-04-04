package com.github.cozyplugins.cozylibrary.configuration;

import com.github.smuddgge.squishyconfiguration.ConfigurationFactory;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;

import java.io.File;

/**
 * <h1>Represents the base config for messages</h1>
 * This class can be inherited in a plugin to
 * extend the message options.
 */
public class BaseConfigMessages {

    protected Configuration configuration;

    /**
     * Used to initialise the messages configuration file.
     */
    public BaseConfigMessages(File folder) {
        this.configuration = ConfigurationFactory.YAML.create(folder, "messages.yml");
        this.configuration.load();
    }
}
