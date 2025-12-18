package uk.co.nikodem.dFChatImprovements.PluginMessaging;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface DFPluginMessageHandler {
    void run(@NotNull String channel, @NotNull Player player, ByteArrayDataInput in, byte @NotNull [] message);

    default byte[] createMessage(String... messages) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        for (String msg : messages) {
            out.writeUTF(msg+" ");
        }

        return out.toByteArray();
    }

    default String convertBoolToString(boolean bool) {
        return bool ? "true" : "false";
    }
}