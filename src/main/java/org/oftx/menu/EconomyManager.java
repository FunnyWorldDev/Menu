package org.oftx.menu;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class EconomyManager {

    private static Economy economy = null;

    public static void setEconomy(@NotNull Economy econ) {
        economy = econ;
    }

    public static double getMoney(Player player) {
        return economy.getBalance(player);
    }

    public static boolean setMoney(String uuid, double amount) {
        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
        if (player == null) {
            return false;
        }
        return economy.withdrawPlayer(player, economy.getBalance(player)).transactionSuccess() && economy.depositPlayer(player, amount).transactionSuccess();
    }
}
