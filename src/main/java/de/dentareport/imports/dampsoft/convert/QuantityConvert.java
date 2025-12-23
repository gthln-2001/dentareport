package de.dentareport.imports.dampsoft.convert;

public class QuantityConvert {

    public static String convert(String value) {
        if (value.matches("[0-9]+.[0]+")) {
            return value.replaceFirst(".[0]+", "");
        }
        return value;
    }
}
