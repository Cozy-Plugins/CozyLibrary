package com.github.cozyplugins.cozylibrary.hologram;

import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.squishylib.configuration.Configuration;
import com.github.squishylib.configuration.implementation.YamlConfiguration;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HologramManager {

    private final @NotNull CozyPlugin<?> plugin;
    private final @NotNull List<Hologram> holograms;

    public HologramManager(@NotNull CozyPlugin<?> plugin) {
        this.plugin = plugin;
        this.holograms = new ArrayList<>();
    }

    public @NotNull List<Hologram> getHolograms() {
        return this.holograms;
    }

    public @Nullable Hologram getHologram(@NotNull String identifier) {
        return this.holograms.stream()
            .filter(h -> h.getIdentifier().equals(identifier))
            .findFirst()
            .orElse(null);
    }

    public @Nullable Hologram getClosestHologram(@NotNull Location location) {
        Hologram closest = null;
        double distance = -1;

        for (Hologram hologram : this.holograms) {

            if (closest == null) {
                closest = hologram;
                distance = hologram.getLocation().distance(location);
                continue;
            }

            double d = hologram.getLocation().distance(location);
            if (d < distance) {
                closest = hologram;
                distance = d;
            }
        }

        return closest;
    }

    public @NotNull Hologram create(@NotNull String identifier, @NotNull Location location) {
        final Hologram hologram = new Hologram(identifier, location);
        this.holograms.add(hologram);
        return hologram;
    }

    public boolean delete(@NotNull String identifier) {
        final Hologram hologram = this.getHologram(identifier);
        if (hologram == null) return false;

        hologram.remove();
        this.holograms.remove(hologram);
        return true;
    }

    public void populateSafely() {
        this.holograms.forEach(Hologram::remove);
        this.holograms.clear();

        final Configuration config = new YamlConfiguration(
            new File(this.plugin.getPlugin().getDataFolder(), "holograms.yml"),
            this.plugin.getPlugin().getClass()
        );
        config.load();

        for (String identifier : config.getKeys()) {
            final Hologram hologram = new Hologram(identifier, config.getSection(identifier));
            hologram.spawnIfAbsent();
            this.holograms.add(hologram);
        }
    }

    public void saveAndRemoveAll() {
        final Configuration config = new YamlConfiguration(
            new File(this.plugin.getPlugin().getDataFolder(), "holograms.yml"),
            this.plugin.getPlugin().getClass()
        );
        config.load();
        config.clear();

        for (Hologram hologram : this.holograms) {
            hologram.remove();
            config.set(hologram.getIdentifier(), hologram.convertToMap());
        }

        this.holograms.clear();
        config.save();
    }
}
