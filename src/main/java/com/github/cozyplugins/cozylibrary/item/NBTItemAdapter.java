package com.github.cozyplugins.cozylibrary.item;

import com.github.cozyplugins.cozylibrary.ConsoleManager;
import com.github.cozyplugins.cozylibrary.indicator.ConfigurationConvertable;
import com.github.cozyplugins.cozylibrary.indicator.Replicable;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>Represents an nbt item adapter</h1>
 * Adapts methods from {@link NBTItem}
 *
 * @param <S> The return type.
 */
public class NBTItemAdapter<S extends NBTItemAdapter<S>> extends MetaItemAdapter<S> implements ConfigurationConvertable<S>, Replicable<ItemStack> {

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
     * Used to get the nbt as a map.
     *
     * @return The nbt as a map.
     */
    public @NotNull Map<String, Object> getNBT() {
        Map<String, Object> nbtMap = new HashMap<>();
        NBTItem item = this.createNBTItem();

        for (String key : item.getKeys()) {
            nbtMap.put(key, item.getObject(key, Object.class));
        }

        return nbtMap;
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
     * @param key   The key of the nbt.
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

    @Override
    public @NotNull ConfigurationSection convert() {
        ConfigurationSection section = new MemoryConfigurationSection(new HashMap<>());

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
        if (!this.getNBT().isEmpty()) section.set("nbt", this.getNBT());

        // Leather armour.
        if (this.getItemMeta() instanceof LeatherArmorMeta) {
            LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
            section.set("color", meta.getColor().asRGB());
        }

        return section;
    }

    @Override
    public S convert(ConfigurationSection section) {
        String materialName = section.getString("material", "AIR");
        if (materialName != null && Material.getMaterial(materialName.toUpperCase()) != null) {
            this.setMaterial(Material.getMaterial(materialName));
        } else {
            ConsoleManager.warn("Could not find material : " + section.getString("material") + " for item with map " + section.getMap());
            this.setMaterial(Material.AIR);
        }

        this.setAmount(section.getInteger("amount", 1));

        if (section.getInteger("durability", -1) != -1) {
            this.setDurability(section.getInteger("durability"));
        }

        if (section.getKeys().contains("enchantments")) this.addEnchantments(section.getSection("enchantments"));
        if (section.getKeys().contains("name")) this.setName(section.getString("name"));
        if (section.getKeys().contains("lore")) this.setLore(section.getListString("lore", new ArrayList<>()));

        if (section.getInteger("custom_model_data", -1) != -1) {
            this.setCustomModelData(section.getInteger("custom_model_data"));
        }

        if (section.getKeys().contains("item_flags"))
            this.addItemFlags(section.getListString("item_flags", new ArrayList<>()));

        if (section.getKeys().contains("unbreakable"))
            this.setUnbreakable(section.getBoolean("unbreakable", false));

        // Leather armour.
        if (this.getItemMeta() instanceof LeatherArmorMeta && section.getKeys().contains("color")) {
            LeatherArmorMeta meta = (LeatherArmorMeta) this.getItemMeta();
            meta.setColor(Color.fromRGB(section.getInteger("color")));
        }

        return (S) this;
    }

    @Override
    public ItemStack duplicate() {
        return this.create().clone();
    }
}
