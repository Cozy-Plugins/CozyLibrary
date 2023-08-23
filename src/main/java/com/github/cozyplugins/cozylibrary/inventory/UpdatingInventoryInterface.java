package com.github.cozyplugins.cozylibrary.inventory;

import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an updating inventory interface.
 * Used for when infomation updates while in the interface.
 */
public abstract class UpdatingInventoryInterface extends InventoryInterface {

    private final int delay;
    private BukkitTask task;

    /**
     * Used to create an updating inventory interface.
     *
     * @param size  The size of the inventory.
     * @param title The inventory's title.
     * @param delay The tick delay between updates.
     */
    public UpdatingInventoryInterface(int size, @NotNull String title, int delay) {
        super(size, title);

        this.delay = delay;
    }

    /**
     * Called when the inventory is updating.
     * The inventory will be reset and then this
     * method will be called to fill the inventory.
     *
     * @param player The instance of the player user.
     */
    protected abstract void onInventoryReWrite(PlayerUser player);

    @Override
    protected void onGenerate(PlayerUser player) {

        // Reset the inventory.
        this.resetInventory();

        // Re-write the inventory.
        this.onInventoryReWrite(player);

        // Start update task.
        this.startUpdateTask();
    }

    /**
     * Used to start the update task.
     * This task will end when there are no viewers.
     */
    private void startUpdateTask() {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        this.task = scheduler.runTaskTimer(CozyPlugin.getPlugin(), () -> {

            if (this.getOwner() == null) return;
            if (this.getInventory().getViewers().isEmpty()) this.stopUpdateTask();

            this.onGenerate(new PlayerUser(this.getOwner()));

        }, this.delay, this.delay);
    }

    /**
     * Used to stop the update task.
     */
    private void stopUpdateTask() {
        if (this.task == null) return;
        this.task.cancel();
    }
}
