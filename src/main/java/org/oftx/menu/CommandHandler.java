package org.oftx.menu;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import static org.oftx.menu.PlayerFormHandler.PlayerFormHandle;

public class CommandHandler {

    public static boolean handleCommand(JavaPlugin plugin, @NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("menu")) {
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("This command can only be executed by a player");
                    return true;
                }

                Player player = (Player) sender;

                if (!PlayerUtils.isBedrockPlayer(player)) {
                    sender.sendMessage("仅基岩版玩家可以使用这个命令");
                    return true;
                }

                PlayerFormHandle(player, null);

                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!(sender instanceof Player)) {
                        Menu.mainFormInfo = ConfigUtils.readConfig();
                        sender.sendMessage("Reloaded config");
                    } else {
                        Player player = (Player) sender;
                        if (player.hasPermission("menu.op")) {
                            sender.sendMessage("Reloaded config");
                            player.sendMessage("Reloaded config");
                        }
                    }
                    return true;
                }
                return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
