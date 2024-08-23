package com.github.cozyplugins.cozylibrary;

import com.github.cozyplugins.cozylibrary.command.CommandManager;
import com.github.cozyplugins.cozylibrary.configuration.CommandDirectory;
import com.github.cozyplugins.cozylibrary.configuration.MessagesConfig;
import com.github.cozyplugins.cozylibrary.dependency.PlaceholderAPIDependency;
import com.github.cozyplugins.cozylibrary.dependency.ProtocolDependency;
import com.github.cozyplugins.cozylibrary.dependency.VaultAPIDependency;
import com.github.cozyplugins.cozylibrary.inventory.InventoryManager;
import com.github.cozyplugins.cozylibrary.inventory.action.ActionManager;
import com.github.cozyplugins.cozylibrary.placeholder.PlaceholderManager;
import com.github.cozyplugins.cozylibrary.scoreboard.ScoreboardManager;
import com.github.squishylib.configuration.directory.ConfigurationDirectory;
import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Represents the base library class.
 * The entry point for a cozy plugin.
 *
 * @param <P> The base plugin class.
 */
public abstract class CozyPlugin<P extends JavaPlugin> {

    private final @NotNull P plugin;
    private final @NotNull CommandManager commandManager;
    private PlaceholderManager<P> placeholderManager;
    private CommandDirectory commandDirectory;
    private MessagesConfig messageConfig;

    /**
     * Used to create a new cozy plugin instance.
     *
     * @param plugin The instance of the plugin loader.
     */
    public CozyPlugin(final @NotNull P plugin) {
        this.plugin = plugin;
        this.commandManager = new CommandManager(plugin.getName());

        // Register this instance with the provider.
        CozyPluginProvider.register(this);
    }

    /**
     * Used to check if the command type configuration
     * directory should be generated.
     *
     * @return True if the command types are enabled.
     */
    public abstract boolean isCommandTypesEnabled();

    /**
     * Called after this plugin has been enabled.
     */
    protected abstract void onEnable();

    /**
     * Called after this plugin has been disabled.
     */
    protected abstract void onDisable();

    /**
     * Called when the commands should be loaded
     * into the manager.
     *
     * @param commandManager The instance of the command manager.
     */
    protected abstract void onLoadCommands(@NotNull CommandManager commandManager);

    /**
     * Called when the placeholders should be loaded
     * into the manager.
     *
     * @param placeholderManager The instance of the placeholder manager.
     */
    protected abstract void onLoadPlaceholders(@NotNull PlaceholderManager<P> placeholderManager);

    /**
     * Used to enable this cozy plugin.
     *
     * @return This instance.
     */
    public @NotNull CozyPlugin<P> enable() {

        // Pre-load nbt api.
        if (!NBT.preloadApi()) {
            this.getPlugin().getLogger().warning("NBT-API wasn't initialized properly, disabling the plugin");
            this.getPlugin().getPluginLoader().disablePlugin(this.getPlugin());
            return this;
        }

        // Set up default messages.
        this.messageConfig = new MessagesConfig(this.getPlugin().getDataFolder(), "messages.yml");
        this.messageConfig.setResourcePath("messages.yml");
        this.messageConfig.load();

        // Load dependencies.
        ProtocolDependency.setup();
        VaultAPIDependency.setup();

        // Set up placeholders.
        if (PlaceholderAPIDependency.isEnabled()) {
            this.placeholderManager = new PlaceholderManager<>(plugin);
            this.onLoadPlaceholders(this.placeholderManager);
            this.placeholderManager.register();
        }

        // Set up the scoreboard manager. This is static so
        // other class can easily change a player's scoreboard.
        // It does not depend on the plugin that changed the scoreboard.
        ScoreboardManager.setup(this.plugin);

        // Load commands.
        this.onLoadCommands(this.commandManager);

        // Check if there should be a command directory
        // for command types.
        if (this.isCommandTypesEnabled()) {
            this.commandDirectory = new CommandDirectory(this);
            this.commandDirectory.addResourcePath("commands.yml");
            this.commandDirectory.load(false);
        }

        // Register commands.
        this.commandManager.registerCommands();

        // Register listeners.
        this.plugin.getServer().getPluginManager().registerEvents(new InventoryManager(), this.plugin);
        this.plugin.getServer().getPluginManager().registerEvents(new ScoreboardManager(), this.plugin);

        // Set up the action manager.
        new ActionManager(this);

        // Call on enable.
        this.onEnable();
        return this;
    }

    /**
     * Used to disable this cozy plugin.
     *
     * @return This instance.
     */
    public @NotNull CozyPlugin<P> disable() {

        // Remove commands.
        this.commandManager.unregisterCommands();
        this.commandManager.removeAll();

        // Remove placeholders.
        if (this.placeholderManager != null) {
            this.placeholderManager.unregister();
            this.placeholderManager.removeAll();
        }

        // Remove inventories registered.
        InventoryManager.removeAll();

        // Stop scoreboards.
        ScoreboardManager.stop();

        // Call on disable.
        this.onDisable();
        return this;
    }

    public @NotNull P getPlugin() {
        return this.plugin;
    }

    public @NotNull CommandManager getCommandManager() {
        return this.commandManager;
    }

    public @NotNull Optional<PlaceholderManager<P>> getPlaceholderManager() {
        return Optional.ofNullable(this.placeholderManager);
    }

    public @NotNull ConfigurationDirectory getCommandDirectory() {
        return this.commandDirectory;
    }

    public @NotNull MessagesConfig getMessageConfig() {
        return this.messageConfig;
    }
}
