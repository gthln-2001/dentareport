package de.dentareport.imports.dampsoft.convert;

import com.google.common.collect.ImmutableMap;
import de.dentareport.utils.Toothnumbers;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.dbf.DbfRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// TODO: Test
// TODO: Rename fields? Check what is the proper name.
// TODO: TEST?
public class EvidencePaConvert {

    public static List<DbCell> convert(DbfRow paEvidence) {
        List<DbCell> ret = new ArrayList<>();
        for (String toothnumber : Toothnumbers.ALL) {
            String evidence = paEvidence.value("ZA" + toothnumber);
            ret.add(new DbCell("pa_wert_" + toothnumber, paWert(evidence)));
            ret.add(new DbCell("beweglichkeit_" + toothnumber, beweglichkeit(evidence)));
            ret.add(new DbCell("furkation_" + toothnumber, furkation(evidence)));
            ret.add(new DbCell("vitality_" + toothnumber, vitality(evidence)));
            ret.add(new DbCell("pa_therapie_" + toothnumber, paTherapie(evidence)));
            ret.add(new DbCell("rezession_" + toothnumber, rezession(evidence)));
            ret.add(new DbCell("befund_" + toothnumber, befund(evidence)));
        }
        return ret;
    }

    private static String paWert(String evidence) {
        return evidence.substring(0, 6);
    }

    private static String befund(String evidence) {
        return evidence.substring(15, 17).trim();
    }

    private static String rezession(String evidence) {
        return evidence.substring(12, 14).trim();
    }

    private static String paTherapie(String evidence) {
        return evidence.substring(11, 12).trim();
    }

    private static String vitality(String evidence) {
        return evidence.substring(9, 10).trim();
    }

    // TODO: Replace map with constant
    private static String furkation(String evidence) {
        String paChar = evidence.substring(7, 8).trim();
        Map<String, String> valueMap = ImmutableMap.of(
                "A", "",
                "B", "1",
                "C", "2",
                "D", "3"
        );
        return valueMap.getOrDefault(paChar, paChar);
    }

    // TODO: Replace map with constant
    private static String beweglichkeit(String evidence) {
        String paChar = evidence.substring(6, 7).trim();
        Map<String, String> valueMap = ImmutableMap.of(
                "A", "0",
                "B", "1",
                "C", "2",
                "D", "3",
                "E", ""
        );
        return valueMap.getOrDefault(paChar, paChar);
    }
}
