package com.github.cozyplugins.cozylibrary;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Represents a cozy plugin</h1>
 */
public class CozyPlugin {

    private static String pluginName = null;

    /**
     * <h1>Used to set the plugins name</h1>
     *
     * @return The name of the plugin.
     */
    public static @NotNull String getPluginName() {
        if (CozyPlugin.pluginName == null) {
            ConsoleManager.warn("[CozyLibrary] Plugin name is not set");
            ConsoleManager.warn("[CozyLibrary] &7Try : CozyPlugin.setPluginName(\"Name\");");
            throw new RuntimeException("Plugin name is not set");
        }

        return CozyPlugin.pluginName;
    }

    /**
     * <h1>Used to set the plugins name</h1>
     *
     * @param pluginName The plugins name.
     */
    public static void setPluginName(@Nullable String pluginName) {
        CozyPlugin.pluginName = pluginName;
    }
}
