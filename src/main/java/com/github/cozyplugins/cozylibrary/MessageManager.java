package com.github.cozyplugins.cozylibrary;

import com.github.cozyplugins.cozylibrary.configuration.message.BaseConfigMessages;
import com.github.cozyplugins.cozylibrary.configuration.message.DefaultMessage;
import com.github.cozyplugins.cozylibrary.dependency.PlaceholderAPIDependency;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1>Represents the message manager</h1>
 * Contains useful static methods to send messages,
 * parse placeholders and colours!
 */
public class MessageManager {

    /**
     * <h1>Used to parse colours and placeholders in a message</h1>
     *
     * @param message The message to parse.
     * @return The parsed message.
     */
    public static @NotNull String parse(@NotNull String message, @Nullable Player player) {
        return MessageManager.parseColours(MessageManager.parsePlaceholders(message, player));
    }

    /**
     * <h1>Used to parse colours and placeholders in a message</h1>
     * This will parse placeholders using the first online player.
     * If there are no players online it will not parse placeholders.
     *
     * @param message The message to parse.
     * @return The parsed message.
     */
    public static @NotNull String parse(@NotNull String message) {
        return MessageManager.parse(message, null);
    }

    /**
     * <h1>Used to parse a message lists placeholders and colours</h1>
     * This will combine the list into a single string with line breaks.
     *
     * @param messageList The instance of the message list.
     * @return The parsed message.
     */
    public static @NotNull String parse(@NotNull List<String> messageList) {
        StringBuilder message = new StringBuilder();
        for (String item : messageList) {
            message.append(MessageManager.parse(item)).append("\n");
        }
        return message.substring(0, message.length() - 1);
    }

    /**
     * <h1>Used to parse colours in a message</h1>
     *
     * @param message The message to parse.
     * @return The parsed message.
     */
    public static @NotNull String parseColours(@NotNull String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher match = pattern.matcher(message);

        while (match.find()) {
            String colour = message.substring(match.start(), match.end());
            message = message.replace(colour, String.valueOf(net.md_5.bungee.api.ChatColor.of(colour)));
            match = pattern.matcher(message);
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * <h1>Used to parse a messages placeholders</h1>
     * This will also check if the placeholder api is enabled.
     * Then it will use the method in {@link PlaceholderAPIDependency}
     * to parse the placeholders.
     * Furthermore, the placeholders defined in the message files will also be parsed.
     *
     * @param message The message to parse.
     * @return The parsed message.
     */
    public static @NotNull String parsePlaceholders(@NotNull String message, @Nullable Player player) {
        if (PlaceholderAPIDependency.isEnabled()) return PlaceholderAPIDependency.parse(message, player);
        return message
                .replace("{message}", BaseConfigMessages.getMessage(DefaultMessage.MESSAGE))
                .replace("{error}", BaseConfigMessages.getMessage(DefaultMessage.ERROR));
    }
}
