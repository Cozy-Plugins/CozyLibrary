package com.github.cozyplugins.cozylibrary.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Utility;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * <h1>Represents a cozy item stack</h1>
 * Used to create items quicker and easier.
 * Defaults to {@link Material#AIR}.
 *
 * @param <S> The return type for stacking methods.
 */
public class CozyItemAdapter<S extends CozyItemAdapter<S>> {

    protected final @NotNull ItemStack itemStack;

    /**
     * <h1>Initialises the cozy item</h1>
     * Sets the material to {@link Material#AIR}.
     */
    public CozyItemAdapter() {
        this.itemStack = new ItemStack(Material.AIR);
    }

    /**
     * <h1>Initialises the cozy item</h1>
     */
    public CozyItemAdapter(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * <h1>Used to get the items material</h1>
     *
     * @return The items material.
     */
    public @NotNull Material getMaterial() {
        return this.itemStack.getType();
    }

    /**
     * <h1>Used to set the material of the item</h1>
     *
     * @param material The material to set.
     * @return This instance.
     */
    public @NotNull S setMaterial(@NotNull Material material) {
        this.itemStack.setType(material);
        return (S) this;
    }

    /**
     * <h1>Gets the amount of items in the stack</h1>
     *
     * @return The amount of items in the stack.
     */
    public int getAmount() {
        return this.itemStack.getAmount();
    }

    /**
     * <h1>Used to set the amount of items in the stack</h1>
     *
     * @param amount The amount of items.
     * @return This instance.
     */
    public @NotNull S setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return (S) this;
    }

    /**
     * <h1>Used to get the material data</h1>
     *
     * @return The materials data.
     */
    public @Nullable MaterialData getMaterialData() {
        return this.itemStack.getData();
    }

    /**
     * <h1>Used to set the items material data</h1>
     *
     * @param materialData The material data.
     * @return This instance.
     */
    public @NotNull S setMaterialData(@NotNull MaterialData materialData) {
        this.itemStack.setData(materialData);
        return (S) this;
    }

    /**
     * <h1>Used to get the items durability</h1>
     * <li>Warning, be careful with item meta that
     * hasn't been applied yet.</li>
     *
     * @return The item's durability.
     */
    public int getDurability() {
        ItemMeta meta = this.itemStack.getItemMeta();
        return (meta == null) ? 0 : ((Damageable) meta).getDamage();
    }

    /**
     * <h1>Used to set the item's durability</h1>
     * <li>Warning, be careful with item meta that
     * hasn't been applied yet.</li>
     *
     * @param durability The durability to set the item.
     * @return This instance.
     */
    public @NotNull S setDurability(int durability) {
        ItemMeta meta = this.itemStack.getItemMeta();
        if (meta != null) {
            ((Damageable) meta).setDamage(durability);
            this.itemStack.setItemMeta(meta);
        }
        return (S) this;
    }

    /**
     * <h1>Used to get the max stack size</h1>
     * Returns -1 if the {@link ItemStack} has
     * no clue
     *
     * @return The max stack size.
     */
    public int getMaxStackSize() {
        return this.itemStack.getMaxStackSize();
    }

    @Override
    @Utility
    public @NotNull String toString() {
        return this.itemStack.toString();
    }

    @Override
    @Utility
    public boolean equals(Object obj) {
        return this.itemStack.equals(obj);
    }

    /**
     * <h1>Used to check if a item stack is similar</h1>
     * Checks everything except stack size.
     *
     * @param item The instance of the item.
     * @return True if they are similar.
     */
    @Utility
    public boolean isSimilar(@NotNull S item) {
        return this.itemStack.isSimilar(item.itemStack);
    }
}
