package uk.co.nikodem.dFChatImprovements;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.nikodem.dFChatImprovements.PluginMessaging.MessageListener;
import uk.co.nikodem.dFChatImprovements.PluginMessaging.ProxyAbstractions;

public final class DFChatImprovements extends JavaPlugin implements Listener {

    public static MessageListener messageListener;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        ProxyAbstractions.setupChannels();

        messageListener = new MessageListener();
        messageListener.initialiseMessageHandlers();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void OnPlayerJoin(PlayerChannelEvent e) {
        // messages can only be sent once a player is present
        ProxyAbstractions.requestBridgeAccess(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void OnDeath(PlayerDeathEvent e) {
        if (e.deathMessage() == null) return;
        Player plr = e.getEntity();
        ProxyAbstractions.sendDeathMessage(plr, PlainTextComponentSerializer.plainText().serialize(e.deathMessage()));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void OnAdvancementMessage(PlayerAdvancementDoneEvent e) {
        if (e.message() == null) return;
        ProxyAbstractions.sendAdvancementMessage(e.getPlayer(), PlainTextComponentSerializer.plainText().serialize(e.message()));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void OnChat(AsyncChatEvent e) {
        // chat modifications

        if (e.isCancelled()) return;

        MiniMessage mm = MiniMessage.miniMessage();
        String msg = mm.serialize(e.message());
        for (String word : msg.split("\\s+")){
            // pinging
            if (word.startsWith("@")) {
                String name = word.substring(1);
                for (Player mentioned : Bukkit.getOnlinePlayers()) {
                    if (mentioned.getName().equals(name)) {
                        mentioned.playSound(mentioned.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
                        msg = msg.replace(word, "<aqua>"+word+"</aqua>");
                    }
                }

                // emoji
            } else if (word.startsWith(":") && word.endsWith(":")) {
                // emoji
                String emojiName = word.substring(1, word.length()-1).toLowerCase();

                String glyphChar = EmojiMappings.mappings.get(EmojiMappings.alternateWords.getOrDefault(emojiName, emojiName));
                if (glyphChar != null) msg = msg.replace(word, glyphChar);
            }
        }

        // make \: replace with : so you can say things like :skull: without it turning into an emoji
        msg = msg.replace("\\:", ":");
        // make \/ replace with / so you can type out commands without running them
        msg = msg.replace("\\/", "/");

        e.message(mm.deserialize(msg));

        if (ProxyAbstractions.hasAccess) {
            String discordReadyMessage = PlainTextComponentSerializer.plainText().serialize(mm.deserialize(msg));

            for (String word : discordReadyMessage.split("\\s+")) {
                if (EmojiMappings.invertedMappings.containsKey(word)) {
                    discordReadyMessage = discordReadyMessage.replace(word, ":"+EmojiMappings.invertedMappings.get(word)+":");
                }
            }

            ProxyAbstractions.sendPlayerMessage(e.getPlayer(), discordReadyMessage);
        }
    }
}
