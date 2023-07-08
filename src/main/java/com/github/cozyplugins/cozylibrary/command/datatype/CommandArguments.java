package com.github.cozyplugins.cozylibrary.command.datatype;

import com.github.cozyplugins.cozylibrary.ListUtility;
import com.github.cozyplugins.cozylibrary.command.command.CozyCommand;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <h1>Represents a command arguments</h1>
 */
public class CommandArguments {

    private final @NotNull String name;
    private final @NotNull List<String> subCommandNameList;
    private final @NotNull List<String> arguments;


    /**
     * <h1>Creates a command argument class</h1>
     *
     * @param name               The name of the command.
     * @param subCommandNameList The names of the sub commands.
     * @param arguments          The command's arguments.
     */
    public CommandArguments(@NotNull String name, @NotNull List<String> subCommandNameList, @NotNull List<String> arguments) {
        this.name = name;
        this.subCommandNameList = subCommandNameList;
        this.arguments = arguments;
    }

    /**
     * <h1>Creates a command argument class</h1>
     * This method uses the cozy command to determine
     * the subcommands.
     *
     * @param cozyCommand The instance of a cozy command.
     * @param label       The command's name.
     * @param args        The arguments given.
     */
    public CommandArguments(@NotNull CozyCommand cozyCommand, @NotNull String label, @NotNull String[] args) {
        List<String> argsList = new ArrayList<>(Arrays.stream(args).toList());

        // Create command arguments without sub command names.
        CommandArguments arguments = new CommandArguments(
                label, new ArrayList<>(), argsList
        );

        // Check if there are sub commands.
        if (!(cozyCommand.getSubCommands() == null || cozyCommand.getSubCommands().isEmpty())) {
            List<String> leftOverArgs = new ArrayList<>(argsList);

            arguments = new CommandArguments(
                    label, cozyCommand.getSubCommands().extractNames(argsList),
                    ListUtility.removeTheFirst(leftOverArgs, argsList.size())
            );
        }

        // Adapt arguments.
        this.name = arguments.getCommandName();
        this.subCommandNameList = arguments.getSubCommandNameList();
        this.arguments = arguments.getArguments();
    }

    /**
     * Retrieves the command name.
     *
     * @return The command name.
     */
    public @NotNull String getCommandName() {
        return this.name;
    }

    /**
     * Retrieves the sub command name list.
     * The current list of sub command names that are included in the arguments.
     * <p>
     * Example:
     * /test 1
     * [1]
     * </p>
     *
     * @return The sub command name list.
     */
    public @NotNull List<String> getSubCommandNameList() {
        return this.subCommandNameList;
    }

    /**
     * Retrieves the command's arguments.
     *
     * @return The command's arguments.
     */
    public @NotNull List<String> getArguments() {
        return this.arguments;
    }
}
