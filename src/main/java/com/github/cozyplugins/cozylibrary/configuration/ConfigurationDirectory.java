package com.github.cozyplugins.cozylibrary.configuration;

import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.smuddgge.squishyconfiguration.implementation.yaml.YamlConfiguration;
import com.github.smuddgge.squishyconfiguration.interfaces.Configuration;
import com.github.smuddgge.squishyconfiguration.memory.MemoryConfigurationSection;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
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

    /**
     * <h1>Used to create a configuration directory</h1>
     *
     * @param directoryName The name of the directory.
     */
    public ConfigurationDirectory(@NotNull String directoryName) {
        super(new HashMap<>());
        this.directoryName = directoryName;
    }

    /**
     * <h1>Used to create a default configuration file</h1>
     *
     * @param folder The folder to create the file in.
     * @return The instance of the configuration file.
     */
    public abstract @Nullable Configuration createDefaultConfiguration(@NotNull File folder);

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
        Plugin plugin = Bukkit.getPluginManager().getPlugin(CozyPlugin.getPluginName());
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
        return this.getDirectory().mkdir();
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
            Configuration configuration = this.createDefaultConfiguration(this.getDirectory());
            if (configuration == null) return;
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
