package com.github.cozyplugins.cozylibrary.inventory;

import com.github.cozyplugins.cozylibrary.MessageManager;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <h1>Represents an intractable inventory</h1>
 */
public abstract class InventoryInterface {

    private final @NotNull UUID uuid;
    private final @NotNull Inventory inventory;
    private final @NotNull PlayerUser player;
    private final @NotNull List<Action> actionList;

    /**
     * <h1>Used to create an inventory interface</h1>
     *
     * @param player The instance of the player.
     * @param size   The size of the inventory.
     * @param title  The title of the inventory.
     */
    public InventoryInterface(@NotNull Player player, int size, @NotNull String title) {
        this.uuid = UUID.randomUUID();
        this.inventory = Bukkit.createInventory(player, size, MessageManager.parse(title, player));
        this.player = new PlayerUser(player);
        this.actionList = new ArrayList<>();
    }

    /**
     * <h1>Used to create an inventory interface</h1>
     *
     * @param player The instance of the player.
     * @param type   The type of inventory.
     * @param title  The title of the inventory.
     */
    public InventoryInterface(@NotNull Player player, @NotNull InventoryType type, @NotNull String title) {
        this.uuid = UUID.randomUUID();
        this.inventory = Bukkit.createInventory(player, type, MessageManager.parse(title, player));
        this.player = new PlayerUser(player);
        this.actionList = new ArrayList<>();
    }

    /**
     * <h1>Called before opening the inventory</h1>
     *
     * @param inventory The instance of the inventory.
     * @param player    The instance of the player user.
     */
    public abstract void onGenerate(Inventory inventory, PlayerUser player);

    /**
     * <h1>Used to get the inventory's uuid</h1>
     *
     * @return The inventory's uuid.
     */
    public @NotNull UUID getUuid() {
        return this.uuid;
    }

    /**
     * <h1>Used to get the player using the interface</h1>
     *
     * @return The bukkit player.
     */
    public @NotNull Player getPlayer() {
        return this.player.getPlayer();
    }

    /**
     * <h1>Used to open the inventory</h1>
     * This will also add the inventory to the handler.
     *
     * @return This instance.
     */
    public @NotNull InventoryInterface open() {
        this.onGenerate(this.inventory, this.player);
        this.player.getPlayer().openInventory(this.inventory);
        InventoryHandler.add(this);
        return this;
    }

    /**
     * <h1>Used to close the inventory</h1>
     * This will also remove the inventory from the handler.
     *
     * @return This instance.
     */
    public @NotNull InventoryInterface close() {
        this.player.getPlayer().closeInventory();
        InventoryHandler.remove(this);
        return this;
    }
}
