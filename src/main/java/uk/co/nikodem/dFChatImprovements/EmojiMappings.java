package uk.co.nikodem.dFChatImprovements;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class EmojiMappings {
    public static Map<String, String> alternateWords = Map.ofEntries(
            entry(":money_mouth_face:", ":money_mouth:"),
            entry(":folded_hands:", ":pray:"),
            entry(":3_hearts:", ":smiling_face_with_3_hearts:"),
            entry(":cold:", ":cold_face:"),
            entry(":speaking_head_in_silhouette:", ":speaking_head:"),
            entry(":flame:", ":fire:"),
            entry(":moyai:", ":moai:"),
            entry(":cold_cry:", ":joobi:"),
            entry(":waving_hand:", ":wave:"),
            entry(":pensive_face:", ":pensive:"),
            entry(":neutral:", ":neutral_face:"),
            entry(":slightly_smiling_face:", ":slight_smile:"),
            entry(":vomiting:", ":face_vomiting:"),
            entry(":pleading:", ":pleading_face:"),
            entry(":angry_face:", ":angry:"),
            entry(":rage_face:", ":rage:"),
            entry(":pouting_face:", ":rage:"),
            entry(":nerd_face:", ":nerd:"),
            entry(":japanese_symbol_for_beginner:", ":beginner:"),
            entry(":trans_flag:", ":trans:"),
            entry(":full_battery:", ":battery:"),
            entry(":thumbsup:", ":thumbs_up:"),
            entry(":thumbsdown:", ":thumbs_down:"),
            entry(":face_with_bags_under_eyes:", ":baggy_eyes:"),
            entry(":x:", ":wrong:"),
            entry(":tick:", ":check:"),
            entry(":white_check_mark:", ":check:"),
            entry(":facyoy:", ":fuck_you:"),
            entry(":steamhappy:", ":steam_happy:")
    );

    public static Map<String, String> mappings = Map.<String, String>ofEntries(
            entry(":skull:", "\uE900"),
            entry(":cold_sunglasses:", "\uE901"),
            entry(":sob:", "\uE902"),
            entry(":chair:", "\uE903"),
            entry(":sunglasses:", "\uE904"),
            entry(":smile:", "\uE905"),
            entry(":money_mouth:", "\uE906"),
            entry(":pray:", "\uE907"),
            entry(":smiling_face_with_3_hearts:", "\uE908"),
            entry(":smirk_cat:", "\uE909"),
            entry(":cold_face:", "\uE90A"),
            entry(":speaking_head:", "\uE90B"),
            entry(":fire:", "\uE90C"),
            entry(":fish:", "\uE90D"),
            entry(":moai:", "\uE90E"),
            entry(":thumbs:", "\uE90F"),
            entry(":salute:", "\uE910"),
            entry(":shrug:", "\uE911"),
            entry(":wompwomp:", "\uE912"),
            entry(":joobi:", "\uE913"),
            entry(":wave:", "\uE914"),
            entry(":pensive:", "\uE915"),
            entry(":scream:", "\uE916"),
            entry(":smiling_imp:", "\uE917"),
            entry(":neutral_face:", "\uE918"),
            entry(":slight_smile:",  "\uE919"),
            entry(":flushed:", "\uE91A"),
            entry(":astonished:", "\uE91B"),
            entry(":face_vomiting:", "\uE91C"),
            entry(":scream_cat:", "\uE91D"),
            entry(":pleading:", "\uE91E"),
            entry(":angry_face:", "\uE91F"),
            entry(":rage:", "\uE920"),
            entry(":nerd:", "\uE921"),
            entry(":beginner:", "\uE922"),
            entry(":wilted_rose:", "\uE923"),
            entry(":tanabana_tree:", "\uE924"),
            entry(":heart:", "\uE925"),
            entry(":broken_heart:", "\uE926"),
            entry(":trans:", "\uE927"),
            entry(":battery:", "\uE928"),
            entry(":low_battery:", "\uE929"),
            entry(":mending_heart:", "\uE92A"),
            entry(":rose:", "\uE92B"),
            entry(":black_heart:", "\uE92C"),
            entry(":thinking:", "\uE92D"),
            entry(":confused:", "\uE92E"),
            entry(":thumbs_up:", "\uE92F"),
            entry(":thumbs_down:", "\uE930"),
            entry(":tada:", "\uE931"),
            entry(":heart_hands:", "\uE932"),
            entry(":baggy_eyes:", "\uE933"),
            entry(":wrong:", "\uE934"),
            entry(":check:", "\uE935"),
            entry(":partying_face:", "\uE936"),
            entry(":false:", "\uE937"),
            entry(":true:", "\uE938"),
            entry(":thumbs_up_cat:", "\uE939"),
            entry(":fuck_you:", "\uE93A"),
            entry(":silly:", "\uE93B"),
            entry(":mrrp:", "\uE93C"),
            entry(":cat_sit:", "\uE93D"),
            entry(":steam_happy:", "\uE93E")
    );

    public static Map<String, String> customDiscordEmojiMappings = Map.ofEntries(
            entry(":joobi:", "<:joobi:1323391120134377503>"),
            entry(":cold_sunglasses:", "<:cold_sunglasses:1320410427863334965>"),
            entry(":wompwomp:", "<:wompwomp:1320855389251834046>"),
            entry(":thumbs:", "<:thumbs:1320855387884355707>"),
            entry(":shrug:", "<:shrug:1320855386710081546>"),
            entry(":salute:", "<:salute:1320855385476960266>"),
            entry(":false:", "<:false:1563275403659968584>"),
            entry(":true:", "<:true:1563275776764157982>"),
            entry(":thumbs_up_cat:", "<:thumbs_up:1563275449579077633>"),
            entry(":fuck_you:", "<:fucyoy:1563275743897583706>"),
            entry(":silly:", "<:silly:1563275432076386324>"),
            entry(":mrrp:", "<:mrrp:1563275420567076924>"),
            entry(":cat_sit:", "<:cat_sit:1563275377659355366>"),
            entry(":steam_happy:", "<:steamhappy:1563276184710553741>"),

            entry(":trans:", ":transgender_flag:"),
            entry(":wrong:", ":x:"),
            entry(":check:", ":white_check_mark:"),
            entry(":baggy_eyes:", ":face_with_bags_under_eyes:")
    );

    public static final Map<String, String> invertedMappings;
    static {
        Map<String, String> newMappings = new HashMap<>();
        for (Map.Entry<String, String> entry : mappings.entrySet()) {
            String word = entry.getKey();
            String emoji = entry.getValue();

            newMappings.put(emoji, word);
        }

        invertedMappings = Collections.unmodifiableMap(newMappings);
    }
}
