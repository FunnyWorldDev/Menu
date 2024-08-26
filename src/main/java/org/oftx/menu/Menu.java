package org.oftx.menu;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Menu extends JavaPlugin {

    public static FormInfo mainFormInfo;
    public static String configuredItemType;
    public static Boolean useItemOpenMenu;

    @Override
    public void onEnable() {
        DependenciesChecker.getAndSetDependenciesStatus(this);

        if (!DependenciesChecker.isFloodgateLoaded()) {
            getLogger().severe("Geyser或Floodgate未找到！插件将禁用...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (!DependenciesChecker.isPlaceholderApiLoaded()) {
            getLogger().info("PlaceHolderAPI插件未找到");
        }

        if (!DependenciesChecker.isEconomyPluginLoaded()) {
            getLogger().info("Vault插件未找到或经济未设置");
        }

        if (!DependenciesChecker.isResidenceLoaded()) {
            getLogger().info("Residence插件未找到");
        }

        ConfigUtils.setMenuConfigFile(this);
        ConfigUtils.initConfig();
        mainFormInfo = ConfigUtils.readConfig();

        ItemUseEventListener itemUseEventListener = new ItemUseEventListener();
        getServer().getPluginManager().registerEvents(itemUseEventListener, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return CommandHandler.handleCommand(this, sender, command, label, args);
    }
}
