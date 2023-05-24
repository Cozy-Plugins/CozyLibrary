package com.github.cozyplugins.cozylibrary.configuration;

import com.github.cozyplugins.cozylibrary.CozyLibrary;
import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.smuddgge.squishyconfiguration.implementation.yaml.YamlConfiguration;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <h1>Represents a configuration directory</h1>
 * A directory where all the files represent a single file.
 * This lets the server operator organise files by
 * creating new directory's and splitting files,
 * yet with this class it will be as if it was a single file.
 */
public abstract class ConfigurationDirectory extends MemoryConfigurationSection {

    private final @NotNull String directoryName;
    private final @NotNull Class<? extends CozyPlugin> clazz;

    /**
     * <h1>Used to create a configuration directory</h1>
     *
     * @param directoryName The name of the directory.
     */
    public ConfigurationDirectory(@NotNull String directoryName, @NotNull Class<? extends CozyPlugin> clazz) {
        super(new HashMap<>());
        this.directoryName = directoryName;
        this.clazz = clazz;
    }

    /**
     * <h1>Used to get the defaults file name</h1>
     *
     * @return The name of the default file.
     */
    public abstract @Nullable String getDefaultFileName();

    /**
     * <h1>Called when the the configuration file is reloaded</h1>
     */
    protected abstract void onReload();

    /**
     * <h1>Used to get the directory's name</h1>
     *
     * @return The directory's name.
     */
    public @NotNull String getDirectoryName() {
        return this.directoryName;
    }

    /**
     * <h1>Used to get the directory</h1>
     *
     * @return The directory as a file.
     */
    public @NotNull File getDirectory() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(CozyLibrary.getPluginName());
        assert plugin != null;
        return new File(plugin.getDataFolder() + File.separator + this.directoryName);
    }

    /**
     * <h1>Used to get the list of files</h1>
     *
     * @return The list of files.
     */
    public @NotNull List<File> getFiles() {
        return ConfigurationDirectory.getFiles(this.getDirectory());
    }

    /**
     * <h1>Used to get the list of file names</h1>
     *
     * @return The list of file names.
     */
    public @NotNull List<String> getFileNames() {
        List<String> fileNameList = new ArrayList<>();

        for (File file : this.getFiles()) {
            fileNameList.add(file.getName());
        }

        return fileNameList;
    }

    /**
     * <h1>Used to load a configuration file</h1>
     * Adds the configuration file into the directory data.
     *
     * @param configuration The instance of a configuration file.
     */
    public void loadConfigurationFile(@NotNull Configuration configuration) {
        this.data.putAll(configuration.getMap());
    }

    /**
     * <h1>Used to create the directory</h1>
     *
     * @return True if the directory was created.
     */
    protected boolean createDirectory() {
        return this.getDirectory().mkdirs();
    }

    /**
     * <h1>Creates the default file in the directory</h1>
     *
     * @return True If the file gets created.
     */
    public boolean createDefaultFile() {
        // Check if default file name is specified.
        if (this.getDefaultFileName() == null) return false;

        // Make sure the directory is created.
        this.createDirectory();

        try (InputStream input = this.clazz.getResourceAsStream("/" + this.getDefaultFileName())) {

            // Get file instance.
            File file = new File(this.getDirectory(), this.getDefaultFileName());

            if (input != null) {
                Files.copy(input, file.toPath());
                return true;
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * <h1>Used to reload the configuration files</h1>
     */
    public void reload() {
        // Reset the data
        this.data = new HashMap<>();

        // Attempt to create the directory.
        this.createDirectory();

        // If no files were loaded.
        boolean isEmpty = true;

        // Load all files currently in the directory.
        for (File file : this.getFiles()) {
            isEmpty = false;
            this.loadConfigurationFile(new YamlConfiguration(file));
        }

        if (isEmpty) {
            this.createDefaultFile();

            // Load configuration.
            Configuration configuration = new YamlConfiguration(this.getDirectory(), this.getDefaultFileName());
            configuration.load();
            this.loadConfigurationFile(configuration);
        }

        this.onReload();
    }

    /**
     * <h1>Used to get the files from a directory</h1>
     * Checks for directory's located in the folder that
     * contain files.
     *
     * @param folder The instance of a folder.
     * @return The list of requested files.
     */
    public static @NotNull List<File> getFiles(@NotNull File folder) {
        File[] fileList = folder.listFiles();
        if (fileList == null) return new ArrayList<>();

        List<File> finalFileList = new ArrayList<>();
        for (File file : fileList) {
            List<File> filesInFile = ConfigurationDirectory.getFiles(file);
            if (filesInFile.isEmpty()) {
                finalFileList.add(file);
                continue;
            }

            finalFileList.addAll(filesInFile);
        }

        return finalFileList;
    }
}
