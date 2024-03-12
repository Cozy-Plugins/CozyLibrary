/*
 * CozyDeliveries - An item and money delivery service for a minecraft server.
 * Copyright (C) 2024  Smuddgge
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.cozyplugins.cozylibrary.configuration;

import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.smuddgge.squishyconfiguration.implementation.YamlConfiguration;
import com.github.smuddgge.squishyconfiguration.indicator.ConfigurationConvertable;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a single type configuration directory.
 * Used to save a specific kind of class to multiple files in a directory.
 *
 * @param <T> The type of object the directory contains.
 *            Each configuration key is a one of these objects.
 */
public abstract class SingleTypeConfigurationDirectory<T extends ConfigurationConvertable<T>> extends ConfigurationDirectory {

    /**
     * Used to create a new configuration directory instance.
     *
     * @param directoryName   The name of the directory in this plugin.
     */
    public SingleTypeConfigurationDirectory(@NotNull String directoryName, @NotNull Class<? extends CozyPlugin> clazz) {
        super(directoryName, clazz);
    }

    /**
     * Used to create an empty instance of the object.
     *
     * @param identifier The object's identifier.
     */
    public abstract @NotNull T createEmpty(@NotNull String identifier);

    /**
     * Used to get the configuration file that contains
     * a certain identifier.
     * This is used to save data as you cannot save data
     * in a configuration directory.
     *
     * @param identifier The identifier to look for.
     * @return The configuration file instance.
     * Empty if the identifier doesn't exist.
     */
    public @NotNull Optional<Configuration> getConfigurationThatContains(@NotNull String identifier) {
        for (File file : this.getFiles()) {
            YamlConfiguration configuration = new YamlConfiguration(file);
            configuration.load();
            if (configuration.getKeys().contains(identifier)) return Optional.of(configuration);
        }

        return Optional.empty();
    }

    /**
     * Used to get a type object from the configuration.
     *
     * @param identifier The object's identifier.
     * @return The instance of the object.
     * Empty if it isn't in the configuration directory.
     */
    public @NotNull Optional<T> getType(@NotNull String identifier) {

        // Check if the type exists.
        if (this.getKeys().contains(identifier)) {
            return Optional.of(this.createEmpty(identifier).convert(this.getSection(identifier)));
        }

        // Otherwise the object does not exist.
        return Optional.empty();
    }

    /**
     * Used to get the list of all the types in
     * the configuration directory.
     *
     * @return The list of types.
     */
    public @NotNull List<T> getAllTypes() {
        List<T> typeList = new ArrayList<>();

        for (String identifier : this.getKeys()) {
            typeList.add(this.getType(identifier)
                    .orElseThrow(() -> new RuntimeException("Directory exists in directory list but not in directory?"))
            );
        }

        return typeList;
    }

    /**
     * Used to insert a type into the directory.
     *
     * @param identifier The instance of the identifier.
     * @param type       The type to insert.
     * @return This instance.
     */
    public @NotNull SingleTypeConfigurationDirectory<T> insertType(@NotNull String identifier, @NotNull T type) {

        // Get the local configuration file.
        Optional<Configuration> optionalConfiguration = this.getConfigurationThatContains(identifier);

        // Check if the configuration exists.
        if (optionalConfiguration.isEmpty()) return this;
        Configuration configuration = optionalConfiguration.get();

        configuration.set(identifier, type.convert().getMap());
        configuration.save();

        // Reload the directory.
        this.reload();
        return this;
    }

    /**
     * Used to remove a type from the configuration directory.
     * This will also save it to the local configuration
     * and reload the directory.
     *
     * @param identifier The instance of the identifier.
     * @return This instance.
     */
    public @NotNull SingleTypeConfigurationDirectory<T> removeType(@NotNull String identifier) {

        // Get the local configuration file.
        Optional<Configuration> optionalConfiguration = this.getConfigurationThatContains(identifier);

        // Check if the configuration exists.
        if (optionalConfiguration.isEmpty()) return this;
        Configuration configuration = optionalConfiguration.get();

        // Remove the identifier and data.
        configuration.set(identifier, null);
        configuration.save();

        // Reload the directory.
        this.reload();
        return this;
    }

    /**
     * Used to check if the configuration directory
     * contains a certain identifier.
     *
     * @param identifier The identifier to check for.
     * @return True if it exists in the configuration directory.
     */
    public boolean contains(@NotNull String identifier) {
        return this.getKeys().contains(identifier);
    }
}
