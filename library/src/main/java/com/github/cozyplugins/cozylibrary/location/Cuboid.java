package com.github.cozyplugins.cozylibrary.location;

import com.github.cozyplugins.cozylibrary.indicator.Replicable;
import com.github.squishylib.configuration.ConfigurationSection;
import com.github.squishylib.configuration.implementation.MemoryConfigurationSection;
import com.github.squishylib.configuration.indicator.ConfigurationConvertible;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.error.YAMLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cuboid implements Replicable<Cuboid>, ConfigurationConvertible<Cuboid> {

    private @NotNull Location pos1;
    private @NotNull Location pos2;

    public Cuboid() {}

    public Cuboid(@NotNull Location position) {
        this.pos1 = position;
        this.pos2 = position;
    }

    public Cuboid(@NotNull Location position, double expand) {
        this.pos1 = position;
        this.pos2 = position;
        this.expand(expand);
    }

    /**
     * Creates a cuboid between the two points.
     *
     * @param position1 The first position.
     * @param position2 The second position.
     * @throws IllegalArgumentException If the positions are in different worlds.
     */
    public Cuboid(@NotNull Location position1, @NotNull Location position2) {

        // Ensure the positions are in the same world.
        if (position1.getWorld() == null || position2.getWorld() == null
            || !position1.getWorld().getName().equals(position2.getWorld().getName())) {

            throw new IllegalArgumentException("Positions must be in the same world to create a cuboid.");
        }

        this.pos1 = position1;
        this.pos2 = position2;
    }

    /**
     * Creates a cuboid given a cuboid configuration section.
     *
     * @param section The instance of the configuration.
     * @throws IllegalArgumentException If the positions are in different worlds.
     */
    public Cuboid(@NotNull ConfigurationSection section) {

        // Convert the section to a cuboid.
        this.convert(section);

        // Ensure the positions are in the same world.
        if (this.pos1.getWorld() == null || this.pos2.getWorld() == null
            || !this.pos1.getWorld().getName().equals(this.pos2.getWorld().getName())) {

            throw new IllegalArgumentException("Positions must be in the same world to create a cuboid.");
        }
    }

    @Override
    public String toString() {
        assert this.pos1.getWorld() != null;
        return "world: " + this.pos1.getWorld().getName()
            + ", pos1: " + this.pos1.getX() + " " + this.pos1.getY() + " " + this.pos1.getZ()
            + ", pos2: " + this.pos2.getX() + " " + this.pos2.getY() + " " + this.pos2.getZ();
    }

    @Override
    public Cuboid duplicate() {
        return new Cuboid(this.pos1.clone(), this.pos2.clone());
    }

    @Override
    public @NotNull ConfigurationSection convert() {
        ConfigurationSection section = new MemoryConfigurationSection();

        assert this.pos1.getWorld() != null;

        section.set("world", this.pos1.getWorld().getName());

        section.set("position1.x", this.pos1.getX());
        section.set("position1.y", this.pos1.getY());
        section.set("position1.z", this.pos1.getZ());

        section.set("position2.x", this.pos2.getX());
        section.set("position2.y", this.pos2.getY());
        section.set("position2.z", this.pos2.getZ());

        return section;
    }

    @Override
    public Cuboid convert(ConfigurationSection section) {
        World world = null;
        try {
            world = Bukkit.getWorld(section.getString("world", null));
        } catch (Exception exception) {
            System.out.println("Section didnt contain a valid world?");
            System.out.println(section.getMap());
            throw new RuntimeException(exception);
        }

        // Check if the world is null.
        if (world == null) {
            throw new YAMLException(
                "Unable to load world from configuration. World does not exists : " + section.getMap()
            );
        }

        // Set first position.
        this.pos1 = new Location(
            world,
            (double) section.get("position1.x"),
            (double) section.get("position1.y"),
            (double) section.get("position1.z")
        );

        // Set second position.
        this.pos2 = new Location(
            world,
            (double) section.get("position2.x"),
            (double) section.get("position2.y"),
            (double) section.get("position2.z")
        );

        return this;
    }

    public @NotNull Location getPos1() {
        return this.pos1;
    }

    public @NotNull Location getPos2() {
        return this.pos2;
    }

    /**
     * @return The smallest x, z and y value as a location.
     */
    public @NotNull Location getMinPoint() {
        return new Location(
            this.pos1.getWorld(),
            Math.min(this.pos1.getBlockX(), this.pos2.getBlockX()),
            Math.min(this.pos1.getBlockY(), this.pos2.getBlockY()),
            Math.min(this.pos1.getBlockZ(), this.pos2.getBlockZ())
        );
    }

    /**
     * @return The largest x, z and y value as a location.
     */
    public @NotNull Location getMaxPoint() {
        return new Location(
            this.pos1.getWorld(),
            Math.max(this.pos1.getBlockX(), this.pos2.getBlockX()),
            Math.max(this.pos1.getBlockY(), this.pos2.getBlockY()),
            Math.max(this.pos1.getBlockZ(), this.pos2.getBlockZ())
        );
    }

    /**
     * @return The center of the cuboid.
     */
    public @NotNull Location getCenter() {
        return new Location(
            this.pos1.getWorld(),
            (this.getMinPoint().getX() + this.getMaxPoint().getX()) / 2,
            (this.getMinPoint().getY() + this.getMaxPoint().getY()) / 2,
            (this.getMinPoint().getZ() + this.getMaxPoint().getZ()) / 2
        );
    }

    /**
     * @param location A location to measure to.
     * @return The distance between the center and given location.
     */
    public double getDistanceFromCenter(@NotNull Location location) {
        return Cuboid.getDistance(location, this.getCenter());
    }

    public @NotNull List<Block> getBlockList() {
        List<Block> blockList = new ArrayList<>();

        for (int x = this.getMinPoint().getBlockX(); x <= this.getMaxPoint().getBlockX(); x++) {
            for (int y = this.getMinPoint().getBlockY(); y <= this.getMaxPoint().getBlockY(); y++) {
                for (int z = this.getMinPoint().getBlockZ(); z <= this.getMaxPoint().getBlockZ(); z++) {

                    Location location = new Location(this.pos1.getWorld(), x, y, z);
                    blockList.add(location.getBlock());
                }
            }
        }

        return blockList;
    }

    public interface Checker {

        /**
         * @param block A block within the cuboid.
         * @return If it should be returned.
         */
        boolean check(@NotNull Block block);
    }

    /**
     * For big cuboid with lots of blocks.
     * If you're just looking for a certain block this
     * could be more efficient.
     *
     * @param checker Runes for each block.
     * @return The block found.
     */
    public @Nullable Block getBlock(@NotNull Checker checker) {
        for (int x = this.getMinPoint().getBlockX(); x < this.getMaxPoint().getBlockX(); x++) {
            for (int y = this.getMinPoint().getBlockY(); y < this.getMaxPoint().getBlockY(); y++) {
                for (int z = this.getMinPoint().getBlockZ(); z < this.getMaxPoint().getBlockZ(); z++) {

                    // Trigger block iteration.
                    final Block block = new Location(this.pos1.getWorld(), x, y, z).getBlock();
                    boolean end = checker.check(block);
                    if (end) return block;
                }
            }
        }
        return null;
    }

    public double getDistanceX() {
        return this.getMaxPoint().getX() - this.getMinPoint().getX();
    }

    public double getDistanceY() {
        return this.getMaxPoint().getY() - this.getMinPoint().getY();
    }

    public double getDistanceZ() {
        return this.getMaxPoint().getZ() - this.getMinPoint().getZ();
    }

    public double getArea3D() {
        return this.getDistanceX() * this.getDistanceY() * this.getDistanceZ();
    }

    /**
     * Imagine putting arrows pointing away from the surface.
     * That's the face direction.
     */
    public double getArea2D(@NotNull Direction face) {
        if (face.equals(Direction.DOWN) || face.equals(Direction.UP)) return this.getDistanceX() * this.getDistanceZ();
        if (face.equals(Direction.NORTH) || face.equals(Direction.SOUTH)) return this.getDistanceY() * this.getDistanceX();
        if (face.equals(Direction.EAST) || face.equals(Direction.WEST)) return this.getDistanceY() * this.getDistanceZ();
        throw new RuntimeException("Unknown face: " + face);
    }

    public double getPerimeter3D() {
        return (this.getDistanceX() * 4) + (this.getDistanceZ() * 4) + (this.getDistanceY() * 4);
    }

    public double getPerimeter2D(@NotNull Direction face) {
        if (face.equals(Direction.DOWN) || face.equals(Direction.UP)) return (this.getDistanceX() * 2) + (this.getDistanceZ() * 2);
        if (face.equals(Direction.NORTH) || face.equals(Direction.SOUTH)) return (this.getDistanceY() * 2) + (this.getDistanceX() * 2);
        if (face.equals(Direction.EAST) || face.equals(Direction.WEST)) return (this.getDistanceY() * 2) + (this.getDistanceZ() * 2);
        throw new RuntimeException("Unknown face: " + face);
    }

    /**
     * Used to expand the cuboid in all directions.
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
    public @NotNull Cuboid expand(double x, double y, double z) {
        Location min = this.getMinPoint();
        Location max = this.getMaxPoint();

        min.add(-x, -y, -z);
        max.add(x, y, z);

        this.pos1 = min;
        this.pos2 = max;
        return this;
    }

    /**
     * Used to expand the cuboid in all directions.
     * <li>
     * The minimum point will be decreased and the max will be increased.
     * </li>
     * <li>
     * This process will also change the location
     * of the 2 positions entered originally.
     * </li>
     *
     * @param amount The amount to expand the cuboid by in all directions.
     * @return This instance.
     */
    public @NotNull Cuboid expand(double amount) {
        this.expand(amount, amount, amount);
        return this;
    }

    /**
     * Used to check if a location is within the cuboid.
     * <p>
     * Super quick method.
     *
     * @param location The location to check.
     * @return True if the location is in the cuboid.
     */
    public boolean contains(@NotNull Location location) {
        if (location.getWorld() == null) return false;
        if (!location.getWorld().getName().equals(
            Objects.requireNonNull(this.pos1.getWorld()).getName()
        )) return false;

        final Location min = this.getMinPoint();
        final Location max = this.getMaxPoint();

        // Check if the location is within the min and max points.
        return location.getBlockX() >= min.getBlockX() && location.getBlockX() <= max.getBlockX()
            && location.getBlockY() >= min.getBlockY() && location.getBlockY() <= max.getBlockY()
            && location.getBlockZ() >= min.getBlockZ() && location.getBlockZ() <= max.getBlockZ();
    }

    public boolean overlaps(@NotNull Cuboid cuboid) {

        final Location min1 = this.getMinPoint();
        final Location max1 = this.getMaxPoint();
        final Location min2 = cuboid.getMinPoint();
        final Location max2 = cuboid.getMaxPoint();

        // Check if they are disjoint along any axis
        boolean disjointX = max1.getX() < min2.getX() || min1.getX() > max2.getX();
        boolean disjointY = max1.getY() < min2.getY() || min1.getY() > max2.getY();
        boolean disjointZ = max1.getZ() < min2.getZ() || min1.getZ() > max2.getZ();

        // If they are disjoint along any axis, they do not overlap
        return !(disjointX || disjointY || disjointZ);
    }

    public @Nullable Cuboid intersect(@NotNull Cuboid other) {
        if (!this.overlaps(other)) return null;

        Location min1 = this.getMinPoint();
        Location max1 = this.getMaxPoint();
        Location min2 = other.getMinPoint();
        Location max2 = other.getMaxPoint();

        Location newMin = new Location(
            min1.getWorld(),
            Math.max(min1.getX(), min2.getX()),
            Math.max(min1.getY(), min2.getY()),
            Math.max(min1.getZ(), min2.getZ())
        );

        Location newMax = new Location(
            min1.getWorld(),
            Math.min(max1.getX(), max2.getX()),
            Math.min(max1.getY(), max2.getY()),
            Math.min(max1.getZ(), max2.getZ())
        );

        return new Cuboid(newMin, newMax);
    }


    /**
     * Used to check if the cuboid contains a material.
     * <li>
     * To find the material, the method goes though each
     * location, checking for the material.
     * </li>
     *
     * @param material The material to look for.
     * @return True if the cuboid contains the material.
     */
    public boolean contains(@NotNull Material material) {
        final Block block = this.getBlock(b -> b.getType() == material);
        return block != null;
    }

    /**
     * Used to check if the cuboid is all 1 type of material.
     * <li>
     * This method has a time complexity of ~ O(n)
     * where n is the number of blocks.
     * </li>
     *
     * @param material The material to check for.
     * @return True if the cuboid is all 1 material.
     */
    public boolean isSameMaterial(@NotNull Material material) {
        final Block block = this.getBlock(t -> t.getType() != material);
        return block == null;
    }

    /**
     * Used to check if the cuboid is all 1 type of material.
     * <li>
     * This method has a time complexity of ~ O(n)
     * where n is the number of blocks.
     * </li>
     *
     * @return True if the cuboid is the same material.
     */
    public boolean isSameMaterial() {
        return this.isSameMaterial(this.pos1.getBlock().getType());
    }

    /**
     * Used to check if the cuboid is all air.
     * <li>
     * This method has a time complexity of O(n)
     * where n is the number of blocks.
     * </li>
     *
     * @return True if the cuboid is all air.
     */
    public boolean isAir() {
        return this.isSameMaterial(Material.AIR);
    }

    /**
     * Split this cube into more cubes so that the other cuboid
     * doesn't overlap this cuboid.
     */
    public List<Cuboid> splitAround(@NotNull Cuboid other) {
        final List<Cuboid> result = new ArrayList<>();
        final Cuboid intersection = this.intersect(other);

        if (intersection == null) {
            result.add(this);
            return result;
        }

        final Location min = this.getMinPoint();
        final Location max = this.getMaxPoint();
        final Location intersectionMin = intersection.getMinPoint();
        final Location intersectionMax = intersection.getMaxPoint();
        final World world = min.getWorld();

        // We work on 3 axes: x (0), y (1), z (2)
        double[] minCoords = { min.getX(), min.getY(), min.getZ() };
        double[] maxCoords = { max.getX(), max.getY(), max.getZ() };
        double[] intersectionMinCoords = { intersectionMin.getX(), intersectionMin.getY(), intersectionMin.getZ() };
        double[] intersectionMaxCoords = { intersectionMax.getX(), intersectionMax.getY(), intersectionMax.getZ() };

        for (int axis = 0; axis < 3; axis++) {
            // Slice before intersection.
            if (minCoords[axis] < intersectionMinCoords[axis]) {
                result.add(this.makeCuboid(
                    world, minCoords, maxCoords, axis,
                    minCoords[axis], intersectionMinCoords[axis]
                ));
            }
            // Slice after intersection.
            if (maxCoords[axis] > intersectionMaxCoords[axis]) {
                result.add(this.makeCuboid(
                    world, minCoords, maxCoords, axis,
                    intersectionMaxCoords[axis], maxCoords[axis]
                ));
            }
        }

        return result;
    }

    private @NotNull Cuboid makeCuboid(World world, double[] minCoords, double[] maxCoords, int axis, double start, double end) {
        double[] newMin = minCoords.clone();
        double[] newMax = maxCoords.clone();
        newMin[axis] = start;
        newMax[axis] = end;

        return new Cuboid(
            new Location(world, newMin[0], newMin[1], newMin[2]),
            new Location(world, newMax[0], newMax[1], newMax[2])
        );
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

    public static List<Block> getLineBlocks(Location start, Location end) {
        List<Block> blocks = new ArrayList<>();

        if (start.getWorld() == null) throw new RuntimeException("start.getWorld() == null");
        if (end.getWorld() == null) throw new RuntimeException("end.getWorld() == null");

        // Ensure both locations are in the same world
        if (!Objects.equals(start.getWorld().getName(), end.getWorld().getName())) {
            throw new IllegalArgumentException("Locations must be in the same world");
        }

        int x1 = start.getBlockX();
        int y1 = start.getBlockY();
        int z1 = start.getBlockZ();
        int x2 = end.getBlockX();
        int y2 = end.getBlockY();
        int z2 = end.getBlockZ();

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int dz = Math.abs(z2 - z1);

        int xs = Integer.compare(x2, x1);
        int ys = Integer.compare(y2, y1);
        int zs = Integer.compare(z2, z1);

        int dx2 = dx * 2;
        int dy2 = dy * 2;
        int dz2 = dz * 2;

        int x = x1;
        int y = y1;
        int z = z1;

        if (dx >= dy && dx >= dz) {
            int yd = dy2 - dx;
            int zd = dz2 - dx;

            for (int i = 0; i <= dx; i++) {
                blocks.add(start.getWorld().getBlockAt(x, y, z));
                if (yd >= 0) {
                    y += ys;
                    yd -= dx2;
                }
                if (zd >= 0) {
                    z += zs;
                    zd -= dx2;
                }
                yd += dy2;
                zd += dz2;
                x += xs;
            }
        } else if (dy >= dx && dy >= dz) {
            int xd = dx2 - dy;
            int zd = dz2 - dy;

            for (int i = 0; i <= dy; i++) {
                blocks.add(start.getWorld().getBlockAt(x, y, z));
                if (xd >= 0) {
                    x += xs;
                    xd -= dy2;
                }
                if (zd >= 0) {
                    z += zs;
                    zd -= dy2;
                }
                xd += dx2;
                zd += dz2;
                y += ys;
            }
        } else {
            int xd = dx2 - dz;
            int yd = dy2 - dz;

            for (int i = 0; i <= dz; i++) {
                blocks.add(start.getWorld().getBlockAt(x, y, z));
                if (xd >= 0) {
                    x += xs;
                    xd -= dz2;
                }
                if (yd >= 0) {
                    y += ys;
                    yd -= dz2;
                }
                xd += dx2;
                yd += dy2;
                z += zs;
            }
        }

        return blocks;
    }
}
