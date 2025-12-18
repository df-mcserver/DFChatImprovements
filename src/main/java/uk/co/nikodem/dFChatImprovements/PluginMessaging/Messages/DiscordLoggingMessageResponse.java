package uk.co.nikodem.dFChatImprovements.PluginMessaging.Messages;

import com.google.common.io.ByteArrayDataInput;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.co.nikodem.dFChatImprovements.PluginMessaging.DFPluginMessageHandler;
import uk.co.nikodem.dFChatImprovements.PluginMessaging.ProxyAbstractions;

public class DiscordLoggingMessageResponse implements DFPluginMessageHandler {
    @Override
    public void run(@NotNull String channel, @NotNull Player player, ByteArrayDataInput in, byte @NotNull [] message) {
        ProxyAbstractions.hasAccess = false;
        ProxyAbstractions.queue.add(message);

        ProxyAbstractions.hasRequested = false;
        ProxyAbstractions.requestBridgeAccess(player);
    }
}
