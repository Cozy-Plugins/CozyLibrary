package com.github.cozyplugins.cozylibrary.command.datatype;

import com.github.cozyplugins.cozylibrary.CozyPluginProvider;
import com.github.cozyplugins.cozylibrary.command.command.CozyCommand;
import com.github.cozyplugins.cozylibrary.configuration.MessagesConfig;
import org.jetbrains.annotations.NotNull;

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

    private boolean noPermission = false;
    private boolean incorrectArguments = false;

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
     * <h1>Used to state the user does not have sufficient permissions</h1>
     *
     * @return This instance.
     */
    public @NotNull CommandStatus setNoPermission() {
        this.noPermission = true;
        return this;
    }

    public @NotNull CommandStatus setIncorrectArguments() {
        this.incorrectArguments = true;
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
        final MessagesConfig messages = CozyPluginProvider.getCozyPlugin().getMessageConfig();
        final List<String> messageList = new ArrayList<>();

        if (this.notPlayerCommand) {
            messageList.add(messages.get(MessagesConfig.Message.ERROR_NOT_PLAYER_COMMAND));
        }

        if (this.notConsoleCommand) {
            messageList.add(messages.get(MessagesConfig.Message.ERROR_NOT_CONSOLE_COMMAND));
        }

        if (this.notFakeUserCommand) {
            messageList.add(messages.get(MessagesConfig.Message.ERROR_NOT_FAKE_PLAYER_COMMAND));
        }

        if (this.noPermission) {
            messageList.add(messages.get(MessagesConfig.Message.ERROR_NO_PERMISSION));
        }

        if (this.incorrectArguments) {
            messageList.add(messages.get(MessagesConfig.Message.ERROR_INCORRECT_ARGUMENTS)
                    .replace("{syntax}", cozyCommand.getSyntax() == null ? "null" : cozyCommand.getSyntax()));
        }

        return messageList;
    }
}
