package com.github.cozyplugins.cozylibrary.configuration;

import com.github.cozyplugins.cozylibrary.CozyLibrary;
import com.github.cozyplugins.cozylibrary.command.CommandTypeManager;
import com.github.cozyplugins.cozylibrary.command.adapter.CozyCommandTypeAdapter;
import com.github.cozyplugins.cozylibrary.command.command.CozyCommandType;
import com.github.smuddgge.squishyconfiguration.implementation.yaml.YamlConfiguration;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class CommandDirectory extends ConfigurationDirectory {

    private final @NotNull String defaultFile;

    /**
     * <h1>Used to create a commands configuration directory</h1>
     */
    public CommandDirectory(@NotNull String defaultFile) {
        super("commands");

        this.defaultFile = defaultFile;
    }

    @Override
    public @Nullable Configuration createDefaultConfiguration(@NotNull File folder) {
        return new YamlConfiguration(folder, this.defaultFile);
    }

    @Override
    protected void onReload() {
        // Loop though commands.
        for (String key : this.getKeys()) {
            CozyCommandType commandType = CommandTypeManager.get(this.getSection(key).getString("type"));
            if (commandType == null) continue;

            CozyCommandTypeAdapter command = new CozyCommandTypeAdapter(
                    this.getSection(key),
                    commandType
            );

            CozyLibrary.getCommandHandler().add(command);
        }
    }
}
