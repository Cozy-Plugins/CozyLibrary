package com.github.cozyplugins.example;

import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.cozylibrary.command.CommandManager;
import com.github.cozyplugins.cozylibrary.placeholder.PlaceholderManager;
import org.jetbrains.annotations.NotNull;

public class ExamplePlugin extends CozyPlugin<ExamplePluginLoader> {

    public ExamplePlugin(@NotNull ExamplePluginLoader plugin) {
        super(plugin);
    }

    @Override
    public boolean isCommandTypesEnabled() {
        return false;
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    @Override
    protected void onLoadCommands(@NotNull CommandManager commandManager) {

    }

    @Override
    protected void onLoadPlaceholders(@NotNull PlaceholderManager<ExamplePluginLoader> placeholderManager) {

    }
}
