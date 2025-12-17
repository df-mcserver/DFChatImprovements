package uk.co.nikodem.dFChatImprovements;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.nikodem.dFChatImprovements.PluginMessaging.ProxyAbstractions;

public final class DFChatImprovements extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void OnDeath(PlayerDeathEvent e) {
        Player plr = e.getEntity();
        ProxyAbstractions.sendDeathMessage(plr, e.getDeathMessage());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void OnChat(AsyncPlayerChatEvent e) {
        // chat modifications

        if (e.isCancelled()) return;

        String msg = e.getMessage();
        for (String word : msg.split("\\s+")){
            // pinging
            if (word.startsWith("@")) {
                String name = word.substring(1);
                for (Player mentioned : Bukkit.getOnlinePlayers()) {
                    if (mentioned.getDisplayName().equals(name)) {
                        mentioned.playSound(mentioned.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
                        msg = msg.replace(word, ChatColor.translateAlternateColorCodes('&', "&3"+word+"&r"));
                    }
                }

                // emoji
            } else if (word.startsWith(":") && word.endsWith(":")) {
                // emoji
                String emojiName = word.substring(1, word.length()-1).toLowerCase();

                String glyphChar = EmojiMappings.mappings.get(emojiName);
                if (glyphChar != null) msg = msg.replace(word, glyphChar);
            }
        }

        // make \: replace with : so you can say things like :skull: without it turning into an emoji
        msg = msg.replace("\\:", ":");
        // make \/ replace with / so you can type out commands without running them
        msg = msg.replace("\\/", "/");

        e.setMessage(msg);
    }
}
