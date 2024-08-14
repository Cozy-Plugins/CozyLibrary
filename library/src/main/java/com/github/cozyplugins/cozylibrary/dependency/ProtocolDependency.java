package com.github.cozyplugins.cozylibrary.dependency;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.github.cozyplugins.cozylibrary.ConsoleManager;
import org.bukkit.Bukkit;

/**
 * Handles the protocol dependency.
 */
public class ProtocolDependency {

    private static ProtocolManager protocolManager;

    /**
     * Used to set up the protocol manager.
     */
    public static void setup() {
        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null) {
            ConsoleManager.warn("The plugin named Protocol is not installed. Some features may be disabled as a result.");
            return;
        }
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    /**
     * Used to check if the protocol dependency is enabled.
     *
     * @return True if it is enabled.
     */
    public static boolean isEnabled() {
        return Bukkit.getPluginManager().getPlugin("ProtocolLib") != null;
    }

    /**
     * Used to get the protocol manager.
     *
     * @return The protocol manager.
     */
    public static ProtocolManager get() {
        return ProtocolDependency.protocolManager;
    }
}
