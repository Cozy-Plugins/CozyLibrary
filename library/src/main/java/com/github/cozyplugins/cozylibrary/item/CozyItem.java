package com.github.cozyplugins.cozylibrary.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * <h1>Represents a cozy item</h1>
 * Expands on the bukkit {@link ItemStack}
 * with more methods, making it easier
 * to edit the item.
 */
public class CozyItem extends NBTItemAdapter<CozyItem> {

    /**
     * <h1>Used to create a empty cozy item</h1>
     */
    public CozyItem() {
        super();
    }

    /**
     * <h1>Used to adapt a item stack</h1>
     *
     * @param itemStack The item stack to represent.
     */
    public CozyItem(@NotNull ItemStack itemStack) {
        super(itemStack);
    }

    /**
     * <h1>Used to create an item</h1>
     * Creates an item with a custom name.
     *
     * @param name The name of the item.
     */
    public CozyItem(@NotNull String name) {
        super();
        this.setName(name);
    }

    /**
     * <h1>Used to create an item</h1>
     * Creates an item with a name and lore.
     *
     * @param name     The name of the item.
     * @param loreList The lore of the item.
     */
    public CozyItem(@NotNull String name, @NotNull String... loreList) {
        super();
        this.setName(name);
        for (String line : loreList) this.addLore(line);
    }

    /**
     * <h1>Used to create an item</h1>
     * Creates an item with a name and material.
     *
     * @param name     The name of the item.
     * @param material The items material.
     */
    public CozyItem(@NotNull String name, @NotNull Material material) {
        super();
        this.setName(name);
        this.setMaterial(material);
    }

    /**
     * <h1>Used to create an item</h1>
     * Creates an item with a material.
     *
     * @param material The items material.
     */
    public CozyItem(@NotNull Material material) {
        super();
        this.setMaterial(material);
    }

    /**
     * <h1>Used to create an item</h1>
     * Creates an item with a material and lore.
     *
     * @param material The items material.
     * @param loreList The lore of the item.
     */
    public CozyItem(@NotNull Material material, @NotNull String... loreList) {
        super();
        this.setMaterial(material);
        for (String line : loreList) this.addLore(line);
    }
}
