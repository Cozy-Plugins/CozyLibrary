package com.github.cozyplugins.cozylibrary.item;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Represents an nbt item adapter</h1>
 * Adapts methods from {@link NBTItem}
 *
 * @param <S> The return type.
 */
public class NBTItemAdapter<S extends NBTItemAdapter<S>> extends MetaItemAdapter<S> {

    /**
     * <h1>Used to create an empty item</h1>
     */
    public NBTItemAdapter() {
        super();
    }

    /**
     * <h1>Used to create a nbt from a item stack</h1>
     *
     * @param itemStack The instance of an item stack.
     */
    public NBTItemAdapter(@NotNull ItemStack itemStack) {
        super(itemStack);
    }

    /**
     * <h1>Represents a nbt update</h1>
     */
    private interface NBTUpdate {
        @NotNull NBTItem apply(@NotNull NBTItem nbtItem);
    }

    /**
     * <h1>Used to update the nbt values</h1>
     *
     * @param nbtUpdate The instance of a nbt update.
     * @return This instance.
     */
    private @NotNull S update(NBTUpdate nbtUpdate) {
        NBTItem nbtItem = new NBTItem(this.itemStack);
        nbtUpdate.apply(nbtItem).applyNBT(this.itemStack);
        return (S) this;
    }

    /**
     * <h1>Used to create a new nbt item</h1>
     * This method will clone the item stack.
     *
     * @return The nbt item.
     */
    public @NotNull NBTItem createNBTItem() {
        return new NBTItem(this.itemStack);
    }

    /**
     * <h1>Used to create a nbt map of the item</h1>
     * This method will clone the item stack.
     *
     * @return The nbt compound.
     */
    public @NotNull NBTCompound createNBTMap() {
        return (NBTCompound) new NBTItem(this.itemStack).getCompound();
    }

    /**
     * <h1>Used to check if the item has any custom nbt values</h1>
     *
     * @return True if the item has any custom nbt tags.
     */
    public boolean hasCustomNBT() {
        return this.createNBTItem().hasCustomNbtData();
    }

    /**
     * <h1>Used to check if the item has nbt values</h1>
     * This can return true when there are no nbt tags.
     * For more info see {@link NBTItem#hasNBTData()}.
     *
     * @return True if the item has any nbt tags.
     */
    public boolean hasNBT() {
        return this.createNBTItem().hasNBTData();
    }

    /**
     * <h1>Used to check if a nbt tag exists</h1>
     *
     * @param key The name of the nbt tag.
     * @return True if the nbt tag exists.
     */
    public boolean hasNBT(@NotNull String key) {
        return this.createNBTItem().hasKey(key);
    }

    /**
     * <h1>Used to get a nbt value</h1>
     *
     * @param key  The name of the key.
     * @param type The class type to return.
     * @param <T>  The return type.
     * @return The requested nbt value.
     */
    public @Nullable <T> T getNBT(@NotNull String key, @NotNull Class<T> type) {
        return this.createNBTItem().getObject(key, type);
    }

    /**
     * <h1>Used to get a string nbt value</h1>
     *
     * @param key The name of the key.
     * @return The requested nbt value.
     */
    public @Nullable String getNBTString(@NotNull String key) {
        return this.createNBTItem().getString(key);
    }

    /**
     * <h1>Used to get a string nbt value</h1>
     *
     * @param key The name of the key.
     * @return The requested nbt value.
     */
    public @Nullable Integer getNBTInteger(@NotNull String key) {
        return this.createNBTItem().getInteger(key);
    }

    /**
     * <h1>Used to get a boolean nbt value</h1>
     *
     * @param key The name of the key.
     * @return The requested nbt value.
     */
    public @Nullable Boolean getNBTBoolean(@NotNull String key) {
        return this.createNBTItem().getBoolean(key);
    }

    /**
     * <h1>Used to set a nbt value</h1>
     *
     * @param key The key of the nbt.
     * @param value The value to set.
     * @return This instance.
     */
    public @NotNull S setNBT(@NotNull String key, @Nullable Object value) {
        this.update(nbtItem -> {
            nbtItem.setObject(key, value);
            return nbtItem;
        });
        return (S) this;
    }

    /**
     * <h1>Clears the items nbt tags</h1>
     *
     * @return This instance.
     */
    public @NotNull S clearNBT() {
        this.update(nbtItem -> {
            nbtItem.clearCustomNBT();
            return nbtItem;
        });
        return (S) this;
    }
}
