package com.github.cozyplugins.cozylibrary.inventory;

import com.github.cozyplugins.cozylibrary.inventory.action.Action;
import com.github.cozyplugins.cozylibrary.item.CozyItem;
import com.github.cozyplugins.cozylibrary.item.NBTItemAdapter;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * <h1>Represents a {@link NBTItemAdapter} that is in a collection of slots</h1>
 * <li>This class can also hold the items action.</li>
 */
public class InventoryItem extends NBTItemAdapter<InventoryItem> {

    private @NotNull SlotPool slotPool;
    private @NotNull List<Action> actionList;

    /**
     * <h1>Used to create a new inventory item</h1>
     */
    public InventoryItem() {
        super();

        this.slotPool = new SlotPool();
        this.actionList = new ArrayList<>();
    }

    /**
     * Used to create an inventory item given an item stack base.
     *
     * @param itemStack The instance of the item stack.
     */
    public InventoryItem(@NotNull ItemStack itemStack) {
        this();

        this.itemStack = itemStack;
    }

    /**
     * <h1>Used to add slots that the item will be placed in</h1>
     *
     * @param slots The additional slots the item will be placed in.
     * @return This instance.
     */
    public @NotNull InventoryItem addSlot(Integer... slots) {
        this.slotPool.addAll(List.of(slots));
        return this;
    }

    /**
     * <h1>Used to add a list of slots that the item will be placed in</h1>
     *
     * @param slots The list of additional slots the item will be placed in.
     * @return This instance.
     */
    public @NotNull InventoryItem addSlotList(List<Integer> slots) {
        this.slotPool.addAll(slots);
        return this;
    }

    /**
     * <h1>Used to add a range of slots the item will be placed in</h1>
     * <li>The item will be placed within this range of slots.</li>
     * <li>The item will also be included in the start and end slot.</li>
     *
     * @param start The starting point.
     * @param end   The end point.
     * @return This instance.
     */
    public @NotNull InventoryItem addSlotRange(int start, int end) {
        IntStream.range(start, end + 1).forEachOrdered(this::addSlot);
        return this;
    }

    /**
     * <h1>Used to remove all slots the item will be placed in</h1>
     *
     * @return This instance.
     */
    public @NotNull InventoryItem removeSlots() {
        this.slotPool = new SlotPool();
        return this;
    }

    /**
     * <h1>Used to remove certain slots that the item will be placed in</h1>
     *
     * @param slots The slots to remove.
     * @return This instance.
     */
    public @NotNull InventoryItem removeSlots(Integer... slots) {
        this.slotPool.removeAll(List.of(slots));
        return this;
    }

    /**
     * <h1>Used to get the slot pool</h1>
     * Represents the list of slots that the item will be placed in.
     *
     * @return The slot pool.
     */
    public @NotNull SlotPool getSlots() {
        return this.slotPool;
    }

    public @NotNull InventoryItem addAction(Action action) {
        this.actionList.add(action);
        return this;
    }

    public @NotNull InventoryItem removeAllActions() {
        this.actionList = new ArrayList<>();
        return this;
    }

    public @NotNull InventoryItem removeAction(Action action) {
        this.actionList.remove(action);
        return this;
    }

    /**
     * <h1>Used to get the action list</h1>
     * The action list will apply to all slots where the item is placed.
     * If there are no actions, the slots actions will be removed.
     *
     * @return The action list.
     */
    public List<Action> getActionList() {
        return this.actionList;
    }
}
