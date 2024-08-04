package com.github.cozyplugins.cozylibrary.datatype.ratio;

import com.github.cozyplugins.cozylibrary.indicator.Replicable;
import com.github.squishylib.configuration.ConfigurationSection;
import com.github.squishylib.configuration.implementation.MemoryConfigurationSection;
import com.github.squishylib.configuration.indicator.ConfigurationConvertible;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a ratio.
 */
public class Ratio implements ConfigurationConvertible<Ratio>, Replicable<Ratio> {

    private int left;
    private int right;

    /**
     * Used to create a new ratio.
     */
    public Ratio() {
        this.left = 1;
        this.right = 1;
    }

    /**
     * Used to create a new ratio.
     *
     * @param left  The left side of the ratio.
     * @param right The right side of the ratio.
     */
    public Ratio(int left, int right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Used to create a new ratio.
     *
     * @param ratio The ratio.
     *              Two integers separated by a colon.
     */
    public Ratio(@NotNull String ratio) {
        this();
        this.convert(ratio);
    }

    /**
     * Used to get the left side of the ratio.
     *
     * @return The left side.
     */
    public int getLeft() {
        return left;
    }

    /**
     * Used to get the right side of the ratio.
     *
     * @return The right side.
     */
    public int getRight() {
        return right;
    }

    /**
     * Used to set the left side of the ratio.
     *
     * @param left The left side.
     * @return This instance.
     */
    public @NotNull Ratio setLeft(int left) {
        this.left = left;
        return this;
    }

    /**
     * Used to set the right side of the ratio.
     *
     * @param right The right side.
     * @return This instance.
     */
    public @NotNull Ratio setRight(int right) {
        this.right = right;
        return this;
    }

    /**
     * Used to multiply the ratio by a constant.
     *
     * @param amount The amount to multiply.
     * @return This instance.
     */
    public @NotNull Ratio multiply(int amount) {
        this.left = this.left * amount;
        this.right = this.right * amount;
        return this;
    }

    /**
     * Used to get the ratio when the right
     * is scaled to a number.
     * 1:2 scaled to 4 would be 2:4.
     *
     * @param amount The amount to scale the right to.
     * @return The clone of this ratio scaled.
     */
    public Ratio getLeftScaled(int amount) {
        Ratio clone = this.duplicate();
        int multiple = amount / this.right;
        clone.multiply(multiple);
        return clone;
    }

    /**
     * Used to check if the sides are the same.
     *
     * @return If the sides are the same.
     */
    public boolean isIdentical() {
        return this.left == this.right;
    }

    /**
     * Used to check if one of the sides
     * is half the other side.
     *
     * @return If a side is half of the other side.
     */
    public boolean isHalf() {
        return this.left / 2 == this.right
                || this.right / 2 == this.left;
    }

    /**
     * Checks if the left side is smaller or equal to the right side.
     *
     * @return True if the left is smaller or equal
     */
    public boolean isLeftSmallerOrEqual() {
        return this.left <= this.right;
    }

    /**
     * Used to convert a ratio into this class
     * and apply it to this class.
     *
     * @param ratio The instance of the ratio.
     *              Two integers separated by a colon.
     * @return This instance.
     */
    public @NotNull Ratio convert(@NotNull String ratio) {
        String[] parts = ratio.split(":");
        this.left = Integer.parseInt(parts[0]);
        this.right = Integer.parseInt(parts[1]);
        return this;
    }

    @Override
    public String toString() {
        return left + ":" + right;
    }

    @Override
    public @NotNull ConfigurationSection convert() {
        ConfigurationSection section = new MemoryConfigurationSection();
        section.set("ratio", this.toString());
        return section;
    }

    @Override
    public @NotNull Ratio convert(ConfigurationSection section) {
        return this.convert(section.getString("ratio", "1:1"));
    }

    @Override
    public Ratio duplicate() {
        ConfigurationSection section = this.convert();
        return new Ratio().convert(section);
    }
}
