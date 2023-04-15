package com.github.cozyplugins.cozylibrary;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Represents the cozy library core</h1>
 * Handles core static information in
 * the plugin instance.
 */
public class CozyLibrary {

    private static String pluginName = null;

    /**
     * <h1>Used to set the plugins name</h1>
     *
     * @return The name of the plugin.
     */
    public static @NotNull String getPluginName() {
        if (CozyLibrary.pluginName == null) {
            ConsoleManager.warn("[com.github.cozyplugins.cozylibrary.CozyLibrary] Plugin name is not set");
            ConsoleManager.warn("[com.github.cozyplugins.cozylibrary.CozyLibrary] &7Try : CozyPlugin.setPluginName(\"Name\");");
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
}
