package de.dentareport.imports.dampsoft.convert;

import de.dentareport.utils.string.StringUtils;

// TODO: TEST?
public class HkpIndexConvert {

    public static String convert(String value) {
        return StringUtils.isNumeric(value.trim())
                ? value.trim()
                : "";
    }
}
