package com.github.cozyplugins.cozylibrary.configuration;

import com.github.cozyplugins.cozylibrary.CozyLibrary;
import com.github.cozyplugins.cozylibrary.command.CommandTypeManager;
import com.github.cozyplugins.cozylibrary.command.adapter.CommandTypeAdapter;
import com.github.cozyplugins.cozylibrary.command.command.CommandType;
import com.github.cozyplugins.cozylibrary.command.command.CozyCommand;
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
        // Remove all current command types registered as commands.
        CozyLibrary.getCommandHandler().removeCommandTypes();

        // Loop though commands.
        for (String key : this.getKeys()) {

            // Get command type.
            CommandType commandType = CommandTypeManager.get(this.getSection(key).getString("type"));
            if (commandType == null) continue;

            // Create new command.
            CozyCommand command = new CommandTypeAdapter(
                    this.getSection(key),
                    commandType
            );

            // Add command to handler.
            CozyLibrary.getCommandHandler().add(command);
        }
    }
}
