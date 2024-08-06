package com.github.cozyplugins.cozylibrary.item;

import com.github.cozyplugins.cozylibrary.ConsoleManager;
import com.github.cozyplugins.cozylibrary.MessageManager;
import com.github.cozyplugins.cozylibrary.indicator.Replicable;
import com.github.squishylib.configuration.ConfigurationSection;
import com.github.squishylib.configuration.implementation.MemoryConfigurationSection;
import com.github.squishylib.configuration.indicator.ConfigurationConvertible;
import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTType;
import de.tr7zw.nbtapi.iface.ReadableItemNBT;
import org.bukkit.Color;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h1>Represents an nbt item adapter</h1>
 * Adapts methods from {@link NBTItem}
 *
 * @param <S> The return type.
 */
public class NBTItemAdapter<S extends NBTItemAdapter<S>> extends MetaItemAdapter<S> implements ConfigurationConvertible<S>, Replicable<ItemStack> {

    public NBTItemAdapter() {
        super();
    }

    public NBTItemAdapter(@NotNull ItemStack itemStack) {
        super(itemStack);
    }

    public boolean hasNBT() {
        return NBT.get(this.itemStack, ReadableItemNBT::hasNBTData);
    }

    public String getNBTString(@NotNull String key) {
        return NBT.get(this.itemStack, nbt -> {
            return nbt.getString(key);
        });
    }

    public Integer getNBTInteger(@NotNull String key) {
        return NBT.get(this.itemStack, nbt -> {
            return nbt.getInteger(key);
        });
    }

    public @NotNull S setNBTString(@NotNull String key, String value) {
        NBT.modify(this.itemStack, nbt -> {
            nbt.setString(key, value);
        });
        return (S) this;
    }

    public @NotNull S setNBTInteger(@NotNull String key, int value) {
        NBT.modify(this.itemStack, nbt -> {
            nbt.setInteger(key, value);
        });
        return (S) this;
    }

    /**
     * Only converts strings and integers.
     *
     * @return The half map.
     */
    public @NotNull Map<String, Object> getNBTAsHalfMap() {
        return NBT.get(this.itemStack, nbt -> {
           Map<String, Object> map = new HashMap<>();

           for (String key : nbt.getKeys()) {

               if (nbt.getType(key).equals(NBTType.NBTTagString)) {
                   map.put(key, nbt.getString(key));
               }

               if (nbt.getType(key).equals(NBTType.NBTTagInt)) {
                   map.put(key, nbt.getInteger(key));
               }
           }

           return map;
        });
    }

    /**
     * Only converts strings and integers.
     *
     * @return This instance.
     */
    public @NotNull S setNBTMapHalfMap(@NotNull Map<String, Object> map) {
        NBT.modify(this.itemStack, nbt -> {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof String) {
                    nbt.setString(entry.getKey(), (String) entry.getValue());
                    continue;
                }
                if (entry.getValue() instanceof Integer) {
                    nbt.setInteger(entry.getKey(), (Integer) entry.getValue());
                    continue;
                }
                ConsoleManager.warn("Unsupported item nbt type in configuration : " + entry.getValue().getClass());
            }
        });
        return (S) this;
    }

    /**
     * Used to replace a string with a string within the
     * item's name and lore.
     *
     * @param match   The string to look for.
     * @param content The string to replace it with.
     * @return This instance.
     */
    public @NotNull S replaceNameAndLore(@NotNull String match, @NotNull String content) {

        // Check in the name.
        this.setName(this.getName().replace(match, content));

        // Check in the lore.
        this.setLore(this.getLore().stream()
                .map(item -> item.replace(match, content))
                .toList()
        );

        return (S) this;
    }

    /**
     * Used to replace the name lore and nbt in the item.
     *
     * @param match   The string to look for.
     * @param content The content to replace it with.
     * @return This instance.
     */
    @SuppressWarnings("unchecked")
    public @NotNull S replaceNameLoreAndNBT(@NotNull String match, @NotNull String content) {
        this.replaceNameAndLore(match, content);
        this.replaceNBT(match, content);
        return (S) this;
    }

    @SuppressWarnings("unchecked")
    private @NotNull S replaceNBT(@NotNull String match, @NotNull String content) {
        NBT.modify(this.itemStack, nbt -> {
            for (String key : nbt.getKeys()) {
                if (nbt.getType(key).equals(NBTType.NBTTagString)) {
                    nbt.setString(key, nbt.getString(key).replace(match, content));
                }
            }
        });
        return (S) this;
    }

    @Override
    public @NotNull ConfigurationSection convert() {
        ConfigurationSection section = new MemoryConfigurationSection();

        // Item stack adapter.
        section.set("material", this.getMaterial().toString());
        section.set("amount", this.getAmount());
        section.set("durability", this.getDurability());
        if (!this.getEnchantments().isEmpty()) section.set("enchantments", this.getEnchantmentNames());

        // Meta item adapter.
        if (!this.getName().equals("")) section.set("name", this.getName());
        if (!this.getLore().isEmpty()) section.set("lore", this.getLore());
        if (this.hasModelData()) section.set("custom_model_data", this.getCustomModelData());
        if (!this.getItemFlags().isEmpty()) section.set("item_flags", this.getItemFlagNames());
        section.set("unbreakable", this.isUnbreakable());

        // NBT item adapter.
        if (this.hasNBT()) section.set("nbt", this.getNBTAsHalfMap());

        // Leather armour.
        if (this.getItemMeta() instanceof LeatherArmorMeta meta) {
            section.set("color", meta.getColor().asRGB());
        }

        return section;
    }

    @Override
    public S convert(@NotNull ConfigurationSection section) {

        // Convert material.
        this.setMaterial(section.getString("material", "AIR"));

        // Convert amount.
        this.setAmount(section.getInteger("amount", 1));

        // Convert durability.
        final int durability = section.getInteger("durability", -1);
        if (durability != -1) this.setDurability(durability);

        // Convert enchants.
        if (section.getKeys().contains("enchantments")) this.addEnchantments(section.getSection("enchantments"));

        // Convert name.
        if (section.getKeys().contains("name")) this.setName(section.getString("name", "null"));

        // Convert lore.
        if (section.getKeys().contains("lore")) this.setLore(section.getListString("lore", new ArrayList<>()));

        // Convert custom model data.
        final int customModelData = section.getInteger("custom_model_data", -1);
        if (customModelData != -1) this.setCustomModelData(customModelData);

        // Convert item flags.
        this.addItemFlags(section.getListString("item_flags", new ArrayList<>()));

        // Convert unbreakable.
        this.setUnbreakable(section.getBoolean("unbreakable", false));

        // Convert leather armour color.
        if (this.getItemMeta() instanceof LeatherArmorMeta meta && section.getKeys().contains("color")) {
            meta.setColor(Color.fromRGB(section.getInteger("color")));
            this.setItemMeta(meta);
        }

        // Convert enchanted flag.
        if (section.getBoolean("enchanted", false)) {
            this.addEnchantment(Enchantment.MENDING, 1);
            this.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        // Convert nbt.
        this.setNBTMapHalfMap(section.getMap("nbt", new HashMap<>()));
        return (S) this;
    }

    @Override
    public ItemStack duplicate() {
        return this.create().clone();
    }
}
