package uk.co.nikodem.dFChatImprovements.Utils;

public class StringHelper {
    public static String SanitiseString(String string) {
        return string.replaceAll("\\p{C}", "");
    }
}