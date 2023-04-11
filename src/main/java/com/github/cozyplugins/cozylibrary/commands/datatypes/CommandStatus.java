package com.github.cozyplugins.cozylibrary.commands.datatypes;

import com.github.cozyplugins.cozylibrary.commands.interfaces.CozyCommand;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Represents a commands status after executing</h1>
 * If a boolean stored is true, a message will be displayed.
 */
public class CommandStatus {

    private boolean notPlayerCommand = false;
    private boolean notConsoleCommand = false;
    private boolean notFakeUserCommand = false;

    /**
     * <h1>Used to state the command is not executable by a player</h1>
     *
     * @return This instance.
     */
    public @NotNull CommandStatus setNotPlayerCommand() {
        this.notPlayerCommand = true;
        return this;
    }

    /**
     * <h1>Used to state the command is not executable by the console</h1>
     *
     * @return This instance.
     */
    public @NotNull CommandStatus setNotConsoleCommand() {
        this.notConsoleCommand = true;
        return this;
    }

    /**
     * <h1>Used to state the command is not executable by a fake user</h1>
     *
     * @return This instance.
     */
    public @NotNull CommandStatus setNotFakeUserCommand() {
        this.notFakeUserCommand = true;
        return this;
    }


    /**
     * <h1>Used to get the status message list</h1>
     * This message list is determined by if
     * a boolean is set to true.
     *
     * @param cozyCommand The instance of a cozy command.
     * @return The instance of the message list.
     */
    public @NotNull List<String> getMessageList(@NotNull CozyCommand cozyCommand) {
        List<String> messageList = new ArrayList<>();
    }
}
