package com.github.cozyplugins.cozylibrary.item;

import com.github.cozyplugins.cozylibrary.message.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Represents a cozy item</h1>
 * Uses the bukkit {@link ItemStack} as a
 * base, and adds methods on top.
 */
public class CozyItem extends CozyItemAdapter<CozyItem> {

    /**
     * <h1>Used to create an empty item</h1>
     */
    public CozyItem() {
        super();
    }

    /**
     * <h1>Used to create a cozy item from a item stack</h1>
     *
     * @param itemStack The instance of an item stack.
     */
    public CozyItem(@NotNull ItemStack itemStack) {
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
    public @NotNull CozyItem setName(@NotNull String name) {
        if (this.getItemMeta() == null) this.createItemMeta();
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName(MessageManager.parse(name));

        this.setItemMeta(itemMeta);
        return this;
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
    public @NotNull CozyItem setLore(@NotNull List<String> lore) {
        if (this.getItemMeta() == null) this.createItemMeta();
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore(lore);

        this.setItemMeta(itemMeta);
        return this;
    }

    /**
     * <h1>Used to add lore to a specific index</h1>
     *
     * @param line The lore to set.
     * @param index The place to set the lore.
     * @return This instance.
     */
    public @NotNull CozyItem setLore(@NotNull String line, int index) {
        if (this.getItemMeta() == null) this.createItemMeta();

        List<String> lore = this.getLore();
        lore.add(index, line);

        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore(lore);

        this.setItemMeta(itemMeta);
        return this;
    }

    public @NotNull CozyItem addLore(String line) {
        if (this.getItemMeta() == null) this.createItemMeta();

        List<String> lore = this.getLore();
        lore.add(MessageManager.parse(line));

        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore(lore);

        this.setItemMeta(itemMeta);
        return this;
    }

    /**
     * <h1>Used to override item meta with the items default meta</h1>
     *
     * @return This instance.
     */
    public @NotNull CozyItem createItemMeta() {
        this.setItemMeta(Bukkit.getItemFactory().getItemMeta(this.getMaterial()));
        return this;
    }
}
