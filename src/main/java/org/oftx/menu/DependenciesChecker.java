package org.oftx.menu;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class DependenciesChecker {

    private static boolean ECONOMY_PLUGIN_LOADED = false;
    private static boolean RESIDENCE_LOADED = false;
    private static boolean CMI_LOADED = false;
    private static boolean FLOODGATE_LOADED = false;
    private static boolean PLACEHOLDERAPI_LOADED = false;

    public static void getAndSetDependenciesStatus(JavaPlugin plugin) {
        FLOODGATE_LOADED = plugin.getServer().getPluginManager().getPlugin("Geyser-Spigot") != null && plugin.getServer().getPluginManager().getPlugin("floodgate") != null;

        RegisteredServiceProvider<Economy> rsp;
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null || (rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class)) == null) {
            ECONOMY_PLUGIN_LOADED = false;
        } else {
            ECONOMY_PLUGIN_LOADED = true;
            EconomyManager.setEconomy(rsp.getProvider());
        }

        RESIDENCE_LOADED = plugin.getServer().getPluginManager().getPlugin("Residence") != null;
        CMI_LOADED = plugin.getServer().getPluginManager().getPlugin("CMI") != null;
        PLACEHOLDERAPI_LOADED = plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    public static boolean isFloodgateLoaded() {
        return FLOODGATE_LOADED;
    }

    public static boolean isEconomyPluginLoaded() {
        return ECONOMY_PLUGIN_LOADED;
    }

    public static boolean isResidenceLoaded() {
        return RESIDENCE_LOADED;
    }

    public static boolean isPlaceholderApiLoaded() {
        return PLACEHOLDERAPI_LOADED;
    }
}
