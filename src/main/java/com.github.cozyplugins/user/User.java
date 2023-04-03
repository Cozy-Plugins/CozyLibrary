package com.github.cozyplugins.user;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents a player, console or fake player.
 */
public class User implements UserInterface {

    private Player player = null;
    private ConsoleCommandSender console = null;
    private CommandSender sender = null;

    private @NotNull String name = null;
    private @NotNull UUID uuid = null;

    public User(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
    }

    public User(ConsoleCommandSender console) {
        this.console = console;
        this.uuid = UUID.randomUUID();
        this.name = "Console";
    }

    public User(CommandSender sender) {
        this.sender = sender;
        this.uuid = UUID.randomUUID();
        this.name = sender.getName();

        if (sender instanceof Player) {
            this.player = (Player) sender;
        }

        if (sender instanceof ConsoleCommandSender) {
            this.console = (ConsoleCommandSender) sender;
        }
    }

    public User(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public User(UUID uuid) {
        this.uuid = uuid;
        this.name = "null";
    }

    @Override
    public @NotNull UUID getUuid() {
        return uuid;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public void sendMessage(String message) {
        if (this.player != null) {
            this.player.sendMessage(message);
        }

        if (this.console != null) {
            this.console.sendMessage(message);
        }
    }

    @Override
    public boolean isVanished() {
        if (this.player != null) {
            for (MetadataValue meta : this.player.getMetadata("vanished")) {
                if (meta.asBoolean()) return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean hasPermission(String permission) {
        if (this.player != null) {
            return player.hasPermission(permission);
        }
        return this.console != null;
    }
}
