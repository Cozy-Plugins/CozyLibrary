package com.github.cozyplugins.cozylibrary.location;

import com.github.squishylib.common.indicator.Replicable;
import com.github.squishylib.configuration.ConfigurationSection;
import com.github.squishylib.configuration.implementation.MemoryConfigurationSection;
import com.github.squishylib.configuration.indicator.ConfigurationConvertible;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The region will change the cuboids you append.
 * This is so there are no overlaps.
 * <p>
 * When you append a cuboid it will be cut into multiple to
 * ensure there are no overlaps between the cuboids.
 * <p>
 * This is because, if there were overlaps and there are 20 cuboids
 * that is already 2^20 combinations the code will have to check for
 * to calculate the area. That's 1,048,576 combinations to check!
 * <p>
 * Its allot better to just ensure no overlaps.
 */
public class Region implements Replicable<Region>, ConfigurationConvertible<Region> {

    private final @NotNull
    @NoOverlaps List<Cuboid> cuboids;

    public Region() {
        this.cuboids = new ArrayList<>();
    }

    public Region(@NotNull Location pos1, @NotNull Location pos2) {
        Cuboid cuboid = new Cuboid(pos1, pos2);
        this.cuboids = new ArrayList<>();
        this.cuboids.add(cuboid);
    }

    public Region(@NotNull Cuboid cuboid) {
        this.cuboids = new ArrayList<>();
        this.cuboids.add(cuboid);
    }

    /**
     * Please ensure that no overlap.
     * This is really meant for duplicating a region.
     * Since regions ensure no overlaps.
     */
    public Region(@NoOverlaps @NotNull List<Cuboid> cuboids) {
        this.cuboids = cuboids;
    }

    public @NotNull List<Cuboid> getCuboids() {
        return this.cuboids;
    }

    public @Nullable Cuboid getCuboidThatContains(@NotNull Location location) {
        for (Cuboid cuboid : this.cuboids) {
            if (cuboid.contains(location)) return cuboid;
        }
        return null;
    }

    public @Nullable Cuboid getCuboidThatOverlaps(@NotNull Cuboid cuboid) {
        for (Cuboid temp : this.cuboids) {
            if (temp.overlaps(cuboid)) return cuboid;
        }
        return null;
    }

    /**
     * Finds the min and max point to create a cuboid out of.
     *
     * @return The region as a cuboid estimation.
     */
    public @NotNull Cuboid getRegionAsCuboid() {
        return new Cuboid(this.getMinPoint(), this.getMaxPoint());
    }

    public boolean isEmpty() {
        return this.cuboids.isEmpty();
    }

    public @NotNull Location getMinPoint() {
        if (this.cuboids.isEmpty()) throw new RuntimeException("Region.getMinPoint() called on a empty region.");
        final Location firstMin = this.cuboids.get(0).getMinPoint();

        double minX = firstMin.getX();
        double minY = firstMin.getY();
        double minZ = firstMin.getZ();

        for (Cuboid cuboid : this.cuboids) {
            final Location location = cuboid.getMinPoint();
            if (location.getX() < minX) minX = location.getX();
            if (location.getY() < minY) minY = location.getY();
            if (location.getZ() < minZ) minZ = location.getZ();
        }

        return new Location(firstMin.getWorld(), minX, minY, minZ);
    }

    public @NotNull Location getMaxPoint() {
        if (this.cuboids.isEmpty()) throw new RuntimeException("Region.getMaxPoint() called on a empty region.");
        final Location firstMax = this.cuboids.get(0).getMaxPoint();

        double maxX = firstMax.getX();
        double maxY = firstMax.getX();
        double maxZ = firstMax.getX();

        for (Cuboid cuboid : this.cuboids) {
            final Location location = cuboid.getMaxPoint();
            if (location.getX() > maxX) maxX = location.getX();
            if (location.getY() > maxY) maxY = location.getY();
            if (location.getZ() > maxZ) maxZ = location.getZ();
        }

        return new Location(firstMax.getWorld(), maxX, maxY, maxZ);
    }

    /**
     * Used to check if there is a cuboid in this region
     * in the direction from a specific location.
     */
    public @Nullable Cuboid getCuboid(@NotNull Location from, @NotNull Direction direction) {
        if (!this.containsWithinMinMax(from)) return null;
        final Cuboid find = this.getCuboidThatOverlaps(new Cuboid(from, from.clone().add(direction.getVector().multiply(5))));
        if (find == null) return this.getCuboid(from.clone().add(direction.getVector().multiply(5)), direction);
        return find;
    }

    public boolean hasCuboid(@NotNull Location from, @NotNull Direction direction) {
        return this.getCuboid(from, direction) != null;
    }

    public @NotNull World getWorld() {
        return this.getCuboids().get(0).getWorld();
    }

    /**
     * The area of the region.
     * <p>
     * Adds all the cuboid areas together.
     */
    public double getArea3D() {
        double area = 0;
        for (Cuboid cuboid : this.cuboids) {
            area += cuboid.getArea3D();
        }
        return area;
    }

    /**
     * Imagine casting a shadow in the face direction.
     * It will be the area of the shadow.
     */
    public double getArea2D(@NotNull Direction face) {
        double area = 0;
        for (Cuboid cuboid : this.cuboids) {

            area += cuboid.getArea2D(face);
        }
        return area;
    }

    public @NotNull List<Block> getPerimeterBlocks() {
        List<Block> perimeter = new ArrayList<>();

        for (Cuboid cuboid : this.cuboids) {
            for (Block block : cuboid.getBlockList()) {
                this.handleBlock(block, perimeter);
            }
        }

        return perimeter;
    }

    private void handleBlock(@NotNull Block block, @NotNull List<Block> perimeter) {
        final Cuboid checkingCuboid = new Cuboid(
            block.getLocation().clone().add(1, 0, 1),
            block.getLocation().clone().add(-1, 0, -1)
        );
        for (Block checkingBlock : checkingCuboid.getBlockList()) {
            if (this.containsWithinCuboids(checkingBlock.getLocation())) continue;
            perimeter.add(checkingBlock);
            return;
        }
    }


    /**
     * This will recalculate the cuboids so there are no overlaps.
     */
    public @NotNull Region append(@NotNull Cuboid cuboid) {
        List<Cuboid> newCuboids = new ArrayList<>();

        // Loop though each existing non-overlapping cuboid.
        for (Cuboid existingCuboid : this.cuboids) {

            // Does the existing cuboid overlap the new one?
            // If so split the existing one into new cuboids.
            if (existingCuboid.overlaps(cuboid)) {
                newCuboids.addAll(existingCuboid.splitAround(cuboid));
            }

            // Add the new cuboid.
            newCuboids.add(cuboid);
        }

        // Update the region with the new cuboids.
        this.cuboids.clear();
        this.cuboids.addAll(newCuboids);
        return this;
    }

    public @NotNull Region remove(@NotNull Cuboid cuboid) {
        List<Cuboid> newCuboids = new ArrayList<>();

        // Loop though each existing non-overlapping cuboid.
        for (Cuboid existingCuboid : this.cuboids) {

            // Does the existing cuboid overlap the new one?
            // If so split the existing one into new cuboids.
            if (existingCuboid.overlaps(cuboid)) {
                newCuboids.addAll(existingCuboid.splitAround(cuboid));
            }
        }

        // Update the region with the new cuboids.
        this.cuboids.clear();
        this.cuboids.addAll(newCuboids);
        return this;
    }


    public boolean containsWithinCuboids(@NotNull Location location) {
        for (Cuboid cuboid : this.cuboids) {
            if (cuboid.contains(location)) return true;
        }
        return false;
    }

    public boolean containsWithinCuboids(@NotNull Material material) {
        for (Cuboid cuboid : this.cuboids) {
            if (cuboid.contains(material)) return true;
        }
        return false;
    }

    /**
     * Draws a cuboid around the entire region.
     * It then checks if the location is within it.
     *
     * @param location The location to check.
     * @return True if it's within the min max of the region.
     */
    public boolean containsWithinMinMax(@NotNull Location location) {
        return new Cuboid(this.getMinPoint(), this.getMaxPoint()).contains(location);
    }

    public boolean overlaps(@NotNull Cuboid cuboid) {
        for (Cuboid temp : this.cuboids) {
            if (temp.overlaps(cuboid)) return true;
        }
        return false;
    }

    public boolean isSameMaterial(@NotNull Material material) {
        for (Cuboid cuboid : this.cuboids) {
            if (!cuboid.isSameMaterial(material)) return false;
        }
        return true;
    }

    @Override
    public @NotNull Region duplicate() {
        final Region region = new Region();

        for (Cuboid cuboid : this.cuboids) {
            region.append(cuboid.duplicate());
        }

        return region;
    }

    @Override
    public @NotNull ConfigurationSection convert() {
        final ConfigurationSection section = new MemoryConfigurationSection();

        for (Cuboid cuboid : this.cuboids) {
            section.set("string-" + UUID.randomUUID().toString(), cuboid.convertToMap());
        }

        return section;
    }

    @Override
    public @NotNull Region convert(@NotNull ConfigurationSection section) {

        try {
            for (String key : section.getKeys()) {
                this.cuboids.add(new Cuboid(section.getSection(key)));
            }
        } catch (Exception exception) {
            System.out.println("Unable to convert region?");
            System.out.println(section.getMap());
            throw new RuntimeException(exception);
        }

        return this;
    }
}
