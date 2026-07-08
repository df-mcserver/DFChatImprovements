package uk.co.nikodem.dFChatImprovements;

import io.papermc.paper.advancement.AdvancementDisplay;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.nikodem.dFChatImprovements.Commands.FlexCommand;
import uk.co.nikodem.dFChatImprovements.PluginMessaging.MessageListener;
import uk.co.nikodem.dFChatImprovements.PluginMessaging.ProxyAbstractions;
import uk.co.nikodem.dFChatImprovements.UAAPi.CustomAdvancementListener;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
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

        Objects.requireNonNull(Bukkit.getPluginCommand("flex")).setExecutor(new FlexCommand());

        if (Bukkit.getPluginManager().isPluginEnabled("UltimateAdvancementAPI")) {
            Bukkit.getPluginManager().registerEvents(new CustomAdvancementListener(), this);
        }
    }

    public Component getTimeHoverMessage() {
        LocalTime currentTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
        return Component.text(currentTime.toString());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void OnPlayerEstablishChannels(PlayerChannelEvent e) {
        // messages can only be sent once a player is present
        if (e.getChannel().equals(CUSTOM_PROXY_CHANNEL)) ProxyAbstractions.requestBridgeAccess(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void OnPlayerJoin(PlayerJoinEvent e) {
        Component joinMessage = e.joinMessage();
        if (joinMessage == null) return;

        e.joinMessage(joinMessage.hoverEvent(HoverEvent.showText(this::getTimeHoverMessage)));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void OnPlayerLeave(PlayerQuitEvent e) {
        Component quitMessage = e.quitMessage();
        if (quitMessage == null) return;

        e.quitMessage(quitMessage.hoverEvent(HoverEvent.showText(this::getTimeHoverMessage)));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void OnDeath(PlayerDeathEvent e) {
        Component deathMessage = e.deathMessage();
        if (deathMessage == null) return;

        e.deathMessage(deathMessage.hoverEvent(HoverEvent.showText(this::getTimeHoverMessage)));

        Player plr = e.getEntity();
        ProxyAbstractions.sendDeathMessage(plr, PlainTextComponentSerializer.plainText().serialize(deathMessage));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void OnAdvancementMessage(PlayerAdvancementDoneEvent e) {
        if (e.message() == null) return;
        Player plr = e.getPlayer();
        AdvancementDisplay display = e.getAdvancement().getDisplay();
        String description = display == null ? "" : "\n"+PlainTextComponentSerializer.plainText().serialize(display.description());
        boolean isChallenge = display != null && display.frame() == AdvancementDisplay.Frame.CHALLENGE;
        ProxyAbstractions.sendAdvancementMessage(plr, isChallenge, PlainTextComponentSerializer.plainText().serialize(e.message())+description);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void OnChat(AsyncChatEvent e) {
        // chat modifications
        // TODO: fix reliability

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

        e.message(mm.deserialize(msg).hoverEvent(HoverEvent.showText(this::getTimeHoverMessage)));

        if (ProxyAbstractions.hasAccess) {
            String discordReadyMessage = PlainTextComponentSerializer.plainText().serialize(mm.deserialize(msg));

            Matcher m = charPattern.matcher(discordReadyMessage);
            String replacement = m.replaceAll((result) -> {
                String emojiWord = result.group();
                String finalEmoji = EmojiMappings.invertedMappings.getOrDefault(emojiWord, emojiWord);
                return EmojiMappings.customDiscordEmojiMappings.getOrDefault(finalEmoji, finalEmoji);
            });

            ProxyAbstractions.sendPlayerMessage(e.getPlayer(), replacement);
        }
    }
}
