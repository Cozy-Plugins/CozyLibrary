package com.github.cozyplugins.cozylibrary.location;

import com.github.squishylib.common.indicator.Replicable;
import com.github.squishylib.configuration.ConfigurationSection;
import com.github.squishylib.configuration.implementation.MemoryConfigurationSection;
import com.github.squishylib.configuration.indicator.ConfigurationConvertible;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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

    private final @NotNull @NoOverlaps List<Cuboid> cuboids;

    public Region() {
        this.cuboids = new ArrayList<>();
    }

    public Region(@NotNull Location pos1, @NotNull Location pos2) {
        Cuboid cuboid = new Cuboid(pos1, pos2);
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

    public double getArea3D() {
        double area = 0;
        for (Cuboid cuboid : this.cuboids) {
            area += cuboid.getArea3D();
        }
        return area;
    }

    public double getArea2D(@NotNull Face face) {
        double area = 0;
        for (Cuboid cuboid : this.cuboids) {
            area += cuboid.getArea2D(face);
        }
        return area;
    }

    /**
     * This will recalculate the cuboids so there are no overlaps.
     */
    public @NotNull Region append(@NotNull Cuboid cuboid) {
        List<Cuboid> newCuboids = new ArrayList<>();

        // Loop though each existing non-overlapping cuboid.
        for (Cuboid existingCuboid : this.cuboids) {

            // Does the existing cuboid overlap the new one?
            // If so split into new cuboids.
            if (!existingCuboid.overlaps(cuboid)) {
                newCuboids.addAll(existingCuboid.split(cuboid));
                continue;
            }

            newCuboids.add(existingCuboid);
        }

        // Update the region with the new cuboids.
        this.cuboids.clear();
        this.cuboids.addAll(newCuboids);
        return this;
    }


    public boolean contains(@NotNull Location location) {
        for (Cuboid cuboid : this.cuboids) {
            if (cuboid.contains(location)) return true;
        }
        return false;
    }

    public boolean contains(@NotNull Material material) {
        for (Cuboid cuboid : this.cuboids) {
            if (cuboid.contains(material)) return true;
        }
        return false;
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

        int i = 0;
        for (Cuboid cuboid : this.cuboids) {
            section.set(String.valueOf(i), cuboid.convertToMap());
            i++;
        }

        return section;
    }

    @Override
    public @NotNull Region convert(@NotNull ConfigurationSection section) {

        for (String key : section.getKeys()) {
            this.cuboids.add(new Cuboid(section.getSection(key)));
        }

        return this;
    }
}
