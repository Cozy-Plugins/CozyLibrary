package com.github.cozyplugins.cozylibrary.user;

import com.github.cozyplugins.cozylibrary.MessageManager;
import com.github.cozyplugins.cozylibrary.dependency.VaultAPIDependency;
import com.github.cozyplugins.cozylibrary.item.CozyItem;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * <h1>Represents a player</h1>
 * Uses methods defined in the user interface.
 */
public class PlayerUser implements User {

    private final @NotNull Player player;

    /**
     * Used to create a new player user.
     *
     * @param player The instance of a player.
     */
    public PlayerUser(@NotNull Player player) {
        this.player = player;
    }

    @Override
    public @NotNull UUID getUuid() {
        return this.player.getUniqueId();
    }

    @Override
    public @NotNull String getName() {
        return this.player.getName();
    }

    @Override
    public void sendMessage(@NotNull String message) {
        this.player.sendMessage(MessageManager.parse(message));
    }

    @Override
    public void sendMessage(@NotNull List<String> messageList) {
        this.player.sendMessage(MessageManager.parse(messageList));
    }

    @Override
    public boolean isVanished() {
        for (MetadataValue meta : this.player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return this.player.hasPermission(permission);
    }

    /**
     * <h1>Used to get the player instance</h1>
     *
     * @return The instance of the player.
     */
    public @NotNull Player getPlayer() {
        return this.player;
    }

    /**
     * <h1>Used to get a item from the players inventory</h1>
     *
     * @param index The slot index.
     * @return The requested item.
     */
    public @NotNull CozyItem getInventoryItem(int index) {
        if (this.player.getInventory().getItem(index) == null) return new CozyItem();
        return new CozyItem(this.player.getInventory().getItem(index));
    }

    /**
     * Used to run a command as operator.
     *
     * @param command The command to run.
     * @return This instance.
     */
    public @NotNull PlayerUser runCommandsAsOp(@NotNull String command) {
        if (this.player.isOp()) {
            Bukkit.dispatchCommand(player, command);
            return this;
        }

        try {
            player.setOp(true);
            Bukkit.dispatchCommand(player, command);
        } finally {
            player.setOp(false);
        }

        return this;
    }

    /**
     * Used to run commands as operator.
     *
     * @param command1 The first command.
     * @param otherCommands Other commands to run.
     * @return This instance.
     */
    public @NotNull PlayerUser runCommandsAsOp(@NotNull String command1, String... otherCommands) {
        this.runCommandsAsOp(command1);
        for (String command : otherCommands) {
            this.runCommandsAsOp(command);
        }

        return this;
    }

    /**
     * Used to run a list of commands as operator.
     *
     * @param commandList The list of commands.
     * @return This instance.
     */
    public @NotNull PlayerUser runCommandsAsOp(@NotNull List<String> commandList) {
        for (String command : commandList) {
            this.runCommandsAsOp(command);
        }

        return this;
    }

    /**
     * Used to get how much money this player has.
     * If the vault api is not installed it will return 0.
     *
     * @return The amount of money this player has.
     */
    public double getMoney() {
        if (!VaultAPIDependency.isEnabled()) return 0;
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(this.getUuid());
        return VaultAPIDependency.get().getBalance(offlinePlayer);
    }

    /**
     * Used to give this user money.
     *
     * @param amount The amount of money to give.
     * @return True if the money was given successfully.
     */
    public boolean giveMoney(double amount) {
        if (!VaultAPIDependency.isEnabled()) return false;
        return VaultAPIDependency.giveMoney(this, amount);
    }

    /**
     * Used to remove money from this player.
     *
     * @param amount The amount to remove.
     * @return True if the money was removed successfully.
     */
    public boolean removeMoney(double amount) {
        if (!VaultAPIDependency.isEnabled()) return false;
        return VaultAPIDependency.removeMoney(this, amount);
    }
}
