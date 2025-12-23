package de.dentareport.imports.dampsoft.convert;

import de.dentareport.utils.Keys;

import java.util.Objects;

public class GenderConvert {

    public static String convert(String gender,
                                 String salutation) {
        if (genderHasValue(gender)) {
            if (Objects.equals(gender.trim().toLowerCase(), "w")) {
                return Keys.FEMALE;
            }
            if (Objects.equals(gender.trim().toLowerCase(), "m")) {
                return Keys.MALE;
            }
            return gender.trim().toLowerCase();
        }
        if (Objects.equals(salutation, "F")) {
            return Keys.FEMALE;
        }
        if (Objects.equals(salutation, "H")) {
            return Keys.MALE;
        }
        return "";
    }

    private static boolean genderHasValue(String gender) {
        return !Objects.equals(gender, "");
    }
}
