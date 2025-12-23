package de.dentareport.imports.dampsoft.convert;

import de.dentareport.imports.dampsoft.dampsoft_files.PatkuerzDbf;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TokensConvert {

    public static String convert(String value) {
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < value.length(); i++) {
            if (Objects.equals(value.substring(i, i + 1), "J")) {
                ret.add(PatkuerzDbf.token(i));
            }
        }
        return String.join(",", ret);
    }
}
