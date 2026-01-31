package de.dentareport.utils.string;

import de.dentareport.utils.Keys;

import java.util.Arrays;
import java.util.Objects;

// TODO: TEST?
public class StringUtils {

    public static String leftPad(String str,
                                 int desiredLength,
                                 String padWith) {
        if (str.length() >= desiredLength) return str;
        return new StringBuilder(desiredLength)
                .append(str)
                .insert(0, paddingString(str, desiredLength, padWith))
                .toString();
    }

    public static String leftPad(String str,
                                 int desiredLength) {
        return leftPad(str, desiredLength, " ");
    }

    public static String rightPad(String str,
                                  int desiredLength,
                                  String padWith) {
        if (str.length() >= desiredLength) return str;
        return String.format("%s%s",
                str,
                paddingString(str, desiredLength, padWith));
    }

    public static String rightPad(String str,
                                  int desiredLength) {
        return rightPad(str, desiredLength, " ");
    }

    public static boolean isNumeric(String value) {
        return value.matches("[0-9]+");
    }

    public static boolean isNoData(String value) {
        return Objects.equals(value, Keys.NO_DATA);
    }

    private static String paddingString(String str,
                                        int desiredLength,
                                        String padWith) {
        CharSequence[] padding = new CharSequence[desiredLength - str.length()];
        Arrays.fill(padding, padWith);
        return String.join("", padding);
    }
}
