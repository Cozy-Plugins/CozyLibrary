package com.github.cozyplugins.cozylibrary.reward;

import com.github.cozyplugins.cozylibrary.indicator.Replicable;
import com.github.cozyplugins.cozylibrary.item.CozyItem;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import com.github.squishylib.configuration.ConfigurationSection;
import com.github.squishylib.configuration.implementation.MemoryConfigurationSection;
import com.github.squishylib.configuration.indicator.ConfigurationConvertible;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a bundle of rewards that can be given to players.
 */
public class RewardBundle implements Replicable<RewardBundle>, ConfigurationConvertible<RewardBundle> {

    private @NotNull List<CozyItem> itemList;
    private @NotNull List<String> commandList;
    private double money;

    /**
     * Used to create a reward bundle.
     */
    public RewardBundle() {
        this.itemList = new ArrayList<>();
        this.commandList = new ArrayList<>();
        this.money = 0;
    }

    /**
     * Used to get the list of item rewards.
     *
     * @return The list of items.
     */
    public @NotNull List<CozyItem> getItemList() {
        return this.itemList;
    }

    /**
     * Used to get the list of commands to execute when rewarded.
     *
     * @return The list of commands.
     */
    public @NotNull List<String> getCommandList() {
        return this.commandList;
    }

    /**
     * Used to get the amount of money to be rewarded.
     *
     * @return The amount of money.
     */
    public double getMoney() {
        return money;
    }

    /**
     * Used to set the amount of money to give.
     *
     * @param money The amount of money.
     * @return This instance.
     */
    public @NotNull RewardBundle setMoney(double money) {
        this.money = money;
        return this;
    }

    /**
     * Used to set the item list.
     *
     * @param itemList The instance of a item list.
     * @return This instance.
     */
    public @NotNull RewardBundle setItemList(@NotNull List<CozyItem> itemList) {
        this.itemList = itemList;
        return this;
    }

    /**
     * Used to give the reward bundle to a user.
     *
     * @param user The instance of the user.
     * @return This instance.
     */
    public @NotNull RewardBundle giveRewardBundle(@NotNull PlayerUser user) {
        // Give the items.
        for (CozyItem item : this.itemList) {
            int freeSlot = user.getPlayer().getInventory().firstEmpty();
            if (freeSlot == -1) continue;
            user.getPlayer().getInventory().setItem(freeSlot, item.create());
        }

        // Run commands as operator.
        user.runCommandsAsOp(this.commandList);

        // Give money.
        if (user.changeMoney(this.money)) {
            if (this.money > 0) {
                user.sendMessage("&7You have received &f$" + this.money + "&7.");
            }
            if (this.money < 0) {
                user.sendMessage("&7&f$" + this.money + " &7has been taken from your balance.");
            }
        }
        return this;
    }

    @Override
    public @NotNull ConfigurationSection convert() {
        ConfigurationSection section = new MemoryConfigurationSection();

        ConfigurationSection itemSection = new MemoryConfigurationSection();
        for (CozyItem item : this.itemList) {
            itemSection.set(UUID.randomUUID().toString(), item.convert().getMap());
        }

        if (!this.itemList.isEmpty()) section.set("items", itemSection.getMap());
        if (!this.commandList.isEmpty()) section.set("commands", this.commandList);
        if (this.money != 0) section.set("money", this.money);

        return section;
    }

    @Override
    public RewardBundle convert(ConfigurationSection section) {
        for (String key : section.getSection("items").getKeys()) {
            CozyItem item = new CozyItem().convert(section.getSection("items").getSection(key));
            this.itemList.add(item);
        }

        this.commandList = section.getListString("commands", new ArrayList<>());
        this.money = section.getClass("money", Double.class, 0d);

        return this;
    }

    @Override
    public RewardBundle duplicate() {
        ConfigurationSection section = this.convert();
        RewardBundle rewardBundle = new RewardBundle();
        rewardBundle.convert(section);
        return rewardBundle;
    }
}
