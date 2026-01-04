package uk.co.nikodem.dFChatImprovements.Commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import uk.co.nikodem.dFChatImprovements.PluginMessaging.ProxyAbstractions;

import java.awt.*;

public class FlexCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player plr) {
            PlayerInventory inventory = plr.getInventory();
            ItemStack item = inventory.getItemInMainHand();

            if (item.getType() == Material.AIR) {
                commandSender.sendMessage(MiniMessage.miniMessage().deserialize("<red>You aren't holding anything!"));
                return true;
            }

            ItemMeta meta = item.getItemMeta();

            int amnt = item.getAmount();
            Component message = item.displayName().append(Component.text(amnt > 1 ? " x"+amnt : ""));

            Component mcMessage = Component.text("<")
                    .append(plr.displayName())
                    .append(Component.text("> "))
                    .append(message);

            for (Player online : Bukkit.getOnlinePlayers()) {
                online.sendMessage(mcMessage);
            }

            TextColor defaultColour = item.displayName().color();
            String discordHex;
            if (meta == null) discordHex = defaultColour.asHexString();
            else {
                if (meta.hasDisplayName()) {
                    discordHex = meta.displayName().color() == null ? defaultColour.asHexString() : meta.displayName().color().asHexString();
                } else discordHex = defaultColour.asHexString();
            }
            String discordMsg = PlainTextComponentSerializer.plainText().serialize(message);
            ProxyAbstractions.sendFlexMessage(plr, discordHex, discordMsg);
        } else {
            commandSender.sendMessage("You are not a player, and thus cannot hold any items!");
        }
        return true;
    }
}
