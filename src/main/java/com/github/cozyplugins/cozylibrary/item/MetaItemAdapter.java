package com.github.cozyplugins.cozylibrary.item;

import com.github.cozyplugins.cozylibrary.MessageManager;
import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * <h1>Represents a item stack with item meta methods</h1>
 *
 * <li>
 * Uses the bukkit {@link ItemStack} as a base,
 * and adds methods on top.
 * </li>
 * <li>
 * Used the bukkit {@link ItemMeta} methods to make
 * it easier to edit the item.
 * </li>
 *
 * @param <S> The type to return.
 */
public class MetaItemAdapter<S extends MetaItemAdapter<S>> extends ItemStackAdapter<S> {

    /**
     * <h1>Used to create an empty item</h1>
     */
    public MetaItemAdapter() {
        super();
    }

    /**
     * <h1>Used to create a meta item from a item stack</h1>
     *
     * @param itemStack The instance of an item stack.
     */
    public MetaItemAdapter(@NotNull ItemStack itemStack) {
        super(itemStack);
    }

    /**
     * <h1>Used to get the items name</h1>
     * If the item does not have item meta, it will return
     * the materials name.
     *
     * @return The name of the item.
     */
    public @NotNull String getName() {
        return (this.getItemMeta() == null)
                ? this.getMaterial().name() : this.getItemMeta().getDisplayName();
    }

    /**
     * <h1>Used to set the items name</h1>
     * Uses {@link MessageManager#parse} to parse
     * the name.
     *
     * @param name The name to set the item.
     * @return This instance.
     */
    public @NotNull S setName(@NotNull String name) {
        if (this.getItemMeta() == null) this.createItemMeta();
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName(MessageManager.parse(name));

        this.setItemMeta(itemMeta);
        return (S) this;
    }

    /**
     * <h1>Used to get the items lore</h1>
     *
     * @return The item's lore.
     */
    public @NotNull List<String> getLore() {
        return (this.getItemMeta() == null || this.getItemMeta().getLore() == null)
                ? new ArrayList<>() : this.getItemMeta().getLore();
    }

    /**
     * <h1>Used to set the items lore</h1>
     * Doesnt parse the lore with {@link MessageManager#parse}.
     *
     * @param lore The lore to set.
     * @return This instance.
     */
    public @NotNull S setLore(@NotNull List<String> lore) {
        // Convert colors.
        List<String> temp = new ArrayList<>();
        for (String line : lore) {
            temp.add(MessageManager.parse(line));
        }

        // Create lore.
        if (this.getItemMeta() == null) this.createItemMeta();
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore(temp);

        this.setItemMeta(itemMeta);
        return (S) this;
    }

    /**
     * Used to set the item's lore.
     *
     * @param lore The lore to set.
     * @return This instance.
     */
    public @NotNull S setLore(@NotNull String... lore) {
        this.setLore(new ArrayList<>(Arrays.asList(lore)));
        return (S) this;
    }

    /**
     * <h1>Used to add lore to a specific index</h1>
     *
     * @param line  The lore to set.
     * @param index The place to set the lore.
     * @return This instance.
     */
    public @NotNull S setLore(@NotNull String line, int index) {
        if (this.getItemMeta() == null) this.createItemMeta();

        List<String> lore = this.getLore();
        lore.add(index, MessageManager.parse(line));

        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore(lore);

        this.setItemMeta(itemMeta);
        return (S) this;
    }

    /**
     * <h1>Used to add a new line of lore</h1>
     *
     * @param line The line to add.
     * @return This instance.
     */
    public @NotNull S addLore(String line) {
        if (this.getItemMeta() == null) this.createItemMeta();

        List<String> lore = this.getLore();
        lore.add(MessageManager.parse(line));

        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore(lore);

        this.setItemMeta(itemMeta);
        return (S) this;
    }

    /**
     * <h1>Used to get the items custom model data</h1>
     *
     * @return The custom model data.
     */
    public int getCustomModelData() {
        if (this.getItemMeta() == null) this.createItemMeta();
        return this.getItemMeta().getCustomModelData();
    }

    /**
     * <h1>Used to set the items custom model data</h1>
     *
     * @param data The data to set. Null to clear.
     * @return This instance.
     */
    public @NotNull S setCustomModelData(@Nullable Integer data) {
        if (this.getItemMeta() == null) this.createItemMeta();
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setCustomModelData(data);
        this.setItemMeta(itemMeta);
        return (S) this;
    }

    /**
     * <h1>Used to check if the item has model data</h1>
     *
     * @return True if the item has model data.
     */
    public boolean hasModelData() {
        if (this.getItemMeta() == null) this.createItemMeta();
        return this.getItemMeta().hasCustomModelData();
    }

    /**
     * <h1>Used to check if the item has enchants</h1>
     *
     * @return True if the item contains enchants.
     */
    public boolean hasEnchants() {
        if (this.getItemMeta() == null) this.createItemMeta();
        return this.getItemMeta().hasEnchants();
    }

    /**
     * <h1>Adds item flags to the item</h1>
     *
     * @param itemFlags The item flags to add.
     * @return This instance.
     */
    public @NotNull S addItemFlags(@NotNull ItemFlag... itemFlags) {
        if (this.getItemMeta() == null) this.createItemMeta();
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.addItemFlags(itemFlags);
        this.setItemMeta(itemMeta);
        return (S) this;
    }

    /**
     * <h1>Removes item flags from the item</h1>
     *
     * @param itemFlags The item flags to remove.
     * @return This instance.
     */
    public @NotNull S removeItemFlags(@NotNull ItemFlag... itemFlags) {
        if (this.getItemMeta() == null) this.createItemMeta();
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.removeItemFlags(itemFlags);
        this.setItemMeta(itemMeta);
        return (S) this;
    }

    /**
     * <h1>Used to get the list of item flags</h1>
     *
     * @return The list of flags on this item.
     */
    public @NotNull Set<ItemFlag> getItemFlags() {
        if (this.getItemMeta() == null) this.createItemMeta();
        return this.getItemMeta().getItemFlags();
    }

    /**
     * <h1>Used to check if a flag is on an item</h1>
     *
     * @param flag The flag to check for.
     * @return True if the item contains the flag.
     */
    public boolean hasItemFlag(@NotNull ItemFlag flag) {
        if (this.getItemMeta() == null) this.createItemMeta();
        return this.getItemMeta().hasItemFlag(flag);
    }

    /**
     * <h1>Used to check if the item is unbreakable</h1>
     * An unbreakable item doesn't loose durability.
     *
     * @return True if the item is unbreakable.
     */
    public boolean isUnbreakable() {
        if (this.getItemMeta() == null) this.createItemMeta();
        return this.getItemMeta().isUnbreakable();
    }

    /**
     * <h1>Sets the unbreakable tag</h1>
     *
     * @param unbreakable The unbreakable tag.
     * @return This instance.
     */
    public @NotNull S setUnbreakable(boolean unbreakable) {
        if (this.getItemMeta() == null) this.createItemMeta();
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setUnbreakable(unbreakable);
        this.setItemMeta(itemMeta);
        return (S) this;
    }

    /**
     * <h1>Used to check if the item has attribute modifiers</h1>
     *
     * @return True if the item has attribute modifiers.
     */
    public boolean hasAttributeModifiers() {
        if (this.getItemMeta() == null) this.createItemMeta();
        return this.getItemMeta().hasAttributeModifiers();
    }

    /**
     * <h1>Used to get the items attribute modifiers</h1>
     *
     * @return The map of attribute to modifier.
     */
    public @Nullable Multimap<Attribute, AttributeModifier> getAttributeModifiers() {
        if (this.getItemMeta() == null) this.createItemMeta();
        return this.getItemMeta().getAttributeModifiers();
    }

    /**
     * <h1>Used to get the items attribute modifiers for an equipment slot</h1>
     *
     * @param slot The equipment slot.
     * @return The map of attribute to modifier.
     */
    public @NotNull Multimap<Attribute, AttributeModifier> getAttributeModifiers(@NotNull EquipmentSlot slot) {
        if (this.getItemMeta() == null) this.createItemMeta();
        return this.getItemMeta().getAttributeModifiers(slot);
    }

    /**
     * <h1>Get the collection of modifiers given an attribute</h1>
     *
     * @param attribute The instance of an attribute.
     * @return The collection of attribute modifiers.
     */
    public @Nullable Collection<AttributeModifier> getAttributeModifiers(@NotNull Attribute attribute) {
        if (this.getItemMeta() == null) this.createItemMeta();
        return this.getItemMeta().getAttributeModifiers(attribute);
    }

    /**
     * <h1>Attempts to add an attribute modifier</h1>
     *
     * @param attribute The attribute to add.
     * @param modifier  The attribute's modifier.
     * @return This instance.
     */
    public @NotNull S addAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier modifier) {
        if (this.getItemMeta() == null) this.createItemMeta();
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.addAttributeModifier(attribute, modifier);
        this.setItemMeta(itemMeta);
        return (S) this;
    }

    /**
     * <h1>Used to set the attribute modifiers</h1>
     *
     * @param attributeModifiers The map of modifiers.
     * @return This instance.
     */
    public @NotNull S setAttributeModifiers(@Nullable Multimap<Attribute, AttributeModifier> attributeModifiers) {
        if (this.getItemMeta() == null) this.createItemMeta();
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setAttributeModifiers(attributeModifiers);
        this.setItemMeta(itemMeta);
        return (S) this;
    }

    /**
     * <h1>Used to remove an attribute modifier</h1>
     *
     * @param attribute The attribute to remove.
     * @return This instance.
     */
    public @NotNull S removeAttributeModifier(@NotNull Attribute attribute) {
        if (this.getItemMeta() == null) this.createItemMeta();
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.removeAttributeModifier(attribute);
        this.setItemMeta(itemMeta);
        return (S) this;
    }

    /**
     * <h1>Used to remove the attributes from an equipment slot</h1>
     *
     * @param slot The equipment slot.
     * @return This instance.
     */
    public @NotNull S removeAttributeModifier(@NotNull EquipmentSlot slot) {
        if (this.getItemMeta() == null) this.createItemMeta();
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.removeAttributeModifier(slot);
        this.setItemMeta(itemMeta);
        return (S) this;
    }

    /**
     * <h1>Used to remove a single attribute modifier</h1>
     *
     * @param attribute The attribute.
     * @param modifier  The modifier.
     * @return This instance.
     */
    public @NotNull S removeAttributeModifier(@NotNull Attribute attribute, @NotNull AttributeModifier modifier) {
        if (this.getItemMeta() == null) this.createItemMeta();
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.removeAttributeModifier(attribute, modifier);
        this.setItemMeta(itemMeta);
        return (S) this;
    }

    /**
     * <h1>Used to get the custom item tag container</h1>
     * This method is depreciated in item meta.
     *
     * @return The custom item tag container.
     */
    public @NotNull CustomItemTagContainer getCustomTagContainer() {
        if (this.getItemMeta() == null) this.createItemMeta();
        return this.getItemMeta().getCustomTagContainer();
    }

    /**
     * <h1>Used to override item meta with the items default meta</h1>
     *
     * @return This instance.
     */
    public @NotNull S createItemMeta() {
        this.setItemMeta(Bukkit.getItemFactory().getItemMeta(this.getMaterial()));
        return (S) this;
    }
}
