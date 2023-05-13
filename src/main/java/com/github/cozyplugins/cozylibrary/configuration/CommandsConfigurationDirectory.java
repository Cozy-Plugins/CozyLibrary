package com.github.cozyplugins.cozylibrary.configuration;

import com.github.cozyplugins.cozylibrary.CozyLibrary;
import com.github.smuddgge.squishyconfiguration.implementation.yaml.YamlConfiguration;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class CommandsConfigurationDirectory extends ConfigurationDirectory {

    public final @NotNull String fileName;

    /**
     * <h1>Used to create a commands configuration directory</h1>
     *
     * @param fileName The name of the default file.
     */
    public CommandsConfigurationDirectory(@NotNull String fileName) {
        super("commands");

        this.fileName = fileName;
    }

    @Override
    public @Nullable Configuration createDefaultConfiguration(@NotNull File folder) {
        return new YamlConfiguration(folder, "commands.yml");
    }

    @Override
    protected void onReload() {
        for (String key : this.getKeys()) {
        }
    }
}
