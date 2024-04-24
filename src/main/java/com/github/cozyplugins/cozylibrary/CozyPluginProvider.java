package com.github.cozyplugins.cozylibrary;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Used to get a non exact plugin instance.
 * <p>
 * This should generally be not used as the plugin
 * instance could change.
 */
public class CozyPluginProvider {

    private static CozyPlugin<?> plugin;

    /**
     * Used to register the instance of a plugin.
     *
     * @param plugin The instance of a plugin.
     */
    @ApiStatus.Internal
    public static void register(@NotNull CozyPlugin<?> plugin) {
        CozyPluginProvider.plugin = plugin;
    }

    /**
     * Used to get the last registered plugin instance.
     *
     * @return The last registered plugin instance.
     */
    public static @NotNull CozyPlugin<?> getCozyPlugin() {
        return CozyPluginProvider.plugin;
    }
}
