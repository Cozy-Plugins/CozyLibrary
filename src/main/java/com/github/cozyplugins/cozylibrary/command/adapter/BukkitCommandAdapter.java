package com.github.cozyplugins.cozylibrary.command.adapter;

import com.github.cozyplugins.cozylibrary.ListUtility;
import com.github.cozyplugins.cozylibrary.command.command.CommandType;
import com.github.cozyplugins.cozylibrary.command.command.CozyCommand;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandArguments;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandPool;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandStatus;
import com.github.cozyplugins.cozylibrary.command.datatype.CommandSuggestions;
import com.github.cozyplugins.cozylibrary.user.ConsoleUser;
import com.github.cozyplugins.cozylibrary.user.FakeUser;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.cozyplugins.cozylibrary.user.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>Represents a bukkit command adapter</h1>
 * Used to adapt a {@link CozyCommand} into a {@link Command}.
 */
public class BukkitCommandAdapter extends Command {

    private final @NotNull CozyCommand cozyCommand;

    /**
     * <h1>Used to create a bukkit command adapter</h1>
     * The commands syntax will be adapted to the bukkit command usage.
     *
     * @param cozyCommand The instance of the cozy command.
     */
    public BukkitCommandAdapter(@NotNull CozyCommand cozyCommand) {
        super(cozyCommand.getName());

        this.cozyCommand = cozyCommand;

        if (cozyCommand.getDescription() != null) {
            this.setDescription(cozyCommand.getDescription());
        }

        if (cozyCommand.getSyntax() != null) {
            this.setUsage(cozyCommand.getSyntax());
        }

        if (cozyCommand.getAliases() != null) {
            this.setAliases(cozyCommand.getAliases());
        }
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String label, @NotNull String[] args) {
        CommandArguments arguments = new CommandArguments(this.cozyCommand, label, args);
        User user = User.from(commandSender);

        if (arguments.getSubCommandNameList().isEmpty()) {
            this.execute(user, arguments);
            return true;
        }

        CommandPool commandPool = this.cozyCommand.getSubCommands();
        if (commandPool == null) {
            this.execute(user, arguments);
            return true;
        }

        CozyCommand command = this.cozyCommand.getSubCommands().getFromName(arguments.getSubCommandNameList().get(0));

        if (command == null) {
            this.execute(user, arguments);
            return true;
        }

        new BukkitCommandAdapter(command).execute(commandSender, label, args);
        return true;
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        CommandArguments commandArguments = new CommandArguments(this.cozyCommand, alias, args);
        List<String> suggestionList = new ArrayList<>();

        // Get the current argument and its position.
        int index = args.length - 1;
        String argument = args[index];

        // Check for suggestions.
        CommandSuggestions suggestions = this.cozyCommand.getSuggestions(
                User.from(sender), commandArguments
        );

        if (suggestions != null) {
            suggestionList.addAll(suggestions.get(index));
        }

        // Check if there are sub commands.
        if (this.cozyCommand.getSubCommands() != null) {
            CommandPool commandPool = this.cozyCommand.getSubCommands()
                    .getNextSubCommandList(commandArguments.getSubCommandNameList());

            suggestionList.addAll(commandPool.extractNames());
        }

        // Check if any arguments are being searched for.
        if (args[index].isEmpty()) return suggestionList;

        // Reduce to the current tab complete.
        return ListUtility.reduce(suggestionList, argument);
    }

    /**
     * <h1>Used to execute the command with a user</h1>
     *
     * @param user      The instance of a user.
     * @param arguments The instance of the command arguments.
     */
    public void execute(@NotNull User user, @NotNull CommandArguments arguments) {
        CommandStatus commandStatus = new CommandStatus();

        // Check if the sender has permission.
        if (this.cozyCommand.getPermissionPool() != null
                && !(user.hasPermissionList(this.cozyCommand.getPermissionPool()))) {

            commandStatus.setNoPermission();

            // Send the command status message.
            user.sendMessage(commandStatus.getMessageList(this.cozyCommand));
            return;
        }

        // Check if the sender is a player.
        if (user instanceof PlayerUser player) {
            commandStatus = this.executeAsPlayer(player, arguments);
        }

        // Check if the sender is the console.
        if (user instanceof ConsoleUser console) {
            commandStatus = this.executeAsConsole(console, arguments);
        }

        // Check if the sender is a fake user.
        if (user instanceof FakeUser fakeUser) {
            commandStatus = this.executeAsFakeUser(fakeUser, arguments);
        }

        // Send the command status message.
        List<String> messageList = commandStatus.getMessageList(this.cozyCommand);
        if (messageList.isEmpty()) return;
        user.sendMessage(messageList);
    }

    /**
     * <h1>Used to execute the command as a player user</h1>
     * This will not act on the command status, but return it.
     *
     * @param player    The instance of the player user.
     * @param arguments The instance of the command arguments.
     * @return The instance of the command status.
     */
    public @NotNull CommandStatus executeAsPlayer(@NotNull PlayerUser player, @NotNull CommandArguments arguments) {
        CommandStatus commandStatus = new CommandStatus();

        // Execute as user.
        CommandStatus onUserStatus = this.cozyCommand.onUser(player, arguments);
        if (onUserStatus != null) commandStatus = onUserStatus;

        // Execute as player.
        CommandStatus onPlayerStatus = this.cozyCommand.onPlayerUser(player, arguments, commandStatus);

        // If there is a player status, override the command status.
        if (onPlayerStatus != null) commandStatus = onPlayerStatus;

        // Check if both are null.
        if (onUserStatus == null && onPlayerStatus == null) return new CommandStatus().setNotPlayerCommand();
        return commandStatus;
    }

    /**
     * <h1>Used to execute the command as a console user</h1>
     * This will not act on the command status, but return it.
     *
     * @param console   The instance of the console user.
     * @param arguments The instance of the command arguments.
     * @return The instance of the command status.
     */
    public @NotNull CommandStatus executeAsConsole(@NotNull ConsoleUser console, @NotNull CommandArguments arguments) {
        CommandStatus commandStatus = new CommandStatus();

        // Execute as user.
        CommandStatus onUserStatus = this.cozyCommand.onUser(console, arguments);
        if (onUserStatus != null) commandStatus = onUserStatus;

        // Execute as player.
        CommandStatus onConsoleStatus = this.cozyCommand.onConsoleUser(console, arguments, commandStatus);

        // If there is a player status, override the command status.
        if (onConsoleStatus != null) commandStatus = onConsoleStatus;

        // Check if both are null.
        if (onUserStatus == null && onConsoleStatus == null) return new CommandStatus().setNotConsoleCommand();
        return commandStatus;
    }

    /**
     * <h1>Used to execute the command as a fake user</h1>
     * This will not act on the command status, but return it.
     *
     * @param fakeUser  The instance of the fake user.
     * @param arguments The instance of the command arguments.
     * @return The instance of the command status.
     */
    public @NotNull CommandStatus executeAsFakeUser(@NotNull FakeUser fakeUser, @NotNull CommandArguments arguments) {
        CommandStatus commandStatus = new CommandStatus();

        // Execute as user.
        CommandStatus onUserStatus = this.cozyCommand.onUser(fakeUser, arguments);
        if (onUserStatus != null) commandStatus = onUserStatus;

        // Execute as player.
        CommandStatus onFakeUserStatus = this.cozyCommand.onFakeUser(fakeUser, arguments, commandStatus);

        // If there is a player status, override the command status.
        if (onFakeUserStatus != null) commandStatus = onFakeUserStatus;

        // Check if both are null.
        if (onUserStatus == null && onFakeUserStatus == null) return new CommandStatus().setNotFakeUserCommand();
        return commandStatus;
    }
}
