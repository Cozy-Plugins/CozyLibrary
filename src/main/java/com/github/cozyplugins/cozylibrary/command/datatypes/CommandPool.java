package com.github.cozyplugins.cozylibrary.command.datatypes;

import com.github.cozyplugins.cozylibrary.command.interfaces.CozyCommand;
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
     * <h1>Used to extract command names from a list of arguments</h1>
     * Identifies which arguments are subcommand names.
     *
     * @param args The list of arguments.
     * @return The list of arguments that are sub commands.
     */
    public @NotNull List<String> extractNames(@NotNull List<String> args) {
        if (args.isEmpty()) return new ArrayList<>();
        List<String> list = new ArrayList<>();

        for (CozyCommand cozyCommand : this) {

            boolean containsArgsInAliases = cozyCommand.getAliases() != null
                    && cozyCommand.getAliases().contains(args.get(0));

            // Check if the arguments contain the commands name
            // or aliases.
            if (cozyCommand.getName().equals(args.get(0)) || containsArgsInAliases) {

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
}
