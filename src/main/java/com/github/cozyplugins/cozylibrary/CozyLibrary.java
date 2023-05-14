package com.github.cozyplugins.cozylibrary;

import com.github.cozyplugins.cozylibrary.command.CozyCommandHandler;
import com.github.cozyplugins.cozylibrary.configuration.CommandDirectory;
import com.github.cozyplugins.cozylibrary.staticmanager.ConsoleManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Represents the cozy library core</h1>
 * Handles core static information in
 * the plugin instance.
 */
public class CozyLibrary {

    private static String pluginName = null;

    private static final @NotNull CozyCommandHandler commandHandler = new CozyCommandHandler();
    private static CommandDirectory commandDirectory;

    /**
     * <h1>Used to set the plugins name</h1>
     *
     * @return The name of the plugin.
     */
    public static @NotNull String getPluginName() {
        return CozyLibrary.pluginName == null ? "" : CozyLibrary.pluginName;
    }

    /**
     * <h1>Used to set the plugins name</h1>
     *
     * @param pluginName The plugins name.
     */
    public static void setPluginName(@Nullable String pluginName) {
        CozyLibrary.pluginName = pluginName;
        ConsoleManager.setPrefix("&7[" + pluginName + "]");
    }

    /**
     * <h1>Used to set the command directory</h1>
     *
     * @param commandDirectory A instance of a command directory.
     */
    public static void setCommandDirectory(@NotNull CommandDirectory commandDirectory) {
        CozyLibrary.commandDirectory = commandDirectory;
    }

    /**
     * <h1>Used to get the command handler</h1>
     * Used to register commands in the plugin.
     *
     * @return The instance of the command handler.
     */
    public static @NotNull CozyCommandHandler getCommandHandler() {
        return CozyLibrary.commandHandler;
    }

    /**
     * <h1>Used to get the command directory</h1>
     * Represents the files in the command directory.
     *
     * @return The command directory.
     */
    public static @NotNull CommandDirectory getCommandDirectory() {
        return CozyLibrary.commandDirectory;
    }
}
