package com.github.cozyplugins.cozylibrary.location;

import com.github.cozyplugins.cozylibrary.indicator.ConfigurationConvertable;
import com.github.cozyplugins.cozylibrary.indicator.Replicable;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.error.YAMLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a 3 dimensional region.
 */
public class Region3D implements Replicable<Region3D>, ConfigurationConvertable {

    private @NotNull Location position1;
    private @NotNull Location position2;

    /**
     * Used to create a 3D region.
     * The region will be the area between the two positions.
     *
     * @param position1 Position 1.
     * @param position2 Position 2.
     * @throws IllegalArgumentException Throws error when the positions are in different worlds
     *                                  or the worlds are null.
     *                                  Positions must be in the same world.
     */
    public Region3D(@NotNull Location position1, @NotNull Location position2) {
        // Check if the positions are in the same world
        // or if the worlds are null.
        if (position1.getWorld() == null || position2.getWorld() == null
                || !position1.getWorld().getName().equals(position2.getWorld().getName())) {

            throw new IllegalArgumentException("Positions must be in the same world to create a region.");
        }

        this.position1 = position1;
        this.position2 = position2;
    }

    @Override
    public String toString() {
        assert this.position1.getWorld() != null;
        return "{Region3D: {world: " + this.position1.getWorld().getName()
                + ", pos1: " + this.position1.getX() + " " + this.position1.getY() + " " + this.position1.getZ()
                + ", pos2: " + this.position2.getX() + " " + this.position2.getY() + " " + this.position2.getZ()
                + "}}";
    }

    @Override
    public Region3D duplicate() {
        return new Region3D(this.position1.clone(), this.position2.clone());
    }

    @Override
    public @NotNull ConfigurationSection convert() {
        ConfigurationSection section = new MemoryConfigurationSection(new HashMap<>());

        assert this.position1.getWorld() != null;

        section.set("world", this.position1.getWorld().getName());

        section.set("position1.x", this.position1.getX());
        section.set("position1.y", this.position1.getY());
        section.set("position1.z", this.position1.getZ());

        section.set("position2.x", this.position2.getX());
        section.set("position2.y", this.position2.getY());
        section.set("position2.z", this.position2.getZ());

        return section;
    }

    @Override
    public void convert(ConfigurationSection section) {
        World world = Bukkit.getWorld(section.getString("world", null));

        // Check if the world is null.
        if (world == null) {
            throw new YAMLException("Unable to load world from configuration. World does not exists : " + section.getString("world"));
        }

        // Set first position.
        this.position1 = new Location(
                world,
                (double) section.get("position1.x"),
                (double) section.get("position1.y"),
                (double) section.get("position1.z")
        );

        // Set second position.
        this.position2 = new Location(
                world,
                (double) section.get("position2.x"),
                (double) section.get("position2.y"),
                (double) section.get("position2.z")
        );
    }

    /**
     * Used to get the first position.
     *
     * @return The first position.
     */
    public @NotNull Location getPosition1() {
        return this.position1;
    }

    /**
     * Used to get the second position.
     *
     * @return The second position.
     */
    public @NotNull Location getPosition2() {
        return this.position2;
    }

    /**
     * Used to get the minimum point in the region.
     * This location will have the smallest x y and z values.
     *
     * @return The location of the min point.
     */
    public @NotNull Location getMinPoint() {
        return new Location(
                this.position1.getWorld(),
                Math.min(this.position1.getBlockX(), this.position2.getBlockX()),
                Math.min(this.position1.getBlockY(), this.position2.getBlockY()),
                Math.min(this.position1.getBlockZ(), this.position2.getBlockZ())
        );
    }

    /**
     * Used to get the maximum point in the region.
     * This location will have the largest x y and z values.
     *
     * @return The location of the max point.
     */
    public @NotNull Location getMaxPoint() {
        return new Location(
                this.position1.getWorld(),
                Math.max(this.position1.getBlockX(), this.position2.getBlockX()),
                Math.max(this.position1.getBlockY(), this.position2.getBlockY()),
                Math.max(this.position1.getBlockZ(), this.position2.getBlockZ())
        );
    }

    /**
     * Used to get the center of the region.
     *
     * @return The center of the region.
     */
    public @NotNull Location getCenter() {
        return new Location(
                this.position1.getWorld(),
                (this.getMinPoint().getX() + this.getMaxPoint().getX()) / 2,
                (this.getMinPoint().getY() + this.getMaxPoint().getY()) / 2,
                (this.getMinPoint().getZ() + this.getMaxPoint().getZ()) / 2
        );
    }

    /**
     * Used to get the distance from the center of the region.
     *
     * @param location The instance of the location.
     * @return The distance between the center and given location.
     */
    public double getDistanceFromCenter(@NotNull Location location) {
        return Region3D.getDistance(location, this.getCenter());
    }

    /**
     * Used to get the list of blocks in this region.
     *
     * @return The list of blocks.
     */
    public @NotNull List<Block> getBlockList() {
        List<Block> blockList = new ArrayList<>();

        for (int x = this.getMinPoint().getBlockX(); x < this.getMaxPoint().getBlockX(); x++) {
            for (int y = this.getMinPoint().getBlockY(); y < this.getMaxPoint().getBlockY(); y++) {
                for (int z = this.getMinPoint().getBlockZ(); z < this.getMaxPoint().getBlockZ(); z++) {

                    Location location = new Location(this.position1.getWorld(), x, y, z);
                    blockList.add(location.getBlock());
                }
            }
        }

        return blockList;
    }

    /**
     * Used to expand the region in all directions.
     * <li>
     * The minimum point will be decreased and the max will be increased.
     * </li>
     * <li>
     * This process will also change the location
     * of the 2 positions entered originally.
     * </li>
     *
     * @param x The amount to expand in the x direction.
     * @param y The amount to expand in the y direction.
     * @param z The amount to expand in the z direction.
     * @return This instance.
     */
    public @NotNull Region3D expand(double x, double y, double z) {
        Location min = this.getMinPoint();
        Location max = this.getMaxPoint();

        min.add(-x, -y, -z);
        max.add(x, y, z);

        this.position1 = min;
        this.position2 = max;
        return this;
    }

    /**
     * Used to expand the region in all directions.
     * <li>
     * The minimum point will be decreased and the max will be increased.
     * </li>
     * <li>
     * This process will also change the location
     * of the 2 positions entered originally.
     * </li>
     *
     * @param amount The amount to expand the region by.
     * @return This instance.
     */
    public @NotNull Region3D expand(double amount) {
        this.expand(amount, amount, amount);
        return this;
    }

    /**
     * Used to check if a location is within the region.
     *
     * @param location The instance of the location.
     * @return True if the location is in the region.
     */
    public boolean contains(@NotNull Location location) {
        if (location.getWorld() == null) return false;
        if (this.position1.getWorld() == null) return false;
        if (!location.getWorld().getName().equals(this.position1.getWorld().getName())) return false;

        Location min = getMinPoint();
        Location max = getMaxPoint();

        // Check if the location is within the min and max points.
        return location.getBlockX() >= min.getBlockX() && location.getBlockX() <= max.getBlockX()
                && location.getBlockY() >= min.getBlockY() && location.getBlockY() <= max.getBlockY()
                && location.getBlockZ() >= min.getBlockZ() && location.getBlockZ() <= max.getBlockZ();
    }

    /**
     * Used to check if the region contains a material.
     * <li>
     * To find the material, the method goes though each
     * location, checking for the material.
     * </li>
     *
     * @param material The material to look for.
     * @return True if the region contains the material.
     */
    public boolean contains(@NotNull Material material) {
        // Loop though all blocks.
        for (int x = this.getMinPoint().getBlockX(); x < this.getMaxPoint().getBlockX(); x++) {
            for (int y = this.getMinPoint().getBlockY(); y < this.getMaxPoint().getBlockY(); y++) {
                for (int z = this.getMinPoint().getBlockZ(); z < this.getMaxPoint().getBlockZ(); z++) {

                    Location location = new Location(this.position1.getWorld(), x, y, z);
                    if (location.getBlock().getType() == material) return true;
                }
            }
        }

        return false;
    }

    /**
     * Used to check if the region is all 1 type of material.
     * <li>
     * This method has a time complexity of O(n) ~ O(2n)
     * where n is the number of blocks.
     * </li>
     *
     * @param material The material to check for.
     * @return True if the region is all 1 material.
     */
    public boolean isMaterial(@NotNull Material material) {
        for (Block block : this.getBlockList()) {
            if (block.getType() != material) return false;
        }

        return true;
    }

    /**
     * Used to check if the region is all 1 type of material.
     * <li>
     * This method has a time complexity of O(n) ~ O(2n)
     * where n is the number of blocks.
     * </li>
     *
     * @return True if the region is the same material.
     */
    public boolean isMaterial() {
        return this.isMaterial(this.position1.getBlock().getType());
    }

    /**
     * Used to check if the region is all air.
     * <li>
     * This method has a time complexity of O(n) ~ O(2n)
     * where n is the number of blocks.
     * </li>
     *
     * @return True if the region is all air.
     */
    public boolean isAir() {
        return this.isMaterial(Material.AIR);
    }

    /**
     * Used to get the distance between two locations
     * in 3 dimensions.
     *
     * @param position1 The first position.
     * @param position2 The second position.
     * @return The distance between the two locations.
     */
    public static double getDistance(@NotNull Location position1, @NotNull Location position2) {
        return Math.abs(Math.sqrt(
                Math.pow((position1.getX() - position2.getX()), 2) +
                        Math.pow((position1.getY() - position2.getY()), 2) +
                        Math.pow((position1.getZ() - position2.getZ()), 2)
        ));
    }
}
