package com.github.cozyplugins.cozylibrary.command;

import com.github.cozyplugins.cozylibrary.CozyLibrary;
import com.github.cozyplugins.cozylibrary.command.command.CozyCommandType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandTypeManager {

    private final static List<@NotNull CozyCommandType> commandTypeList = new ArrayList<>();

    public static void register(@NotNull CozyCommandType commandType) {
        CommandTypeManager.commandTypeList.add(commandType);
    }

    public static void unregister(@NotNull CozyCommandType commandType) {
        CommandTypeManager.commandTypeList.remove(commandType);
    }

    public static void unregister(@NotNull String identifier) {
        for (CozyCommandType commandType : CommandTypeManager.commandTypeList) {
            if (!commandType.getIdentifier().equals(identifier)) continue;
            CommandTypeManager.unregister(commandType);
            break;
        }
    }

    public static @Nullable CozyCommandType get(@NotNull String identifier) {
        for (CozyCommandType commandType : CommandTypeManager.commandTypeList) {
            if (commandType.getIdentifier().equals(identifier)) return commandType;
        }
        return null;
    }
}
