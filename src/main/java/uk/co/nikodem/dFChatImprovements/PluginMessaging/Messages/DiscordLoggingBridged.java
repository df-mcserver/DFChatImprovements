package uk.co.nikodem.dFChatImprovements.PluginMessaging.Messages;

import com.google.common.io.ByteArrayDataInput;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.co.nikodem.dFChatImprovements.PluginMessaging.DFPluginMessageHandler;
import uk.co.nikodem.dFChatImprovements.PluginMessaging.ProxyAbstractions;
import uk.co.nikodem.dFChatImprovements.Utils.StringHelper;

public class DiscordLoggingBridged implements DFPluginMessageHandler {
    @Override
    public void run(@NotNull String channel, @NotNull Player player, ByteArrayDataInput in) {
        String val = StringHelper.SanitiseString(in.readUTF().toLowerCase().split(" ")[0]);

        if (val.equals("true")) ProxyAbstractions.hasAccess = true;
        else if (val.equals("false")) ProxyAbstractions.hasAccess = false;
    }
}
