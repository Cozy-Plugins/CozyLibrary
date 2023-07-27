package com.github.cozyplugins.cozylibrary.reward;

import com.github.cozyplugins.cozylibrary.inventory.InventoryInterface;
import com.github.cozyplugins.cozylibrary.inventory.InventoryItem;
import com.github.cozyplugins.cozylibrary.inventory.action.ActionResult;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ClickAction;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ClickActionWithResult;
import com.github.cozyplugins.cozylibrary.item.CozyItem;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a reward bundle editor inventory.
 */
public abstract class RewardBundleEditorInventory extends InventoryInterface {

    private final @NotNull RewardBundle rewardBundle;
    private @NotNull RewardBundleEditorPage page;

    /**
     * Used to create a reward bundle editor.
     *
     * @param rewardBundle The instance of a reward bundle to edit.
     */
    public RewardBundleEditorInventory(@NotNull RewardBundle rewardBundle) {
        super(54, "&8&lReward Bundle Editor");

        this.rewardBundle = rewardBundle;
        this.page = RewardBundleEditorPage.MAIN;
    }

    /**
     * Called when the reward bundle is updated.
     *
     * @param bundle The instance of the reward bundle.
     */
    protected abstract void onBundleUpdate(@NotNull RewardBundle bundle);

    /**
     * When the back button is pressed it will open the inventory returned.
     * If the inventory is null, there will be no back button.
     *
     * @return The inventory to go back to when the back button is pressed.
     */
    protected abstract @Nullable InventoryInterface onBackButton(@NotNull PlayerUser user);

    @Override
    protected void onGenerate(PlayerUser player) {
        // Reset the inventory.
        this.resetInventory();

        // Set background.
        this.setItem(new InventoryItem()
                .setMaterial(Material.GRAY_STAINED_GLASS_PANE)
                .setName("&7")
                .addSlotRange(45, 53)
                .addAction((ClickActionWithResult) (user, type, inventory, currentResult, slot, event)
                        -> new ActionResult().setCancelled(true))
        );

        // Set back button.
        this.setItem(new InventoryItem()
                .setMaterial(Material.LIME_STAINED_GLASS_PANE)
                .setName("&a&lBack")
                .setLore("&7Click to go back.")
                .addSlot(45)
                .addAction((ClickActionWithResult) (user, type, inventory, currentResult, slot, event) -> {
                    this.page.goBack(this, user);
                    return new ActionResult().setCancelled(true);
                })
        );

        // Generate page.
        this.page.generate(this, player);
    }

    /**
     * Used to get the page currently displayed.
     *
     * @return The page currently displayed.
     */
    public @NotNull RewardBundleEditorPage getPage() {
        return this.page;
    }

    /**
     * Used to get the reward bundle.
     *
     * @return The instance of the reward bundle.
     */
    public @NotNull RewardBundle getBundle() {
        return this.rewardBundle;
    }

    /**
     * Used to set the page of the inventory.
     *
     * @param page The instance of the page.
     * @return This instance.
     */
    public @NotNull RewardBundleEditorInventory setPage(RewardBundleEditorPage page) {
        this.page = page;
        return this;
    }

    /**
     * Used to put an item into the inventory from outside the class.
     *
     * @param item The instance of a item.
     */
    public void insertItem(@NotNull InventoryItem item) {
        this.setItem(item);
    }
}
