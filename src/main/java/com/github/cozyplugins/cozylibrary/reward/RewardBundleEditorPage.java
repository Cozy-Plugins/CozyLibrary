package com.github.cozyplugins.cozylibrary.reward;

import com.github.cozyplugins.cozylibrary.inventory.CozyInventory;
import com.github.cozyplugins.cozylibrary.inventory.InventoryItem;
import com.github.cozyplugins.cozylibrary.inventory.action.action.AnvilValueAction;
import com.github.cozyplugins.cozylibrary.inventory.action.action.ClickAction;
import com.github.cozyplugins.cozylibrary.item.CozyItem;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the pages in the reward bundle editor.
 */
public enum RewardBundleEditorPage {
    MAIN {
        @Override
        public void generate(@NotNull RewardBundleEditorInventory inventory, @NotNull PlayerUser user) {
            // Item page button.
            inventory.insertItem(new InventoryItem()
                    .setMaterial(Material.IRON_SWORD)
                    .setName("&6&lChange Items")
                    .setLore("&7Click to change the items that will be given as a reward.")
                    .addSlot(29)
                    .addAction((ClickAction) (user1, type, inventory1) -> {
                        inventory.setPage(RewardBundleEditorPage.ITEM);
                        inventory.onGenerate(user1);
                    })
            );

            // Command page button.
            inventory.insertItem(new InventoryItem()
                    .setMaterial(Material.COMMAND_BLOCK)
                    .setName("&6&lChange Commands")
                    .setLore("&7Click to change the commands that will be executed.")
                    .addSlot(31)
                    .addAction((ClickAction) (user1, type, inventory12) -> {
                        inventory.setPage(RewardBundleEditorPage.COMMAND);
                        inventory.onGenerate(user1);
                    })
            );

            // Set Money.
            inventory.insertItem(new InventoryItem()
                    .setMaterial(Material.KELP)
                    .setName("&6&lSet Money")
                    .setLore("&7Click to change the amount of money",
                            "&7that will be given.",
                            "&aCurrently &e" + inventory.getBundle().getMoney())
                    .addSlot(33)
                    .addAction(new AnvilValueAction()
                            .setAnvilTitle("&8&lMoney")
                            .setAction((value, user1) -> {
                                if (value != null) {
                                    try {
                                        double money = Double.parseDouble(value);
                                        inventory.getBundle().setMoney(money);
                                        inventory.onBundleUpdate(inventory.getBundle());
                                    } catch (Exception exception) {
                                        user1.sendMessage("&7Unable to convert string to double. " +
                                                "More info in console. " +
                                                "Value entered : " + value);
                                        exception.printStackTrace();
                                    }
                                }

                                inventory.open(user1.getPlayer());
                            }))
            );
        }

        @Override
        public void goBack(@NotNull RewardBundleEditorInventory inventory, @NotNull PlayerUser user) {
            CozyInventory backInventory = inventory.onBackButton(user);
            user.getPlayer().closeInventory();

            // Check if the inventory is null.
            if (backInventory == null) return;

            // Open the back inventory.
            backInventory.open(user.getPlayer());
        }
    }, ITEM {
        @Override
        public void generate(@NotNull RewardBundleEditorInventory inventory, @NotNull PlayerUser user) {
            inventory.setPlaceable(true);

            // Generate items.
            for (CozyItem item : inventory.getBundle().getItemList()) {
                inventory.insertItem(new InventoryItem(item.create()));
            }
        }

        @Override
        public void goBack(@NotNull RewardBundleEditorInventory inventory, @NotNull PlayerUser user) {
            // Add items in inventory to the bundle.
            List<CozyItem> itemList = new ArrayList<>();
            int slot = -1;
            for (ItemStack item : inventory.getInventory().getContents()) {
                slot++;
                if (item == null) continue;
                if (slot >= 45) continue;

                itemList.add(new CozyItem(item));
            }

            // Add items to the bundle.
            inventory.getBundle().setItemList(itemList);

            // Set main inventory.
            inventory.setPlaceable(false);
            inventory.setPage(RewardBundleEditorPage.MAIN);
            inventory.onGenerate(user);
        }
    }, COMMAND {
        @Override
        public void generate(@NotNull RewardBundleEditorInventory inventory, @NotNull PlayerUser user) {
            // Add command button.
            inventory.insertItem(new InventoryItem()
                    .setMaterial(Material.LIME_STAINED_GLASS_PANE)
                    .setName("&a&lCreate A Command")
                    .setLore("&7Click to create a command that",
                            "&7will be executed with the rewards.",
                            "&7You may use the placeholder &f{player}",
                            "&7for the players name.")
                    .addSlot(49)
                    .addAction(new AnvilValueAction()
                            .setAnvilTitle("&8&lCommand")
                            .setAction((value, user1) -> {
                                if (value != null) {
                                    inventory.getBundle().getCommandList().add(value);
                                    inventory.onBundleUpdate(inventory.getBundle());
                                }

                                inventory.open(user1.getPlayer());
                            }))
            );

            // Command items.
            for (String command : inventory.getBundle().getCommandList()) {
                inventory.insertItem(new InventoryItem()
                        .setMaterial(Material.COMMAND_BLOCK)
                        .setName("&f" + command)
                        .setLore("&7Click to remove command.")
                        .addAction((ClickAction) (user1, type, inventory1) -> {
                            inventory.getBundle().getCommandList().remove(command);
                            inventory.onGenerate(user1);
                        })
                );
            }
        }

        @Override
        public void goBack(@NotNull RewardBundleEditorInventory inventory, @NotNull PlayerUser user) {
            inventory.setPage(RewardBundleEditorPage.MAIN);
            inventory.onGenerate(user);
        }
    };

    /**
     * Used to generate the page.
     *
     * @param inventory The instance of the inventory.
     * @param user      The instance of the user.
     */
    public abstract void generate(@NotNull RewardBundleEditorInventory inventory, @NotNull PlayerUser user);

    /**
     * Called when going back.
     *
     * @param inventory The instance of the inventory.
     */
    public abstract void goBack(@NotNull RewardBundleEditorInventory inventory, @NotNull PlayerUser user);
}
