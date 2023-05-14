package com.github.cozyplugins.cozylibrary.command;

import com.github.cozyplugins.cozylibrary.command.command.CommandType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandTypeManager {

    private final static List<@NotNull CommandType> commandTypeList = new ArrayList<>();

    public static void register(@NotNull CommandType commandType) {
        CommandTypeManager.commandTypeList.add(commandType);
    }

    public static void unregister(@NotNull CommandType commandType) {
        CommandTypeManager.commandTypeList.remove(commandType);
    }

    public static void unregister(@NotNull String identifier) {
        for (CommandType commandType : CommandTypeManager.commandTypeList) {
            if (!commandType.getIdentifier().equals(identifier)) continue;
            CommandTypeManager.unregister(commandType);
            break;
        }
    }

    public static @Nullable CommandType get(@NotNull String identifier) {
        for (CommandType commandType : CommandTypeManager.commandTypeList) {
            if (commandType.getIdentifier().equals(identifier)) return commandType;
        }
        return null;
    }
}
