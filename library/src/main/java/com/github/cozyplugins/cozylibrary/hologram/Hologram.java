package com.github.cozyplugins.cozylibrary.hologram;

import com.github.cozyplugins.cozylibrary.MessageManager;
import com.github.cozyplugins.cozylibrary.indicator.LocationConvertable;
import com.github.squishylib.configuration.ConfigurationSection;
import com.github.squishylib.configuration.implementation.MemoryConfigurationSection;
import com.github.squishylib.configuration.indicator.ConfigurationConvertible;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Hologram implements ConfigurationConvertible<Hologram>, LocationConvertable {

    private final @NotNull String identifier;
    private @SuppressWarnings("all")
    @NotNull Location location;
    private final @NotNull List<String> lines;

    private final @NotNull Map<Integer, ArmorStand> entities;

    public Hologram(@NotNull String identifier, @NotNull Location location) {
        this.identifier = identifier;
        this.location = location;
        this.lines = new ArrayList<>();
        this.entities = new HashMap<>();
    }

    public Hologram(@NotNull String identifier, @NotNull ConfigurationSection section) {
        this.identifier = identifier;
        this.lines = new ArrayList<>();
        this.entities = new HashMap<>();
        this.convert();
        if (this.location == null) throw new RuntimeException(
            "Hologram location cannot be null. section=" + section
        );
    }

    public @NotNull String getIdentifier() {
        return this.identifier;
    }

    public @NotNull Location getLocation() {
        return this.location;
    }

    public @NotNull List<String> getLines() {
        return this.lines;
    }

    public @Nullable String getLine(int index) {
        if (index >= this.lines.size()) return null;
        return this.lines.get(index);
    }

    public @NotNull Hologram setLocation(@NotNull Location location) {
        this.location = location;
        if (this.isSpawned()) {
            this.remove();
            this.spawnIfAbsent();
        }
        return this;
    }

    public @NotNull Hologram setLine(int index, @NotNull String line) {
        if (index < 0)
            throw new RuntimeException("Hologram.setLine(" + index + ", " + line + ") <- cannot be negative.");
        if (index >= this.lines.size()) {
            this.lines.add("&7");
            return this.setLine(index, line);
        }
        this.lines.set(index, line);
        this.updateArmorStand(index, line);
        return this;
    }

    private void updateArmorStand(int index, @NotNull String line) {
        if (this.isAbsent()) return;

        // Get the armor stand for the specific line.
        final ArmorStand stand = this.entities.get(index);

        // Re-create the hologram if it doesn't exist.
        if (stand == null) {
            this.remove();
            this.spawnIfAbsent();
            return;
        }

        // Otherwise update the line.
        stand.setCustomName(line);
    }

    public @NotNull Hologram setLines(@NotNull String line) {
        this.lines.clear();
        this.lines.add(line);
        this.respawnIfSpawned();
        return this;
    }

    public @NotNull Hologram setLines(@NotNull String... lines) {
        this.lines.clear();
        this.lines.addAll(new ArrayList<>(Arrays.asList(lines)));
        this.respawnIfSpawned();
        return this;
    }

    public @NotNull Hologram setLines(@NotNull List<String> lines) {
        this.lines.clear();
        this.lines.addAll(lines);
        this.respawnIfSpawned();
        return this;
    }

    public @NotNull Hologram addLine(@NotNull String line) {
        this.lines.add(line);
        this.respawnIfSpawned();
        return this;
    }

    public @NotNull Hologram addLines(@NotNull String... lines) {
        this.lines.addAll(new ArrayList<>(Arrays.asList(lines)));
        this.respawnIfSpawned();
        return this;
    }

    public @NotNull Hologram addLines(@NotNull List<String> lines) {
        this.lines.addAll(lines);
        this.respawnIfSpawned();
        return this;
    }

    @Override
    public @NotNull ConfigurationSection convert() {
        final ConfigurationSection section = new MemoryConfigurationSection();
        section.set("location", this.convertLocation(this.location));
        section.set("lines", this.lines);
        return section;
    }

    @Override
    public @NotNull Hologram convert(@NotNull ConfigurationSection section) {
        this.location = this.convertLocation(section.getSection("location"));
        this.lines.clear();
        this.lines.addAll(section.getListString("lines", new ArrayList<>()));
        return this;
    }

    /* Entity logic */

    public boolean isSpawned() {
        return !this.entities.isEmpty();
    }

    public boolean isAbsent() {
        return this.entities.isEmpty();
    }

    public @NotNull Hologram spawnIfAbsent() {

        // Does the hologram already exist?
        if (this.isAbsent()) return this;

        // Check if the world exists.
        final World world = this.getLocation().getWorld();
        if (world == null) throw new RuntimeException("Hologram.spawnIfAbsent() <- World was null?");

        // Get the initial location.
        Location lineLocation = this.getLocation();
        int index = 0;

        for (String line : this.lines) {

            // Spawn the line in as an armor stand far above where it's meant to be.
            final ArmorStand entity = (ArmorStand) world.spawnEntity(
                lineLocation.clone().add(0, 200, 0),
                EntityType.ARMOR_STAND
            );

            entity.setVisible(false);
            entity.setGravity(false);
            entity.setInvulnerable(true);
            entity.setCustomName(MessageManager.parse(line));
            entity.setCustomNameVisible(true);
            entity.setBasePlate(false);
            this.entities.put(index, entity);

            // Teleport the line to its exact position.
            entity.teleport(lineLocation.clone());

            // Move the line location down for the next line.
            lineLocation.add(0, -1, 0);
            index++;
        }

        return this;
    }

    public @NotNull Hologram remove() {
        this.entities.forEach((index, entity) -> entity.remove());
        this.entities.clear();
        return this;
    }

    public @NotNull Hologram respawnIfSpawned() {
        if (this.isAbsent()) return this;
        this.remove();
        this.spawnIfAbsent();
        return this;
    }
}
