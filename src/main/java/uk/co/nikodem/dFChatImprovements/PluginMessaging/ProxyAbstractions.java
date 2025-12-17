package uk.co.nikodem.dFChatImprovements.PluginMessaging;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import uk.co.nikodem.dFChatImprovements.DFChatImprovements;

public class ProxyAbstractions {
    public static final String CUSTOM_PROXY_CHANNEL = "df:proxy";
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
        if (hasAccess) return;
        DFChatImprovements plugin = DFChatImprovements.getPlugin(DFChatImprovements.class);
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("DiscordLoggingBridged");
        plr.sendPluginMessage(plugin, CUSTOM_PROXY_CHANNEL, out.toByteArray());
    }

    public static void setupChannels() {
        DFChatImprovements plugin = DFChatImprovements.getPlugin(DFChatImprovements.class);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, CUSTOM_PROXY_CHANNEL);
    }
}
