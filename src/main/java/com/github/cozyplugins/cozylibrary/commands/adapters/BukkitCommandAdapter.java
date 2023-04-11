package com.github.cozyplugins.cozylibrary.commands.adapters;

import com.github.cozyplugins.cozylibrary.ConsoleManager;
import com.github.cozyplugins.cozylibrary.ListUtility;
import com.github.cozyplugins.cozylibrary.commands.datatypes.CommandArguments;
import com.github.cozyplugins.cozylibrary.commands.datatypes.CommandStatus;
import com.github.cozyplugins.cozylibrary.commands.interfaces.CozyCommand;
import com.github.cozyplugins.cozylibrary.user.ConsoleUser;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>Represents a bukkit command adapter</h1>
 * Used to adapt a cozy command into a bukkit command.
 */
public class BukkitCommandAdapter extends Command {

    private final @NotNull CozyCommand cozyCommand;

    /**
     * <h1>Used to create a bukkit command adapter</h1>
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
            this.setAliases(cozyCommand.getAliases().get());
        }
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String label, @NotNull String[] args) {
        List<String> argsList = new ArrayList<>(Arrays.stream(args).toList());

        // Create command arguments without sub command names.
        CommandArguments arguments = new CommandArguments(
                label, new ArrayList<>(), argsList
        );

        // Check if there are sub commands.
        if (this.cozyCommand.getSubCommands() == null || this.cozyCommand.getSubCommands().isEmpty()) {
            List<String> leftOverArgs = new ArrayList<>(argsList);

            arguments = new CommandArguments(
                    label, this.cozyCommand.getSubCommands().extractNames(argsList),
                    ListUtility.removeTheFirst(leftOverArgs, argsList.size())
            );
        }

        // The final command status.
        CommandStatus commandStatus;

        // Check if the sender is a player.
        if (commandSender instanceof Player player) {
            commandStatus = this.cozyCommand.onUser(new PlayerUser(player), arguments);

            if (commandStatus == null) {
                commandStatus = this.cozyCommand.onPlayerUser(new PlayerUser(player), arguments, new CommandStatus());
            } else {
                this.cozyCommand.onPlayerUser(new PlayerUser(player), arguments, commandStatus);
            }
        }

        // Check if the sender is the console.
        if (commandSender instanceof ConsoleCommandSender consoleCommandSender) {
            commandStatus = this.cozyCommand.onUser(new ConsoleUser(), arguments);

            if (commandStatus == null) {
                commandStatus = this.cozyCommand.onConsoleUser(new ConsoleUser(), arguments, new CommandStatus());
            } else {
                this.cozyCommand.onConsoleUser(new ConsoleUser(), arguments, commandStatus);
            }

            // Get the status message.
            if (commandStatus == null) commandStatus = new CommandStatus();
            String message = commandStatus.getMessage(this.cozyCommand);
            ConsoleManager.log(commandStatus.getMessage(this.cozyCommand));
        }

        return true;
    }
}
