package com.github.cozyplugins.cozylibrary.command.datatype;

import com.github.cozyplugins.cozylibrary.command.command.CozyCommand;
import com.github.cozyplugins.cozylibrary.pool.Pool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Represents a cozy command pool</h1>
 * A pool which contains {@link CozyCommand}
 */
public class CommandPool extends Pool<CozyCommand, CommandPool> {

    /**
     * <h1>Used to get a cozy command from its name</h1>
     *
     * @param name The name of the command.
     * @return The instance of the command.
     */
    public @Nullable CozyCommand getFromName(String name) {
        for (CozyCommand cozyCommand : this) {
            if (cozyCommand.getName().equals(name)) return cozyCommand;
        }
        return null;
    }

    /**
     * <h1>Extracts command names from this command pool</h1>
     *
     * @return The list of command names.
     */
    public @NotNull List<String> extractNames() {
        List<String> nameList = new ArrayList<>();

        for (CozyCommand command : this) {
            nameList.add(command.getName());
        }

        return nameList;
    }

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

                // Add the correct name/alias.
                list.add(argument);

                // Check if the command also have sub commands.
                if (cozyCommand.getSubCommands() != null && !cozyCommand.getSubCommands().isEmpty()) {
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
     * <h1>Used to get a command pool from the sub commands</h1>
     * Example:
     * <p>
     * input: /test 1 = [1]
     * output: The sub command names of test 1.
     * </p>
     *
     * @param subCommandNameList The names of the sub commands in order.
     * @return The command pool.
     */
    public @NotNull CommandPool getNextSubCommandList(@NotNull List<String> subCommandNameList) {
        List<String> clone = new ArrayList<>(subCommandNameList);

        // Check if there are no command names.
        if (clone.isEmpty()) return this;

        // Get the first command.
        CozyCommand command = this.getFromName(clone.get(0));
        if (command == null) return this;

        // Get the sub commands.
        CommandPool commandPool = command.getSubCommands();
        if (commandPool == null) return this;

        // Remove the command name.
        clone.remove(0);

        // Check if the list is now empty.
        if (clone.isEmpty()) {
            return command.getSubCommands();
        }

        return commandPool.getNextSubCommandList(clone);
    }

    /**
     * <h1>Used to get the current cozy command being used</h1>
     * Example:
     * <p>
     * input: /test 1 = [1]
     * output: The instance of the command representing 1.
     * </p>
     *
     * @param subCommandNameList The names of the sub commands in order.
     * @return Null if the list is empty. Otherwise, the requested command.
     */
    public @Nullable CozyCommand getCommand(@NotNull List<String> subCommandNameList) {
        List<String> clone = new ArrayList<>(subCommandNameList);

        // Check if there are no command names.
        if (clone.isEmpty()) return null;

        // Get the first command.
        CozyCommand command = this.getFromName(clone.get(0));
        if (command == null) return null;

        // Get the sub commands.
        CommandPool commandPool = command.getSubCommands();
        if (commandPool == null) return command;

        // Remove the command name.
        clone.remove(0);

        // Check if the list is now empty.
        if (clone.isEmpty()) {
            return command;
        }

        return commandPool.getCommand(clone);
    }
}
