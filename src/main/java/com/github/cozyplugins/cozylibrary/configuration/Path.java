package com.github.cozyplugins.cozylibrary.configuration;

import com.github.smuddgge.squishyconfiguration.implementation.yaml.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a file path.
 * The location of a file from a starting point.
 */
public class Path {

    private @NotNull File base;
    /**
     * The instance of the path with slashes.
     */
    private @NotNull String path;

    /**
     * Used to create a path.
     */
    public Path(@NotNull File base) {
        this.base = base;
        this.path = "";
    }

    /**
     * Used to create a path.
     * The path given must be with slashes.
     * <li>Example: "folder/file_name.yml</li>
     *
     * @param path The default path.
     */
    public Path(@NotNull File base, @NotNull String path) {
        this.base = base;
        this.path = path;
    }

    /**
     * Used to get the path with slashes.
     *
     * @return The path with slashes.
     */
    public @NotNull String getSlashPath() {
        return this.path;
    }

    /**
     * Used to get the path with dots.
     * This is commonly used with configuration files.
     *
     * @return The path with dots.
     */
    public @NotNull String getDotPath() {
        return this.path.replace("/", ".");
    }

    /**
     * Used to get the path as a list.
     * <li>
     * Example: test/file.txt -> [test, file.txt]
     * </li>
     *
     * @return The path as a list of items.
     */
    public @NotNull List<String> getAsList() {
        // Check if the path doesn't contain any slashes.
        if (!this.path.contains("/")) {
            List<String> list = new ArrayList<>();
            list.add(this.path);
            return list;
        }

        // Return the path as a list.
        return List.of(this.path.split("/"));
    }

    /**
     * Used to get the current location.
     * The name at the top of the path.
     * <li>Example: test/file.txt will return file.txt</li>
     *
     * @return The current top location.
     */
    public @NotNull String getTop() {
        List<String> list = this.getAsList();
        return list.get(list.size() - 1);
    }

    /**
     * Used to check if the path is the base path.
     * Essentially checking if the path string is equal to "".
     *
     * @return True if the path is at its base.
     */
    public boolean isBase() {
        return this.path.equals("");
    }

    /**
     * Used to remove the top location name.
     * <p>
     * Example:
     * <p>
     * folder/file.txt will turn into folder
     * </p>
     * and file.txt will be returned.
     * </p>
     *
     * @return The top location.
     */
    public @Nullable String pop() {
        String top = this.getTop();

        // Remove the top path.
        this.path = this.path.substring(0, this.path.length() - top.length());

        // Return the top.
        return top;
    }

    /**
     * Used to pop a certain amount from the top of the path.
     * This is explained more with the method {@link Path#pop}.
     *
     * @param amount The amount to pop off the top.
     * @return This instance.
     */
    public @NotNull Path pop(int amount) {
        if (amount < 1) return this;

        // Pop 'amount' times.
        for (int i = amount; i > 0; i--) {
            this.pop();
        }

        return this;
    }

    /**
     * Used to create a folder in this path location.
     *
     * @param name The name of the folder.
     * @return True if the folder was created.
     */
    public boolean createFolder(@NotNull String name) {
        File folder = new File(this.base.getAbsolutePath() + "/" + this.getSlashPath() + "/" + name);

        boolean success;
        try {
            success = folder.mkdir();
        } catch (Exception exception) {
            exception.printStackTrace();
            success = false;
        }

        return success;
    }

    /**
     * Used to create a yaml configuration file.
     *
     * @param name The name of the file without extensions.
     * @return This instance.
     */
    public @NotNull Path createYamlConfigurationFile(@NotNull String name) {
        YamlConfiguration configuration = new YamlConfiguration(
                new File(this.base.getAbsoluteFile() + "/" + this.getSlashPath() + "/" + name + ".yml")
        );
        configuration.load();
        return this;
    }
}
