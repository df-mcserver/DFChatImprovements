package uk.co.nikodem.dFChatImprovements.PluginMessaging;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import uk.co.nikodem.dFChatImprovements.DFChatImprovements;

import java.util.ArrayList;
import java.util.List;

public class ProxyAbstractions {
    public static final String CUSTOM_PROXY_CHANNEL = "df:proxy";
    public static boolean hasAccess = false;
    public static boolean hasRequested = false;

    public static List<byte[]> queue = new ArrayList<>();

    public static void sendDeathMessage(Player plr, String deathMessage) {
        if (!hasAccess) return;
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("DiscordLogStandardMessage");
        out.writeUTF(deathMessage);
        sendRequest(plr, out.toByteArray());    }

    public static void sendAdvancementMessage(Player plr, String advancementMessage) {
        if (!hasAccess) return;
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("DiscordLogEmbedMessage");
        out.writeUTF("00ff00");
        out.writeUTF(advancementMessage);
        sendRequest(plr, out.toByteArray());    }

    public static void sendPlayerMessage(Player plr, String message) {
        if (!hasAccess) return;
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("DiscordLogStandardMessage");
        out.writeUTF(message);
        sendRequest(plr, out.toByteArray());
    }

    public static void requestBridgeAccess(Player plr) {
        if (hasAccess || hasRequested) return;
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("DiscordLoggingBridged");
        sendRequest(plr, out.toByteArray());
        hasRequested = true;
    }

    public static void sendRequest(Player plr, byte[] data) {
        DFChatImprovements plugin = DFChatImprovements.getPlugin(DFChatImprovements.class);
        plr.sendPluginMessage(plugin, CUSTOM_PROXY_CHANNEL, data);
        hasRequested = true;
    }

    public static void setupChannels() {
        DFChatImprovements plugin = DFChatImprovements.getPlugin(DFChatImprovements.class);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, CUSTOM_PROXY_CHANNEL);
    }
}
