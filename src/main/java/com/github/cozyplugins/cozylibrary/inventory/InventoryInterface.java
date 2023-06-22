package com.github.cozyplugins.cozylibrary.inventory;

import com.github.cozyplugins.cozylibrary.MessageManager;
import com.github.cozyplugins.cozylibrary.item.CozyItem;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <h1>Represents an intractable inventory</h1>
 */
public abstract class InventoryInterface {

    private final @NotNull UUID uuid;
    private @NotNull Inventory inventory;
    private final @NotNull List<Action> actionList;
    private @Nullable PlayerUser owner;

    private final @NotNull String title;
    private @Nullable InventoryType type;
    private int size = 0;

    private boolean isGenerated = false;

    /**
     * <h1>Used to create an inventory interface</h1>
     *
     * @param size  The size of the inventory.
     * @param title The title of the inventory.
     */
    public InventoryInterface(int size, @NotNull String title) {
        this.uuid = UUID.randomUUID();
        this.inventory = Bukkit.createInventory(null, size, MessageManager.parse(title));
        this.actionList = new ArrayList<>();
        this.owner = null;
        this.title = title;
        this.size = size;

        InventoryHandler.add(this);
    }

    /**
     * <h1>Used to create an inventory interface</h1>
     *
     * @param type  The type of the inventory.
     * @param title The title of the inventory.
     */
    public InventoryInterface(@NotNull InventoryType type, @NotNull String title) {
        this.uuid = UUID.randomUUID();
        this.inventory = Bukkit.createInventory(null, type, MessageManager.parse(title));
        this.actionList = new ArrayList<>();
        this.owner = null;
        this.title = title;
        this.type = type;

        InventoryHandler.add(this);
    }

    /**
     * <h1>Used to create an inventory interface</h1>
     *
     * @param owner The inventory's owner.
     * @param size  The size of the inventory.
     * @param title The title of the inventory.
     */
    public InventoryInterface(@NotNull Player owner, int size, @NotNull String title) {
        this.uuid = UUID.randomUUID();
        this.inventory = Bukkit.createInventory(owner, size, MessageManager.parse(title, owner));
        this.actionList = new ArrayList<>();
        this.owner = new PlayerUser(owner);
        this.title = title;
        this.size = size;

        InventoryHandler.add(this);
    }

    /**
     * <h1>Used to create an inventory interface</h1>
     *
     * @param owner The inventory's owner.
     * @param type  The type of inventory.
     * @param title The title of the inventory.
     */
    public InventoryInterface(@NotNull Player owner, @NotNull InventoryType type, @NotNull String title) {
        this.uuid = UUID.randomUUID();
        this.inventory = Bukkit.createInventory(owner, type, MessageManager.parse(title, owner));
        this.actionList = new ArrayList<>();
        this.owner = new PlayerUser(owner);
        this.title = title;
        this.type = type;

        InventoryHandler.add(this);
    }

    /**
     * <h1>Called before opening the inventory</h1>
     *
     * @param player    The instance of the player user.
     */
    protected abstract void onGenerate(PlayerUser player);

    /**
     * <h1>Used to put a item into the inventory</h1>
     *
     * @param item The instance of the item.
     * @return This instance.
     */
    protected @NotNull InventoryInterface setItem(@NotNull CozyItem item) {
        this.inventory.addItem(item.create());
        return this;
    }

    /**
     * <h1>Used to put a item into the inventory</h1>
     *
     * @param item The instance of the item.
     * @param slot The slot to place the item into.
     * @return This instance.
     *
     */
    protected @NotNull InventoryInterface setItem(@NotNull CozyItem item, int slot) {
        this.inventory.setItem(slot, item.create());
        return this;
    }

    /**
     * <h1>Used to get the inventory's uuid</h1>
     *
     * @return The inventory's uuid.
     */
    public @NotNull UUID getUuid() {
        return this.uuid;
    }

    /**
     * <h1>Used to get the owner using the interface</h1>
     *
     * @return The bukkit player.
     */
    public @Nullable Player getOwner() {
        if (this.owner == null) return null;
        return this.owner.getPlayer();
    }

    /**
     * <h1>Used to get a list of actions</h1>
     *
     * @param type The type of action.
     * @return The list of actions.
     * @param <T> The type of action.
     */
    public @NotNull <T> List<T> getAction(Class<T> type) {
        List<T> list = new ArrayList<>();
        for (Action action : this.actionList) {
            if (type.isInstance(action)) {
                list.add((T) action);
            }
        }
        return list;
    }

    /**
     * <h1>Used to open the inventory</h1>
     * <li>If the owner hasn't been stated, this player will become the owner of the inventory</li>
     * <li>If the inventory hasn't been generated it will also be generated with {@link #onGenerate}</li>
     *
     * @return This instance.
     */
    public @NotNull InventoryInterface open(Player player) {

        // Check if the inventory has an owner.
        if (this.owner == null) {

            // Set the owner to this player.
            this.owner = new PlayerUser(player);

            // Create the inventory.
            if (this.size != 0) {
                this.inventory = Bukkit.createInventory(player, size, MessageManager.parse(this.title, player));
            }
            if (this.type != null) {
                this.inventory = Bukkit.createInventory(player, type, MessageManager.parse(this.title, player));
            }
        }

        // Check if the inventory has been generated.
        if (!this.isGenerated) {
            this.onGenerate(this.owner);
        }

        // Open the inventory for the player.
        player.openInventory(this.inventory);
        return this;
    }

    /**
     * <h1>Used to close the inventory</h1>
     * This will also remove the inventory from the handler.
     *
     * @return This instance.
     */
    public @NotNull InventoryInterface close() {
        for (HumanEntity player : this.inventory.getViewers()) {
            player.closeInventory();
        }
        InventoryHandler.remove(this);
        return this;
    }
}
