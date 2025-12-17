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

import javax.print.DocFlavor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static uk.co.nikodem.dFChatImprovements.PluginMessaging.ProxyAbstractions.CUSTOM_PROXY_CHANNEL;

public final class DFChatImprovements extends JavaPlugin implements Listener {

    public static Pattern emojiColonPattern = Pattern.compile(":[^:]+:");
    public static Pattern charPattern = Pattern.compile("\\X");
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
        if (e.getChannel().equals(CUSTOM_PROXY_CHANNEL)) ProxyAbstractions.requestBridgeAccess(e.getPlayer());
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
            } else if (word.contains(":")) {
                // emoji

                Matcher m = emojiColonPattern.matcher(word);
                String replacement = m.replaceAll((result) -> {
                    String emojiWord = result.group();
                    return EmojiMappings.mappings.get(EmojiMappings.alternateWords.getOrDefault(emojiWord, emojiWord));
                });

                msg = msg.replace(word, replacement);
            }
        }

        // make \: replace with : so you can say things like :skull: without it turning into an emoji
        msg = msg.replace("\\:", ":");
        // make \/ replace with / so you can type out commands without running them
        msg = msg.replace("\\/", "/");

        e.message(mm.deserialize(msg));

        if (ProxyAbstractions.hasAccess) {
            String discordReadyMessage = PlainTextComponentSerializer.plainText().serialize(mm.deserialize(msg));

            Matcher m = charPattern.matcher(discordReadyMessage);
            String replacement = m.replaceAll((result) -> {
                String emojiWord = result.group();
                return EmojiMappings.invertedMappings.getOrDefault(emojiWord, emojiWord);
            });

            ProxyAbstractions.sendPlayerMessage(e.getPlayer(), replacement);
        }
    }
}
