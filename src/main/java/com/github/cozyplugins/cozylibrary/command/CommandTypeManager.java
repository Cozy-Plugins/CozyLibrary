package com.github.cozyplugins.cozylibrary.command;

import com.github.cozyplugins.cozylibrary.command.command.CommandType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Represents the command type manager</h1>
 * Holds the possible command types that can be used.
 */
public class CommandTypeManager {

    private final static List<@NotNull CommandType> commandTypeList = new ArrayList<>();

    /**
     * <h1>Used to register a command type</h1>
     *
     * @param commandType The command type to register.
     */
    public static void register(@NotNull CommandType commandType) {
        CommandTypeManager.commandTypeList.add(commandType);
    }

    /**
     * <h1>Used to unregister a command type</h1>
     *
     * @param commandType The command type to unregister.
     */
    public static void unregister(@NotNull CommandType commandType) {
        CommandTypeManager.commandTypeList.remove(commandType);
    }

    /**
     * <h1>Used to unregister a command type</h1>
     *
     * @param identifier The command types unique identifier.
     */
    public static void unregister(@NotNull String identifier) {
        for (CommandType commandType : CommandTypeManager.commandTypeList) {
            if (!commandType.getIdentifier().equals(identifier)) continue;
            CommandTypeManager.unregister(commandType);
            break;
        }
    }

    /**
     * <h1>Used to get a command type</h1>
     *
     * @param identifier The command types unique identifier.
     * @return The requested command type.
     */
    public static @Nullable CommandType get(@NotNull String identifier) {
        for (CommandType commandType : CommandTypeManager.commandTypeList) {
            if (commandType.getIdentifier().equals(identifier)) return commandType;
        }
        return null;
    }
}
