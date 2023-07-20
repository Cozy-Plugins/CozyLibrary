/*
    This file is part of the project CozyTreasureHunt.
    Copyright (C) 2023  Smudge (Smuddgge), Cozy Plugins and contributors.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.cozyplugins.cozylibrary.indicator;

import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

/**
 * Indicates if a class can be converted into a {@link ConfigurationSection}.
 *
 * @param <T> The class instance to return.
 */
public interface ConfigurationConvertable<T> {

    /**
     * Used to convert the class into an unlinked configuration section.
     *
     * @return The unlinked configuration section instance.
     */
    @NotNull ConfigurationSection convert();

    /**
     * Used to convert a configuration section int oa treasure
     * and apply it to this treasure.
     * <li>
     * This is not a static method as other plugins can build on
     * top of the class.
     * </li>
     *
     * @param section The instance of the configuration section.
     */
    T convert(ConfigurationSection section);
}
