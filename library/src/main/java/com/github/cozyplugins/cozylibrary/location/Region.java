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
import java.util.Map;

public class Region implements Replicable<Region>, ConfigurationConvertible<Region> {

    private final @NotNull List<Cuboid> cuboids;

    public Region() {
        this.cuboids = new ArrayList<>();
    }

    public Region(@NotNull Location pos1, @NotNull Location pos2) {
        Cuboid cuboid = new Cuboid(pos1, pos2);
        this.cuboids = new ArrayList<>();
        this.cuboids.add(cuboid);
    }

    public Region(@NotNull List<Cuboid> cuboids) {
        this.cuboids = cuboids;
    }

    public @NotNull List<Cuboid> getCuboids() {
        return this.cuboids;
    }

    public @NotNull Region append(@NotNull Cuboid cuboid) {
        this.cuboids.add(cuboid);
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
