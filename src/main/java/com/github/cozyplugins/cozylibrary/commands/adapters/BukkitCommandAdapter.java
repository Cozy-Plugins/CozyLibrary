package com.github.cozyplugins.cozylibrary.commands.adapters;

import com.github.cozyplugins.cozylibrary.ListUtility;
import com.github.cozyplugins.cozylibrary.commands.datatypes.CommandArguments;
import com.github.cozyplugins.cozylibrary.commands.interfaces.CozyCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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

        CommandArguments commandArguments = new CommandArguments(
                label, new ArrayList<>(), argsList
        );

        if (this.cozyCommand.getSubCommands() == null || this.cozyCommand.getSubCommands().isEmpty()) {
            List<String> leftOverArgs = new ArrayList<>(argsList);

            commandArguments = new CommandArguments(
                    label, this.cozyCommand.getSubCommands().extractNames(argsList),
                    ListUtility.removeTheFirst(leftOverArgs, argsList.size())
            );
        }

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

        }

        return true;
    }

    public void onPlayerRun(@NotNull Player player) {

    }
}
