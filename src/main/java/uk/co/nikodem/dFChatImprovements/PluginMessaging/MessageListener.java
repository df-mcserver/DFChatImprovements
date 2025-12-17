package uk.co.nikodem.dFChatImprovements.PluginMessaging;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;
import uk.co.nikodem.dFChatImprovements.DFChatImprovements;
import uk.co.nikodem.dFChatImprovements.PluginMessaging.Messages.DiscordLoggingBridged;
import uk.co.nikodem.dFChatImprovements.Utils.StringHelper;

import java.util.HashMap;

import static uk.co.nikodem.dFChatImprovements.PluginMessaging.ProxyAbstractions.CUSTOM_PROXY_CHANNEL;

public class MessageListener implements PluginMessageListener {
    public static final HashMap<String, DFPluginMessageHandler> messageHandlers = new HashMap<>();

    public void initialiseMessageHandlers() {
        messageHandlers.put("DiscordLoggingBridged", new DiscordLoggingBridged());

        DFChatImprovements plugin = DFChatImprovements.getPlugin(DFChatImprovements.class);
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, CUSTOM_PROXY_CHANNEL, this);
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte @NotNull [] message) {
        if (!channel.equals(CUSTOM_PROXY_CHANNEL)) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String command = StringHelper.SanitiseString(in.readUTF().split(" ")[0]);

        DFPluginMessageHandler handler = messageHandlers.get(command);
        if (handler != null) handler.run(channel, player, in);
    }
}