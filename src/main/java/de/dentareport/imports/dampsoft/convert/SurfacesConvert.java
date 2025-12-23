package de.dentareport.imports.dampsoft.convert;

public class SurfacesConvert {

    public static String convert(String value) {
        return InfoConvert.convertSurfaceString(value.trim().toLowerCase());
    }
}
