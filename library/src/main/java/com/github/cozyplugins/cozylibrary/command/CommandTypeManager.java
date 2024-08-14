/*
 * CozyGamesAPI - The api used to interface with the cozy game system.
 * Copyright (C) 2024 Smuddgge
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

package com.github.cozyplugins.cozylibrary.command;

import com.github.cozyplugins.cozylibrary.command.command.CommandType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a command type manager.
 * <p>
 * Used to store command types that are available.
 */
public class CommandTypeManager {

    private final @NotNull List<CommandType> commandTypeList;

    /**
     * Used to create a new command
     * type manager.
     */
    public CommandTypeManager() {
        this.commandTypeList = new ArrayList<>();
    }

    public @NotNull List<CommandType> getCommandTypeList() {
        return this.commandTypeList;
    }

    public @NotNull Optional<CommandType> getCommandType(@NotNull String identifier) {
        for (CommandType type : commandTypeList) {
            if (type.getIdentifier().equalsIgnoreCase(identifier)) return Optional.of(type);
        }
        return Optional.empty();
    }

    public @NotNull CommandTypeManager registerCommandType(@NotNull CommandType type) {
        this.commandTypeList.add(type);
        return this;
    }

    public @NotNull CommandTypeManager unregisterCommandType(@NotNull String identifier) {
        this.commandTypeList.removeIf(temp -> temp.getIdentifier().equalsIgnoreCase(identifier));
        return this;
    }

    public @NotNull CommandTypeManager unregisterCommandType(@NotNull CommandType type) {
        this.unregisterCommandType(type.getIdentifier());
        return this;
    }
}
