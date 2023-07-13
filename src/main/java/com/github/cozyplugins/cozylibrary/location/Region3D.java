package com.github.cozyplugins.cozylibrary.location;

import com.github.cozyplugins.cozylibrary.indicator.Replicable;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a 3 dimensional region.
 */
public class Region3D implements Replicable<Region3D> {

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
    public Region3D duplicate() {
        return new Region3D(this.position1.clone(), this.position2.clone());
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
     * Used to expand the region in all directions.
     *
     * @param amount The amount to expand the region by.
     * @return This instance.
     */
    public @NotNull Region3D expand(double amount) {
        this.position1.add(amount, amount, amount);
        this.position2.add(amount, amount, amount);
        return this;
    }

    /**
     * Used to expand the region in all directions.
     *
     * @param x The amount to expand in the x direction.
     * @param y The amount to expand in the y direction.
     * @param z The amount to expand in the z direction.
     * @return This instance.
     */
    public @NotNull Region3D expand(double x, double y, double z) {
        this.position1.add(x, y, z);
        this.position2.add(x, y, z);
        return this;
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
