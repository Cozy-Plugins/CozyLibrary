package com.github.cozyplugins.cozylibrary.dependency;

import com.github.cozyplugins.cozylibrary.ConsoleManager;
import com.github.cozyplugins.cozylibrary.CozyPlugin;
import com.github.cozyplugins.cozylibrary.user.PlayerUser;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the vault api dependency.
 */
public class VaultAPIDependency {

    private static Economy economy;

    /**
     * Used to set up the vault api dependency.
     */
    public static void setup() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            ConsoleManager.warn("The plugin named Vault is not installed. Some features may be disabled as a result.");
            return;
        }

        VaultAPIDependency.economy = CozyPlugin
                .getPlugin()
                .getServer()
                .getServicesManager()
                .getRegistration(Economy.class)
                .getProvider();
    }

    /**
     * Used to check if the vault dependency is enabled.
     *
     * @return True if it is enabled.
     */
    public static boolean isEnabled() {
        return Bukkit.getPluginManager().getPlugin("Vault") != null;
    }

    /**
     * Used to get the economy manager.
     *
     * @return The economy manager.
     */
    public static Economy get() {
        return VaultAPIDependency.economy;
    }

    /**
     * Used to give a player money.
     *
     * @param user   The instance of the user.
     * @param amount The amount to give.
     * @return If the deposit was a success.
     */
    public static boolean giveMoney(@NotNull PlayerUser user, double amount) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(user.getUuid());
        EconomyResponse response = VaultAPIDependency.get().depositPlayer(offlinePlayer, amount);
        return response.transactionSuccess();
    }

    /**
     * Used to remove money from a player.
     *
     * @param user   The instance of the user.
     * @param amount The amount to remove.
     * @return If the withdrawal was a success.
     */
    public static boolean removeMoney(@NotNull PlayerUser user, double amount) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(user.getUuid());
        EconomyResponse response = VaultAPIDependency.get().withdrawPlayer(offlinePlayer, amount);
        return response.transactionSuccess();
    }
}
