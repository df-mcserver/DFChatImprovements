package uk.co.nikodem.dFChatImprovements.UAAPi;

import com.fren_gor.ultimateAdvancementAPI.advancement.RootAdvancement;
import com.fren_gor.ultimateAdvancementAPI.events.advancement.AdvancementProgressionUpdateEvent;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import uk.co.nikodem.dFChatImprovements.PluginMessaging.ProxyAbstractions;

import java.util.UUID;

public class CustomAdvancementListener implements Listener {

    // very hacky way of detecting UAAPI advancements (they don't work with vanilla advancements)

    @EventHandler
    public void onAdvancementProgression(AdvancementProgressionUpdateEvent event) {
        if (event.getOldProgression() == event.getNewProgression()) return;
        if (event.getAdvancement() instanceof RootAdvancement) return;
        if (event.getNewProgression() >= event.getAdvancement().getMaxProgression()) {
            // completed, send message
            // (i dont think that i register any team advancements, so i think im safe to just do a search for any candidate lol)
            UUID uuid = event.getTeamProgression().getAMember();
            if (uuid == null) return;
            Player plr = Bukkit.getPlayer(uuid);
            if (plr == null) return;

            StringBuilder msg = new StringBuilder();
            BaseComponent[] components = event.getAdvancement().getAnnounceMessage(plr);
            for (BaseComponent component : components) {
                // get announcement msg in plain text, remove the random §a
                msg.append(component.toPlainText().replaceAll("§a", ""));
            }

            ProxyAbstractions.sendAdvancementMessage(plr, msg.toString());
        }
    }
}
