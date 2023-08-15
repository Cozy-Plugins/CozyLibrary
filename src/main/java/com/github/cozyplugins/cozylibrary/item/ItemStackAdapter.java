package com.github.cozyplugins.cozylibrary.item;

import com.github.cozyplugins.cozylibrary.ConsoleManager;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Utility;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>Represents an item stack adapter</h1>
 * Used to create items quicker and easier.
 * Defaults to {@link Material#AIR}.
 * Mimics the bucket {@link ItemStack} methods,
 * but returns when possible its own instance
 * for method stacking.
 *
 * @param <S> The return type for stacking methods.
 */
public class ItemStackAdapter<S extends ItemStackAdapter<S>> {

    protected @NotNull ItemStack itemStack;

    /**
     * <h1>Initialises the cozy item</h1>
     * Sets the material to {@link Material#AIR}.
     */
    public ItemStackAdapter() {
        this.itemStack = new ItemStack(Material.AIR);
    }

    /**
     * <h1>Initialises the cozy item</h1>
     */
    public ItemStackAdapter(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * <h1>Used to create a clone of the core item stack</h1>
     *
     * @return A new instance of this item.
     */
    public @NotNull ItemStack create() {
        return this.itemStack.clone();
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
     * no clue.
     *
     * @return The max stack size.
     */
    public int getMaxStackSize() {
        return this.itemStack.getMaxStackSize();
    }

    /**
     * <h1>Used to check if the item contains an enchant.</h1>
     *
     * @param enchantment The enchant to check for.
     * @return True if the item contains the enchantment.
     */
    public boolean containsEnchantment(@NotNull Enchantment enchantment) {
        return this.itemStack.containsEnchantment(enchantment);
    }

    /**
     * <h1>Used to get the level of an enchant on the item</h1>
     *
     * @param enchantment The enchantment to check.
     * @return The level of the requested enchanting.
     */
    public int getEnchantmentLevel(@NotNull Enchantment enchantment) {
        return this.itemStack.getEnchantmentLevel(enchantment);
    }

    /**
     * <h1>Used to get the map of enchantments and there level</h1>
     *
     * @return The map of enchantments.
     */
    public @NotNull Map<Enchantment, Integer> getEnchantments() {
        return this.itemStack.getEnchantments();
    }

    /**
     * Used to convert the map of enchantments to
     * the enchantment name and level.
     *
     * @return The enchantment names.
     */
    public @NotNull Map<String, Integer> getEnchantmentNames() {
        Map<String, Integer> map = new HashMap<>();

        for (Map.Entry<Enchantment, Integer> entry : this.getEnchantments().entrySet()) {
            map.put(entry.getKey().getKey().toString(), entry.getValue());
        }

        return map;
    }

    /**
     * <h1>Used to add a map of enchantments to this item</h1>
     * Adds the enchants with less checks.
     *
     * @param enchantmentList The enchantments to add.
     * @return This instance.
     */
    public @NotNull S addEnchantments(@NotNull Map<Enchantment, Integer> enchantmentList) {
        this.itemStack.addUnsafeEnchantments(enchantmentList);
        return (S) this;
    }

    /**
     * Used to add enchantments from a configuration section.
     *
     * @param section The configuration section that contains
     *                the key value pairs on enchant to integer.
     * @return This instance.
     */
    public @NotNull S addEnchantments(@NotNull ConfigurationSection section) {
        for (String key : section.getKeys()) {
            Enchantment enchantment = Enchantment.getByKey(NamespacedKey.fromString(key));
            if (enchantment == null) {
                ConsoleManager.warn("Enchantment null. Could not find the enchantment : " + key);
                continue;
            }
            this.addEnchantment(enchantment, section.getInteger(key, 0));
        }
        return (S) this;
    }

    /**
     * <h1>Adds an enchantment to the item</h1>
     * Adds the enchant with less checks.
     * If the level is lower then 1 it will attempt to remove the enchant.
     *
     * @param enchantment The instance of an enchantment.
     * @param level       The enchantment level.
     * @return This instance.
     */
    public @NotNull S addEnchantment(@NotNull Enchantment enchantment, int level) {
        if (level <= 0) {
            this.removeEnchantment(enchantment);
            return (S) this;
        }

        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return (S) this;
    }

    /**
     * <h1>Removes an enchantment from the item</h1>
     *
     * @param enchantment The enchant to remove.
     * @return The enchants level that was removed.
     */
    public @NotNull S removeEnchantment(@NotNull Enchantment enchantment) {
        this.itemStack.removeEnchantment(enchantment);
        return (S) this;
    }

    /**
     * <h1>Used to get the item meta of this item</h1>
     *
     * @return The items meta.
     */
    public @Nullable ItemMeta getItemMeta() {
        return this.itemStack.getItemMeta();
    }

    /**
     * <h1>Used to check if the item has item meta</h1>
     *
     * @return True if the item has meta values set.
     */
    public boolean hasItemMeta() {
        return this.itemStack.hasItemMeta();
    }

    /**
     * <h1>Used to set the items meta</h1>
     *
     * @param itemMeta The meta values to set item.
     * @return This instance.
     */
    public @NotNull S setItemMeta(@Nullable ItemMeta itemMeta) {
        this.itemStack.setItemMeta(itemMeta);
        return (S) this;
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
}
