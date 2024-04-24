package com.github.cozyplugins.cozylibrary;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class ConsoleManager {

    private static final @NotNull String prefix = "&7[CozyPlugin]";

    /**
     * Used to get the console prefix.
     * This defaults to [CozyPlugin].
     *
     * @return The consoles current prefix.
     */
    public static @NotNull String getPrefix() {
        return ConsoleManager.prefix;
    }

    /**
     * <h1>Used to log a message in the console</h1>
     *
     * @param message The message to log.
     */
    public static void log(@NotNull String message) {
        String parsedMessage = MessageManager.parse(ConsoleManager.getPrefix() + " " + message);
        Bukkit.getConsoleSender().sendMessage(parsedMessage);
    }

    /**
     * <h1>Used to post a warning in console</h1>
     *
     * @param message The warning message.
     */
    public static void warn(@NotNull String message) {
        String parsedMessage = MessageManager.parse(ConsoleManager.getPrefix() + " &e[WARNING] " + message);
        Bukkit.getConsoleSender().sendMessage(parsedMessage);
    }

    public static void rawWarn(@NotNull String message) {
        Bukkit.getConsoleSender().sendMessage(MessageManager.parseColours("&e[WARNING] " + message));
    }

    /**
     * <h1>Used to post a error message in the console</h1>
     *
     * @param message The error message.
     */
    public static void error(@NotNull String message) {
        String parsedMessage = MessageManager.parse(ConsoleManager.getPrefix() + " &c[ERROR] " + message);
        Bukkit.getConsoleSender().sendMessage(parsedMessage);
    }
}