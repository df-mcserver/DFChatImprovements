package uk.co.nikodem.dFChatImprovements.PluginMessaging.Messages;

import com.google.common.io.ByteArrayDataInput;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.co.nikodem.dFChatImprovements.PluginMessaging.DFPluginMessageHandler;

public class DiscordLoggingBridged implements DFPluginMessageHandler {
    @Override
    public void run(@NotNull String channel, @NotNull Player player, ByteArrayDataInput in) {
        System.out.println("Received response DiscordLoggingBridged!!!");
    }
}
