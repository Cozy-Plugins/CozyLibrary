package com.github.cozyplugins.cozylibrary.inventory;

import com.github.cozyplugins.cozylibrary.MessageManager;
import com.github.cozyplugins.cozylibrary.task.TaskContainer;
import com.github.cozyplugins.cozylibrary.inventory.action.Action;
import com.github.cozyplugins.cozylibrary.inventory.action.action.CloseAction;
import com.github.cozyplugins.cozylibrary.item.CozyItem;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.IntStream;

/**
 * <h1>Represents an intractable inventory</h1>
 */
public abstract class CozyInventory extends TaskContainer {

    private final static @NotNull String REGENERATE_TASK_IDENTIFIER = "regenerate_task_identifier";

    private final @NotNull UUID uuid;
    private @NotNull Inventory inventory;
    private @NotNull Map<Integer, List<Action>> actionMap;
    private @NotNull List<CloseAction> closeActionList;
    private @Nullable PlayerUser owner;

    private final @NotNull String title;
    private @Nullable InventoryType type;
    private int size = 0;

    private final boolean isGenerated = false;
    private boolean stayActive = false;
    private boolean isPlaceable = false;

    /**
     * <h1>Used to create an inventory interface</h1>
     *
     * @param size  The size of the inventory.
     * @param title The title of the inventory.
     */
    public CozyInventory(int size, @NotNull String title) {
        this.uuid = UUID.randomUUID();
        this.inventory = Bukkit.createInventory(null, size, MessageManager.parse(title));
        this.actionMap = new HashMap<>();
        this.closeActionList = new ArrayList<>();
        this.owner = null;
        this.title = title;
        this.size = size;

        InventoryManager.add(this);
    }

    /**
     * <h1>Used to create an inventory interface</h1>
     *
     * @param type  The type of the inventory.
     * @param title The title of the inventory.
     */
    public CozyInventory(@NotNull InventoryType type, @NotNull String title) {
        this.uuid = UUID.randomUUID();
        this.inventory = Bukkit.createInventory(null, type, MessageManager.parse(title));
        this.actionMap = new HashMap<>();
        this.closeActionList = new ArrayList<>();
        this.owner = null;
        this.title = title;
        this.type = type;

        InventoryManager.add(this);
    }

    /**
     * <h1>Used to create an inventory interface</h1>
     *
     * @param owner The inventory's owner.
     * @param size  The size of the inventory.
     * @param title The title of the inventory.
     */
    public CozyInventory(@NotNull Player owner, int size, @NotNull String title) {
        this.uuid = UUID.randomUUID();
        this.inventory = Bukkit.createInventory(owner, size, MessageManager.parse(title, owner));
        this.actionMap = new HashMap<>();
        this.closeActionList = new ArrayList<>();
        this.owner = new PlayerUser(owner);
        this.title = title;
        this.size = size;

        InventoryManager.add(this);
    }

    /**
     * <h1>Used to create an inventory interface</h1>
     *
     * @param owner The inventory's owner.
     * @param type  The type of inventory.
     * @param title The title of the inventory.
     */
    public CozyInventory(@NotNull Player owner, @NotNull InventoryType type, @NotNull String title) {
        this.uuid = UUID.randomUUID();
        this.inventory = Bukkit.createInventory(owner, type, MessageManager.parse(title, owner));
        this.actionMap = new HashMap<>();
        this.closeActionList = new ArrayList<>();
        this.owner = new PlayerUser(owner);
        this.title = title;
        this.type = type;

        InventoryManager.add(this);
    }

    /**
     * <h1>Called before opening the inventory</h1>
     *
     * @param player The instance of the player user.
     */
    protected abstract void onGenerate(PlayerUser player);


    /**
     * Used to set if players can place items in the inventory.
     * This defaults to false.
     *
     * @param isPlaceable True if the inventory should be placeable.
     * @return This instance.
     */
    public @NotNull CozyInventory setPlaceable(boolean isPlaceable) {
        this.isPlaceable = isPlaceable;
        return this;
    }

    /**
     * Used to check if the inventory is placeable.
     * Determines if players can place items in the inventory by default.
     *
     * @return True if placeable.
     */
    public boolean isPlaceable() {
        return this.isPlaceable;
    }

    /**
     * <h1>Used to put a item into the inventory</h1>
     *
     * @param item The instance of the item.
     * @return This instance.
     */
    protected @NotNull CozyInventory setItem(@NotNull CozyItem item) {
        this.inventory.addItem(item.create());
        return this;
    }

    /**
     * <h1>Used to put a item into the inventory</h1>
     *
     * @param item The instance of the item.
     * @param slot The slot to place the item into.
     * @return This instance.
     */
    protected @NotNull CozyInventory setItem(@NotNull CozyItem item, int slot) {
        this.inventory.setItem(slot, item.create());
        return this;
    }

    /**
     * <h1>Used to put an item in a range of slots.</h1>
     * The item will be placed in the slots between and
     * including the start and end slot.
     *
     * @param item  The instance of the item.
     * @param start The first slot.
     * @param end   The last slot.
     * @return This instance.
     */
    protected @NotNull CozyInventory setItem(@NotNull CozyItem item, int start, int end) {
        IntStream.range(start, end + 1).forEachOrdered(slot -> this.setItem(item, slot));
        return this;
    }

    /**
     * <h1>Used to put an item into the requested slots.</h1>
     *
     * @param item  The instance of the item.
     * @param slots The slots to place the item into
     * @return This instance.
     */
    protected @NotNull CozyInventory setItem(@NotNull CozyItem item, int[] slots) {
        for (int slot : slots) {
            this.setItem(item, slot);
        }
        return this;
    }

    /**
     * <h1>Used to put an item into the requested slots.</h1>
     *
     * @param item     The instance of the item.
     * @param slotPool The instance of the slot pool.
     * @return This instance.
     */
    protected @NotNull CozyInventory setItem(@NotNull CozyItem item, SlotPool slotPool) {
        for (int slot : slotPool) {
            this.setItem(item, slot);
        }
        return this;
    }

    /**
     * <h1>Used to put an item into 3 or more slots.</h1>
     *
     * @param item  The instance of the item
     * @param slot1 The first slot.
     * @param slot2 The second slot.
     * @param slots The rest of the slots.
     * @return This instance.
     */
    protected @NotNull CozyInventory setItem(@NotNull CozyItem item, int slot1, int slot2, int... slots) {
        this.setItem(item, slot1);
        this.setItem(item, slot2);

        for (int slot : slots) {
            this.setItem(item, slot);
        }
        return this;
    }

    /**
     * <h1>Used to place the items in the inventory item into the inventory.</h1>
     *
     * @param item The instance of the inventory item.
     * @return This instance.
     */
    protected @NotNull CozyInventory setItem(@NotNull InventoryItem item) {

        // Check if there are no slots specified.
        if (item.getSlots().isEmpty()) {
            int slot = this.inventory.firstEmpty();
            item.addSlot(slot);
        }

        // Place the item into the inventory slots.
        this.setItem(new CozyItem(item.create()), item.getSlots());

        // Set the actions for the slots.
        // This will override any actions that were also in the slots.
        for (int slot : item.getSlots()) {
            this.actionMap.put(slot, item.getActionList());
        }

        return this;
    }

    /**
     * Used to put replace a list of slots with an action.
     *
     * @param action   The instance of the action.
     * @param slotList The list of slots.
     * @return This instance.
     */
    protected @NotNull CozyInventory setAction(@Nullable Action action, int... slotList) {
        List<Action> actionList = new ArrayList<>();
        actionList.add(action);
        for (int slot : slotList) {
            this.actionMap.put(slot, actionList);
        }
        return this;
    }

    /**
     * Used to remove an action from a list of slots.
     *
     * @param slotList The list of slots.
     * @return This instance.
     */
    protected @NotNull CozyInventory removeAction(int... slotList) {
        this.setAction(null, slotList);
        return this;
    }

    /**
     * Used to remove the action from a range of slots.
     *
     * @param start The first slot.
     * @param end   The last slot.
     * @return This instance.
     */
    protected @NotNull CozyInventory removeActionRange(int start, int end) {
        IntStream.range(start, end + 1).forEachOrdered(slot -> this.setAction(null, slot));
        return this;
    }

    /**
     * Used to add a close action.
     *
     * @param closeAction The instance of a close action.
     * @return This instance.
     */
    public @NotNull CozyInventory addCloseAction(@NotNull CloseAction closeAction) {
        this.closeActionList.add(closeAction);
        return this;
    }

    /**
     * Used to set the inventory to stay active in the plugin if
     * a player exits the inventory and there are no more viewers.
     *
     * @return This instance.
     */
    public @NotNull CozyInventory setStayActive() {
        this.stayActive = true;
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
     * <h1>Used to get the list of actions for a slot</h1>
     *
     * @param slot The slot.
     * @return The list of requested actions.
     */
    public @NotNull List<Action> getActionList(int slot) {
        return this.actionMap.get(slot) == null ? new ArrayList<>() : this.actionMap.get(slot);
    }

    /**
     * <h1>Used to get the list of action types for a slot</h1>
     *
     * @param slot The slot.
     * @param type The action type
     * @param <T>  The type of action.
     * @return The requested list of actions.
     */
    public @NotNull <T extends Action> List<T> getActionList(int slot, Class<T> type) {
        List<T> actionTypeList = new ArrayList<>();

        for (Action action : this.getActionList(slot)) {
            if (type.isInstance(action)) actionTypeList.add((T) action);
        }

        return actionTypeList;
    }

    /**
     * Used to get the close action list.
     *
     * @return The list of close actions.
     */
    public @NotNull List<CloseAction> getCloseActionList() {
        return this.closeActionList;
    }

    /**
     * <h1>Used to get the inventory's holder</h1>
     *
     * @return The inventory holder.
     */
    public @Nullable InventoryHolder getHolder() {
        return this.inventory.getHolder();
    }

    /**
     * <h1>Used to get the inventory</h1>
     *
     * @return The inventory.
     */
    public @NotNull org.bukkit.inventory.Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Used to get if the inventory should always stay active.
     *
     * @return True if the inventory should stay active
     */
    public boolean getStayActive() {
        return this.stayActive;
    }

    /**
     * <h1>Used to open the inventory</h1>
     * <li>If the owner hasn't been stated, this player will become the owner of the inventory</li>
     * <li>If the inventory hasn't been generated it will also be generated with {@link #onGenerate}</li>
     *
     * @return This instance.
     */
    public @NotNull CozyInventory open(Player player) {

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
     * <h1>Used to open the inventory</h1>
     * <li>If the owner hasn't been stated, this player will become the owner of the inventory</li>
     * <li>If the inventory hasn't been generated it will also be generated with {@link #onGenerate}</li>
     *
     * @return This instance.
     */
    public @NotNull CozyInventory open(@NotNull PlayerUser user) {
        this.open(user.getPlayer());
        return this;
    }

    /**
     * <h1>Used to close the inventory</h1>
     * This will also remove the inventory from the handler.
     *
     * @return This instance.
     */
    public @NotNull CozyInventory close() {
        for (HumanEntity player : this.inventory.getViewers()) {
            player.closeInventory();
        }
        InventoryManager.remove(this);
        return this;
    }

    /**
     * Used to completely reset the inventory's contents and actions.
     *
     * @return This instance.
     */
    public @NotNull CozyInventory resetInventory() {
        this.actionMap = new HashMap<>();
        this.closeActionList = new ArrayList<>();
        this.inventory.setContents(new ItemStack[]{});
        return this;
    }

    /**
     * Used to start the regenerating inventory task.
     * If this task is already running, it will do nothing.
     *
     * @param delayTicks Teh delay in ticks.
     * @return This instance.
     */
    public @NotNull CozyInventory startRegeneratingInventory(long delayTicks) {

        // Check if the task is already running.
        if (this.containsTask(CozyInventory.REGENERATE_TASK_IDENTIFIER)) return this;

        this.runTaskLoop(CozyInventory.REGENERATE_TASK_IDENTIFIER, () -> {

            // Check if there are no viewers.
            if (this.getInventory().getViewers().isEmpty()) return;

            // Get the instance of the player.
            HumanEntity humanEntity = this.getInventory().getViewers().get(0);
            Player player = Bukkit.getPlayer(humanEntity.getUniqueId());

            // Check if the player is null.
            if (player == null) return;

            // Regenerate the inventory.
            this.onGenerate(new PlayerUser(player));

        }, delayTicks);

        return this;
    }

    /**
     * Used to stop the regenerating inventory task.
     *
     * @return This instance.
     */
    public @NotNull CozyInventory stopRegeneratingInventory() {
        this.stopTask(CozyInventory.REGENERATE_TASK_IDENTIFIER);
        return this;
    }
}
