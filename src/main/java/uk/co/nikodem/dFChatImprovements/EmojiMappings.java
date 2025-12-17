package uk.co.nikodem.dFChatImprovements;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EmojiMappings {
    public static Map<String, String> alternateWords = Map.ofEntries(
            Map.entry("money_mouth_face", "money_mouth"),
            Map.entry("folded_hands", "pray"),
            Map.entry("3_hearts", "smiling_face_with_3_hearts"),
            Map.entry("cold", "cold_face"),
            Map.entry("speaking_head_in_silhouette", "speaking_head"),
            Map.entry("flame", "fire"),
            Map.entry("moyai", "moai"),
            Map.entry("cold_cry", "joobi"),
            Map.entry("waving_hand", "wave"),
            Map.entry("pensive_face", "pensive"),
            Map.entry("neutral", "neutral_face"),
            Map.entry("slightly_smiling_face", "slight_smile"),
            Map.entry("vomiting", "face_vomiting"),
            Map.entry("pleading", "pleading_face"),
            Map.entry("angry_face", "angry"),
            Map.entry("rage_face", "rage"),
            Map.entry("pouting_face", "rage"),
            Map.entry("nerd_face", "nerd"),
            Map.entry("japanese_symbol_for_beginner", "beginner")
    );

    public static Map<String, String> mappings = Map.<String, String>ofEntries(
            Map.entry("skull", "\uE900"),
            Map.entry("cold_sunglasses", "\uE901"),
            Map.entry("sob", "\uE902"),
            Map.entry("chair", "\uE903"),
            Map.entry("sunglasses", "\uE904"),
            Map.entry("smile", "\uE905"),
            Map.entry("money_mouth", "\uE906"),
            Map.entry("pray", "\uE907"),
            Map.entry("smiling_face_with_3_hearts", "\uE908"),
            Map.entry("smirk_cat", "\uE909"),
            Map.entry("cold_face", "\uE90A"),
            Map.entry("speaking_head", "\uE90B"),
            Map.entry("fire", "\uE90C"),
            Map.entry("fish", "\uE90D"),
            Map.entry("moai", "\uE90E"),
            Map.entry("thumbs_up", "\uE90F"),
            Map.entry("salute", "\uE910"),
            Map.entry("shrug", "\uE911"),
            Map.entry("wompwomp", "\uE912"),
            Map.entry("joobi", "\uE913"),
            Map.entry("wave", "\uE914"),
            Map.entry("pensive", "\uE915"),
            Map.entry("scream", "\uE916"),
            Map.entry("smiling_imp", "\uE917"),
            Map.entry("neutral_face", "\uE918"),
            Map.entry("slight_smile",  "\uE919"),
            Map.entry("flushed", "\uE91A"),
            Map.entry("astonished", "\uE91B"),
            Map.entry("face_vomiting", "\uE91C"),
            Map.entry("scream_cat", "\uE91D"),
            Map.entry("pleading", "\uE91E"),
            Map.entry("angry_face", "\uE91F"),
            Map.entry("rage", "\uE920"),
            Map.entry("nerd", "\uE921"),
            Map.entry("beginner", "\uE922")
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
