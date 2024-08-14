package com.github.cozyplugins.cozylibrary.task;

import com.github.cozyplugins.cozylibrary.CozyPluginProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a task container.
 * Lets you run tasks in a class.
 */
public class TaskContainer {

    private final @NotNull JavaPlugin plugin;
    private @NotNull Map<String, BukkitTask> taskMap;

    /**
     * Used to create a task container.
     */
    public TaskContainer() {
        this.plugin = CozyPluginProvider.getCozyPlugin().getPlugin();
        this.taskMap = new HashMap<>();
    }

    /**
     * Used to run a task in the future.
     * This will also remove the task once it is run.
     *
     * @param identifier The task identifier.
     * @param task       The instance of the runnable task.
     * @param delayTicks The delay in ticks.
     * @return This instance.
     */
    public @NotNull TaskContainer runTaskLater(@NotNull String identifier, @NotNull Runnable task, long delayTicks) {
        BukkitScheduler scheduler = this.plugin.getServer().getScheduler();
        BukkitTask bukkitTask = scheduler.runTaskLater(this.plugin, () -> {
            this.stopTask(identifier);
            task.run();
        }, delayTicks);
        this.registerTask(identifier, bukkitTask);
        return this;
    }

    /**
     * Used to run a task every x ticks.
     *
     * @param identifier The task's identifier.
     * @param task       The instance of the runnable task.
     * @param delayTicks The delay in ticks.
     * @return This instance.
     */
    public @NotNull TaskContainer runTaskLoop(@NotNull String identifier, @NotNull Runnable task, long delayTicks) {
        BukkitScheduler scheduler = this.plugin.getServer().getScheduler();
        BukkitTask bukkitTask = scheduler.runTaskTimer(this.plugin, task, delayTicks, delayTicks);
        this.registerTask(identifier, bukkitTask);
        return this;
    }

    /**
     * Used to register a task within this class.
     *
     * @param identifier The identifier of the task.
     * @param task       The instance of the task.
     */
    public @NotNull TaskContainer registerTask(@NotNull String identifier, @NotNull BukkitTask task) {
        this.taskMap.put(identifier, task);
        return this;
    }

    /**
     * Used to stop a task.
     *
     * @param identifier The task's identifier.
     */
    public @NotNull TaskContainer stopTask(@NotNull String identifier) {
        if (!this.taskMap.containsKey(identifier)) return this;
        BukkitTask task = this.taskMap.get(identifier);
        this.taskMap.remove(identifier);
        task.cancel();
        return this;
    }

    /**
     * Used to stop all the tasks located in this class.
     */
    public @NotNull TaskContainer stopAllTasks() {
        for (BukkitTask task : this.taskMap.values()) {
            task.cancel();
        }

        this.taskMap = new HashMap<>();
        return this;
    }

    /**
     * Used to check if the task container contains
     * a certain task identifier.
     *
     * @param identifier The task identifier to check for.
     * @return True if the is a task with the identifier running.
     */
    protected boolean containsTask(@NotNull String identifier) {
        return this.taskMap.containsKey(identifier);
    }
}
