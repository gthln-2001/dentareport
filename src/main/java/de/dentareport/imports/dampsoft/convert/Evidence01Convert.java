package de.dentareport.imports.dampsoft.convert;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.dentareport.utils.Keys;
import de.dentareport.utils.Log;
import de.dentareport.utils.Toothnumbers;
import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.dbf.DbfRow;
import de.dentareport.utils.string.ToothStringUtils;

import java.util.*;

// TODO: TEST?
public class Evidence01Convert {

    public static List<DbCell> convert(DbfRow evidence) {
        List<DbCell> ret = new ArrayList<>();
        for (String toothnumber : Toothnumbers.ALL) {
            ret.addAll(evidence01ForTooth(evidence, toothnumber));
        }
        return ret;
    }

    private static List<DbCell> evidence01ForTooth(DbfRow evidence,
                                                   String toothnumber) {
        List<DbCell> ret = new ArrayList<>();
        ret.add(new DbCell("milktooth_" + toothnumber, milktoothFrom01Evidence(evidence, toothnumber)));
        ret.add(new DbCell("status_" + toothnumber, statusFrom01Evidence(evidence, toothnumber)));
        ret.add(new DbCell("sealing_" + toothnumber, sealingFrom01Evidence(evidence, toothnumber)));
        ret.add(new DbCell("vitality_" + toothnumber, vitalityFrom01Evidence(evidence, toothnumber)));
        ret.add(new DbCell("insufficient_crown_" + toothnumber, insufficientCrownFrom01Evidence(evidence, toothnumber)));
        // TODO: Rename / translate
        ret.add(new DbCell("wf_" + toothnumber, wfFrom01Evidence(evidence, toothnumber)));
        // TODO: Rename / translate
        ret.add(new DbCell("wurzelstift_" + toothnumber, wurzelstiftFrom01Evidence(evidence, toothnumber)));
        ret.addAll(fillingAndCariesFrom01Evidence(evidence, toothnumber));
        return ret;
    }

    // TODO: Test
    private static String wurzelstiftFrom01Evidence(DbfRow evidence, String toothnumber) {
        return "TODO";
    }

    // TODO: Test
    private static String wfFrom01Evidence(DbfRow evidence, String toothnumber) {
        String wf = evidence.value("ZA" + toothnumber).substring(29, 30).trim();
        if (!ImmutableList.of("", "1", "0", "2", "3", "4", "5", "6", "7", "8", "9", "A", "D", "I", ">", "<", "@", ";", "?", "$", "=", ":").contains(wf)) {
//            if (wf.equals("=")) {
//                System.out.println(evidence.value("PATNR"));
//                System.out.println(evidence.value("DATUM"));
//                System.out.println(evidence.value("ZA" + toothnumber));
//                System.out.println(toothnumber);
//                System.out.println(wf);
//                System.out.println("--------------");
//            }
        }

//        if (evidence.value("PATNR").trim().equals("4250") && evidence.value("DATUM").equals("20100526")) {
//            System.out.println(toothnumber + ": " + evidence.value("ZA" + toothnumber));
//            System.out.println(wf);
//        }

        return "TODO";
    }

    // TODO: Test
    // TODO: Replace vitality values with constants
    private static String vitalityFrom01Evidence(DbfRow evidence, String toothnumber) {
        String vitality = evidence.value("ZA" + toothnumber).substring(2, 3).trim();
        return switch (vitality) {
            case "", "1" -> "";
            case "+", "2" -> "+";
            case "-", "3" -> "-";
            case "?", "!", ",", "4", "5", "6", "A" -> "?";
            default -> {
                Log.error("Unknown value for vitality: " + vitality);
                throw new IllegalArgumentException("Unknown value for vitality: " + vitality);
            }
        };
    }

    // TODO: Test
    // TODO: Replace Immutable List with constant
    private static String insufficientCrownFrom01Evidence(DbfRow evidence, String toothnumber) {
        return ImmutableList.of("1", "2", "3", "4").contains(evidence.value("ZA" + toothnumber).substring(2, 3).trim())
                ? "1"
                : "0";
    }

    private static String milktoothFrom01Evidence(DbfRow evidence, String toothnumber) {
        return (toothIsMarkedAsMilktooth(evidence, toothnumber)
                && toothnumberIsValidForMilktooth(toothnumber))
                ? Keys.EVIDENCE_MILKTOOTH_YES
                : Keys.EVIDENCE_MILKTOOTH_NO;
    }

    private static boolean toothIsMarkedAsMilktooth(DbfRow evidence, String toothnumber) {
        return DampsoftKeys.EVIDENCE_MILKTEETH.contains(
                evidence.value("ZA" + toothnumber).substring(0, 1));
    }

    private static boolean toothnumberIsValidForMilktooth(String toothnumber) {
        return Integer.parseInt(toothnumber.substring(1, 2)) <= 5;
    }

    private static String statusFrom01Evidence(DbfRow evidence, String toothnumber) {
        String status = evidence.value("ZA" + toothnumber).substring(1, 2);
        String evidenceStatus = DampsoftKeys.EVIDENCE_STATUS.getOrDefault(
                status,
                String.format("unknown status: %s", status));
        if (isLueckeVerengt(evidenceStatus)) {
            return directionLueckeVerengt(evidenceStatus, toothnumber);
        }
        return evidenceStatus;
    }

    private static boolean isLueckeVerengt(String status) {
        return Objects.equals(status, Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_RECHTS)
                || Objects.equals(status, Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_LINKS);
    }

    private static String directionLueckeVerengt(String evidenceStatus, String toothnumber) {
        Map<String, String> directions = ImmutableMap.<String, String>builder()
                .put(Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_RECHTS + "1", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_MESIAL)
                .put(Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_RECHTS + "2", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_DISTAL)
                .put(Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_RECHTS + "3", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_DISTAL)
                .put(Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_RECHTS + "4", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_MESIAL)
                .put(Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_LINKS + "1", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_DISTAL)
                .put(Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_LINKS + "2", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_MESIAL)
                .put(Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_LINKS + "3", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_MESIAL)
                .put(Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_LINKS + "4", Keys.EVIDENCE_STATUS__LUECKE_VERENGT_NACH_DISTAL)
                .build();
        return directions.get(evidenceStatus + ToothStringUtils.quadrant(toothnumber));
    }

    private static String sealingFrom01Evidence(DbfRow evidence, String toothnumber) {
        String sealing = evidence.value("ZA" + toothnumber).substring(38, 39);
        return DampsoftKeys.EVIDENCE_SEALING.getOrDefault(sealing,
                String.format("unknown sealing: %s", sealing));
    }

    private static String cariesForSurface(String surfaceString) {
        if (surfaceString.contains("G") && surfaceString.contains("C")) {
            return Keys.EVIDENCE_CARIES__TO_TREAT_WITH_XRAY;
        }
        if (surfaceString.contains("H") && surfaceString.contains("C")) {
            return Keys.EVIDENCE_CARIES__NOT_TO_TREAT_WITH_XRAY;
        }
        if (surfaceString.contains("G")) {
            return Keys.EVIDENCE_CARIES__TO_TREAT_WITHOUT_XRAY;
        }
        if (surfaceString.contains("H")) {
            return Keys.EVIDENCE_CARIES__NOT_TO_TREAT_WITHOUT_XRAY;
        }
        return Keys.EVIDENCE_CARIES__NO_CARIES;
    }

    private static String fillingForSurface(String value) {
        value = removeCariesKeys(value);
        if (Objects.equals(value, "")) {
            return Keys.FILLING__NO_FILLING;
        }
        List<String> chars = new ArrayList<>(new HashSet<>(Arrays.asList(value.split(""))));
        Collections.sort(chars);
        return DampsoftKeys.EVIDENCE_FILLING.getOrDefault(String.join("", chars),
                "unknown filling: " + String.join("", chars));
    }

    private static String removeCariesKeys(String value) {
        return value.replace("H", "").replace("G", "").replace("C", "");
    }

    private static Map<String, String> surfaceStringsFrom01Evidence(DbfRow evidence, String toothnumber) {
        String quadrant = toothnumber.substring(0, 1);
        String toothString = evidence.value("ZA" + toothnumber);
        Map<String, String> ret = new HashMap<>();
        ret.put(Keys.SURFACE_CERVIKAL, surfaceC(toothString));
        ret.put(Keys.SURFACE_DISTAL, surfaceD(toothString, quadrant));
        ret.put(Keys.SURFACE_LINGUAL, surfaceL(toothString, quadrant));
        ret.put(Keys.SURFACE_MESIAL, surfaceM(toothString, quadrant));
        ret.put(Keys.SURFACE_OKKLUSAL, surfaceO(toothString));
        ret.put(Keys.SURFACE_VESTIBULAER, surfaceV(toothString, quadrant));
        return ret;
    }

    private static String surfaceC(String toothString) {
        return toothString.substring(25, 29);
    }

    private static String surfaceD(String toothString, String quadrant) {
        return Objects.equals(quadrant, "2") || Objects.equals(quadrant, "3")
                ? toothString.substring(13, 17)
                : toothString.substring(5, 9);
    }

    private static String surfaceL(String toothString, String quadrant) {
        return Objects.equals(quadrant, "3") || Objects.equals(quadrant, "4")
                ? toothString.substring(9, 13)
                : toothString.substring(17, 21);
    }

    private static String surfaceM(String tooth, String quadrant) {
        return Objects.equals(quadrant, "1") || Objects.equals(quadrant, "4")
                ? tooth.substring(13, 17)
                : tooth.substring(5, 9);
    }

    private static String surfaceO(String toothString) {
        return toothString.substring(21, 25);
    }

    private static String surfaceV(String toothString,
                                   String quadrant) {
        return Objects.equals(quadrant, "1") || Objects.equals(quadrant, "2")
                ? toothString.substring(9, 13)
                : toothString.substring(17, 21);
    }

    private static List<DbCell> fillingAndCariesFrom01Evidence(DbfRow evidence,
                                                               String toothnumber) {
        List<DbCell> ret = new ArrayList<>();
        Map<String, String> surfaceStrings = surfaceStringsFrom01Evidence(evidence, toothnumber);
        for (String surface : Keys.SURFACES) {
            String surfaceString = surfaceStrings.get(surface);
            ret.add(new DbCell(
                    String.format("filling_%s_%s", toothnumber, surface),
                    fillingForSurface(surfaceString)));
            ret.add(new DbCell(
                    String.format("caries_%s_%s", toothnumber, surface),
                    cariesForSurface(surfaceString)));
        }
        return ret;
    }
}
