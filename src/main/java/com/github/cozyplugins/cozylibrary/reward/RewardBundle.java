package com.github.cozyplugins.cozylibrary.reward;

import com.github.cozyplugins.cozylibrary.indicator.ConfigurationConvertable;
import com.github.cozyplugins.cozylibrary.indicator.Replicable;
import com.github.smuddgge.squishyconfiguration.interfaces.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a bundle of rewards that can be given to players.
 */
public class RewardBundle implements Replicable<RewardBundle>, ConfigurationConvertable {

    public RewardBundle() {

    }

    @Override
    public @NotNull ConfigurationSection convert() {
        return null;
    }

    @Override
    public void convert(ConfigurationSection section) {

    }

    @Override
    public RewardBundle duplicate() {
        return null;
    }
}
