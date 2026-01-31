package de.dentareport.utils;

import de.dentareport.utils.db.DbCell;
import de.dentareport.utils.db.DbRow;

import java.util.*;

// TODO: TEST?
public class Dmft {

    private static Map<String, Integer> dmft;
    private static DbRow evidence;
    private static boolean isDft;
    private static boolean isMt;

    public static List<DbCell> calculate(DbRow dbRow) {
        evidence = dbRow;
        List<DbCell> ret = new ArrayList<>();
        ret.addAll(toothCount());
        ret.addAll(dmft());
        return ret;
    }

    private static List<DbCell> toothCount() {
        List<DbCell> ret = new ArrayList<>();
        ret.add(new DbCell("tooth_count_q1", toothCountInQuadrant("1")));
        ret.add(new DbCell("tooth_count_q2", toothCountInQuadrant("2")));
        ret.add(new DbCell("tooth_count_q3", toothCountInQuadrant("3")));
        ret.add(new DbCell("tooth_count_q4", toothCountInQuadrant("4")));
        return ret;
    }

    private static String toothCountInQuadrant(String quadrant) {
        int count = 0;
        for (String tooth : Toothnumbers.QUADRANT.get(quadrant)) {
            if (isNotMissing(tooth)) {
                count++;
            }
        }
        return String.valueOf(count);
    }

    private static boolean isNotMissing(String tooth) {
        return !isMissing(tooth);
    }

    private static boolean isMissing(String tooth) {
        return Keys.EVIDENCE_STATUS__MISSING_TEETH.contains(status(tooth));
    }

    private static String status(String tooth) {
        return evidence.value(String.format("status_%s", tooth));
    }

    private static List<DbCell> dmft() {
        List<DbCell> ret = new ArrayList<>();
        calculateDmft();
        ret.add(new DbCell("dt", dmftResult("dt")));
        ret.add(new DbCell("dt_milkteeth", dmftResult("dt_milkteeth")));
        ret.add(new DbCell("ft", dmftResult("ft")));
        ret.add(new DbCell("ft_milkteeth", dmftResult("ft_milkteeth")));
        ret.add(new DbCell("mt", dmftResult("mt")));
        ret.add(new DbCell("mt_milkteeth", dmftResult("mt_milkteeth")));
        ret.add(new DbCell("dft", dmftResult("dft")));
        ret.add(new DbCell("dmft", dmftResult("dmft")));
        ret.add(new DbCell("dmft_milkteeth", dmftResult("dmft_milkteeth")));
        return ret;
    }

    private static String dmftResult(String key) {
        return String.valueOf(dmft.getOrDefault(key, 0));
    }

    private static void calculateDmft() {
        dmft = new HashMap<>();
        for (String tooth : Toothnumbers.WITHOUT_8) {
            resetFlags();
            countDt(tooth);
            countFt(tooth);
            countMt(tooth);
            countDft();
            countDmft(tooth);
        }
    }

    private static void resetFlags() {
        isDft = false;
        isMt = false;
    }

    private static void countDt(String tooth) {
        if (hasCariesWithoutXray(tooth)) {
            isDft = true;
            countDependingOnMilktoothStatus(tooth, "dt");
        }
    }

    private static void countDependingOnMilktoothStatus(String tooth, String key) {
        if (isMilktooth(tooth)) {
            countAs(String.format("%s_milkteeth", key));
        } else {
            countAs(key);
        }
    }

    private static void countFt(String tooth) {
        if (hasFilling(tooth) || isCrown(tooth)) {
            isDft = true;
            countDependingOnMilktoothStatus(tooth, "ft");
        }
    }

    private static void countMt(String tooth) {
        if (isMissing(tooth)) {
            isMt = true;
            countDependingOnMilktoothStatus(tooth, "mt");
        }
    }

    private static void countDft() {
        if (isDft) {
            countAs("dft");
        }
    }

    private static void countDmft(String tooth) {
        if (isDft || isMt) {
            countDependingOnMilktoothStatus(tooth, "dmft");
        }
    }

    private static Boolean hasCariesWithoutXray(String tooth) {
        for (String surface : Keys.SURFACES) {
            if (surfaceHasCariesWithoutXray(tooth, surface)) {
                return true;
            }
        }
        return false;
    }

    private static boolean surfaceHasCariesWithoutXray(String tooth,
                                                       String surface) {
        return Keys.EVIDENCE_CARIES_WITHOUT_XRAY
                .contains(evidence.value(String.format("caries_%s_%s",
                        tooth,
                        surface))
                );
    }

    private static boolean isMilktooth(String tooth) {
        return Objects.equals(evidence.value(String.format("milktooth_%s", tooth)),
                Keys.EVIDENCE_MILKTOOTH_YES);
    }

    private static void countAs(String key) {
        dmft.put(key, dmft.getOrDefault(key, 0) + 1);
    }

    private static Boolean hasFilling(String tooth) {
        for (String surface : Keys.SURFACES) {
            if (surfaceHasFilling(tooth, surface)) {
                return true;
            }
        }
        return false;
    }

    private static boolean surfaceHasFilling(String tooth,
                                             String surface) {
        return !Objects.equals(evidence.value(String.format("filling_%s_%s", tooth, surface)),
                Keys.FILLING__NO_FILLING);
    }

    private static boolean isCrown(String tooth) {
        return Objects.equals(status(tooth), Keys.EVIDENCE_STATUS__KRONE);
    }
}
