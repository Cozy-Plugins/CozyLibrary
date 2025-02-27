package com.github.cozyplugins.example;

import com.github.cozyplugins.cozylibrary.CozyPlugin;
import org.jetbrains.annotations.NotNull;

public class ExamplePlugin extends CozyPlugin<ExamplePluginLoader> {

    public ExamplePlugin(@NotNull ExamplePluginLoader plugin) {
        super(plugin, false);
    }
}
