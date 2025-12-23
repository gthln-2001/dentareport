package de.dentareport.imports.dampsoft.convert;

public class DateConvert {

    public static String convert(String value) {
        return String.format("%s-%s-%s",
                value.substring(0, 4),
                value.substring(4, 6),
                value.substring(6, 8));
    }
}
