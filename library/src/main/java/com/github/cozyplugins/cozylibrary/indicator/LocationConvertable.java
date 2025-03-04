package com.github.cozyplugins.cozylibrary.indicator;

import com.github.cozyplugins.cozylibrary.ConsoleManager;
import com.github.squishylib.configuration.ConfigurationSection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Indicates if a class can convert locations to
 * configuration maps and back.
 */
public interface LocationConvertable {

    /**
     * Used to convert a location to a configuration map.
     *
     * @param location The location to convert.
     * @return The instance of the map.
     */
    default @NotNull Map<String, Object> convertLocation(@NotNull Location location) {
        Map<String, Object> map = new HashMap<>();

        if (location.getWorld() == null) {
            ConsoleManager.warn("Attempted to convert a location to configuration map, but the world was null. " + location);
            return map;
        }

        map.put("world", location.getWorld().getName());
        map.put("x", location.getX());
        map.put("y", location.getY());
        map.put("z", location.getZ());
        map.put("yaw", location.getYaw());
        map.put("pitch", location.getPitch());

        return map;
    }

    /**
     * Used to convert a configuration section back into a location.
     *
     * @param section The instance of the configuration section.
     * @return The instance of the location.
     */
    default @NotNull Location convertLocation(@NotNull ConfigurationSection section) {
        World world = Bukkit.getWorld(section.getString("world", "null"));

        // Check if the world is null.
        if (world == null) {
            ConsoleManager.warn("Attempted to fetch location from configuration but world was null. " + section.getMap());
            return null;
        }

        // Return the location.
        return new Location(
            world,
            Double.parseDouble(String.valueOf(section.get("x"))),
            Double.parseDouble(String.valueOf(section.get("y"))),
            Double.parseDouble(String.valueOf(section.get("z"))),
            Float.parseFloat(String.valueOf(section.get("yaw"))),
            Float.parseFloat(String.valueOf(section.get("pitch")))
        );
    }
}
