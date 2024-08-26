package org.oftx.menu;

import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;

public class PlayerUtils {

    public static boolean isBedrockPlayer(Player player) {
        return FloodgateApi.getInstance().isFloodgatePlayer(player.getUniqueId());
    }
}
