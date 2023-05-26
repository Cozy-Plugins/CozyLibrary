package com.github.cozyplugins.cozylibrary.command.datatype;

import com.github.cozyplugins.cozylibrary.command.command.CozyCommand;
import com.github.cozyplugins.cozylibrary.pool.Pool;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Represents a cozy command pool</h1>
 * A pool which contains {@link CozyCommand}
 */
public class CommandPool extends Pool<CozyCommand, CommandPool> {

    /**
     * <h1>Used to extract command names from the list of command arguments</h1>
     * Identifies which arguments are subcommand names.
     *
     * @param args The list of arguments.
     * @return The list of arguments that are sub commands.
     */
    public @NotNull List<String> extractNames(@NotNull List<String> args) {
        if (args.isEmpty()) return new ArrayList<>();
        List<String> list = new ArrayList<>();

        // The argument to check against.
        String argument = args.get(0);

        for (CozyCommand cozyCommand : this) {

            boolean containsTheArgumentInAliases = cozyCommand.getAliases() != null
                    && cozyCommand.getAliases().contains(argument);

            boolean argumentIsCommandName = cozyCommand.getName().equals(argument);

            // Check if the command is stated in the argument.
            if (argumentIsCommandName || containsTheArgumentInAliases) {

                list.add(cozyCommand.getName());

                // Check if the command also have sub commands.
                if (cozyCommand.getSubCommands() != null || cozyCommand.getSubCommands().isEmpty()) {
                    List<String> clone = new ArrayList<>(args);
                    clone.remove(0);

                    list.addAll(cozyCommand.getSubCommands().extractNames(clone));
                }

                return list;
            }
        }

        return list;
    }

    /**
     * <h1>Used to get a cozy command from its name</h1>
     *
     * @param name The name of the command.
     * @return The instance of the command.
     */
    public CozyCommand getFromName(String name) {
        for (CozyCommand cozyCommand : this) {
            if (cozyCommand.getName().equals(name)) return cozyCommand;
        }
        return null;
    }
}
