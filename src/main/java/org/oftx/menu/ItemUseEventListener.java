package org.oftx.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;
import static org.oftx.menu.Menu.configuredItemType;
import static org.oftx.menu.Menu.useItemOpenMenu;
import static org.oftx.menu.PlayerFormHandler.PlayerFormHandle;

public class ItemUseEventListener implements Listener {

    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        if (useItemOpenMenu) {
            if (event.getAction() == RIGHT_CLICK_BLOCK) {
                ItemStack item = event.getItem();
                if (item != null && item.getType().toString().equalsIgnoreCase(configuredItemType)) {
                    Player player = event.getPlayer();

                    if (!PlayerUtils.isBedrockPlayer(player)) return;

                    PlayerFormHandle(player, null);
                }
            }
        }
    }
}
