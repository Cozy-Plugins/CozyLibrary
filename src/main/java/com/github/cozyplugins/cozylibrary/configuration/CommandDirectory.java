package com.github.cozyplugins.cozylibrary.configuration;

import com.github.cozyplugins.cozylibrary.ConsoleManager;
import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.cozylibrary.command.CommandTypeManager;
import com.github.cozyplugins.cozylibrary.command.adapter.CommandTypeAdapter;
import com.github.cozyplugins.cozylibrary.command.command.CommandType;
import com.github.cozyplugins.cozylibrary.command.command.CozyCommand;
import com.github.smuddgge.squishyconfiguration.directory.ConfigurationDirectory;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

/**
 * The command directory.
 * <p>
 * A directory that contains commands.
 */
public class CommandDirectory extends ConfigurationDirectory {

    private final @NotNull CozyPlugin<?> pointer;

    /**
     * Used to create a command directory.
     *
     * @param plugin The instance of the plugin.
     */
    public CommandDirectory(@NotNull CozyPlugin<?> plugin) {
        super(plugin.getPlugin().getDataFolder().getAbsolutePath(),
                "commands",
                plugin.getPlugin().getClass()
        );

        this.pointer = plugin;
    }

    private void onReload() {

        // Remove all current command types registered as commands.
        this.pointer.getCommandManager().removeCommandTypes();

        // Get the command type manager.
        final CommandTypeManager typeManager = this.pointer.getCommandManager().getTypeManager();

        // Loop though commands.
        for (String key : this.getKeys()) {

            // Get command type.
            CommandType commandType = typeManager.getCommandType(
                    this.getSection(key).getString("type")
            ).orElse(null);

            // Check if the command type doesn't exist.
            if (commandType == null) continue;

            // Create new command.
            CozyCommand command = new CommandTypeAdapter(
                    this.getSection(key),
                    commandType
            );

            ConsoleManager.log("[CommandDirectory] Added configuration command " + command.getName());

            // Add command to the manager.
            this.pointer.getCommandManager().addCommand(command);
        }
    }
}
