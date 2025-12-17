package uk.co.nikodem.dFChatImprovements.PluginMessaging;

import org.bukkit.entity.Player;

public class ProxyAbstractions {
    public static boolean hasAccess = false;

    public static void sendDeathMessage(Player plr, String deathMessage) {
        if (!hasAccess) return;
    }

    public static void sendAdvancementMessage(Player plr, String advancementMessage) {
        if (!hasAccess) return;
    }

    public static void sendPlayerMessage(Player plr, String message) {
        if (!hasAccess) return;
    }

    public static void requestBridgeAccess(Player plr) {

    }

    public static void setupChannels() {

    }
}
