package com.github.cozyplugins.cozylibrary;

import com.github.cozyplugins.cozylibrary.command.CozyCommandHandler;
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

    /**
     * <h1>Used to set the plugins name</h1>
     *
     * @return The name of the plugin.
     */
    public static @NotNull String getPluginName() {
        if (CozyLibrary.pluginName == null) {
            ConsoleManager.warn("[CozyLibrary] Plugin name is not set");
            ConsoleManager.warn("[CozyLibrary] &7Try : CozyPlugin.setPluginName(\"Name\");");
            throw new RuntimeException("Plugin name is not set");
        }

        return CozyLibrary.pluginName;
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
     * <h1>Used to get the command handler</h1>
     * Used to register commands in the plugin.
     *
     * @return The instance of the command handler.
     */
    public static @NotNull CozyCommandHandler getCommandHandler() {
        return CozyLibrary.commandHandler;
    }
}
