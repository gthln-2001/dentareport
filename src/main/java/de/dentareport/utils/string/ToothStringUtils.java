package de.dentareport.utils.string;

import com.google.common.collect.ImmutableMap;
import de.dentareport.utils.Keys;
import de.dentareport.utils.Toothnumbers;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

// TODO: TEST?
public class ToothStringUtils {

    public static List<String> splitToothrange(String toothrange) {
        Set<String> toothlist = new HashSet<>();
        for (String chunk : replaceSeparators(toothrange).split(",")) {
            toothlist.addAll(handleChunk(chunk));
        }
        return asSortedList(toothlist);
    }

    public static boolean isTooth(String value) {
        return Toothnumbers.ALL_WITH_MILKTEETH.contains(value);
    }

    public static boolean isInQuadrant(String tooth,
                                       String quadrant) {
        return Objects.equals(quadrant(tooth), quadrant);
    }

    public static boolean isInJaw(String tooth,
                                  String jaw) {
        if (Objects.equals(jaw, Keys.UPPER_JAW)) {
            return isInQuadrant(tooth, "1") || isInQuadrant(tooth, "2");
        }
        return isInQuadrant(tooth, "3") || isInQuadrant(tooth, "4");
    }

    public static String neighbourDistal(String tooth) {
        String ret = "";
        String quadrant = quadrant(tooth);
        String position = position(tooth);

        if (Objects.equals(position, "8")) {
            return ret;
        }
        return String.format("%s%s", quadrant, Integer.parseInt(position) + 1);
    }

    public static String neighbourMesial(String tooth) {
        String quadrant = quadrant(tooth);
        String position = position(tooth);
        if (Objects.equals(position, "1")) {
            return firstToothInNeighbouringQuadrant(tooth, quadrant);
        }
        return quadrant + (Integer.parseInt(position) - 1);
    }

    public static boolean isMilktooth(String tooth) {
        return Integer.parseInt(tooth.substring(0, 1)) > 4;
    }

    public static String position(String tooth) {
        return tooth.substring(1, 2);
    }

    public static String quadrant(String tooth) {
        return tooth.substring(0, 1);
    }

    public static List<String> neighboursMesial(String tooth,
                                                int count) {
        String startTooth = tooth;
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            tooth = neighbourMesial(tooth);
            if (Objects.equals(tooth, "")) {
                return ret;
            }
            ret.add(tooth);
            if (!isInQuadrant(tooth, quadrant(startTooth))) {
                ret.addAll(neighboursDistal(tooth, count - i - 1));
                return ret;
            }
        }
        return ret;
    }

    public static List<String> neighboursDistal(String tooth,
                                                int count) {
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            tooth = neighbourDistal(tooth);
            if (Objects.equals(tooth, "")) {
                return ret;
            }
            ret.add(tooth);
        }
        return ret;
    }

    public static List<String> directNeighbours(String tooth) {
        List<String> ret = new ArrayList<>();
        ret.addAll(neighboursDistal(tooth, 1));
        ret.addAll(neighboursMesial(tooth, 1));
        return ret;
    }

    public static String type(String tooth) {
        Map<String, String> types = ImmutableMap.of(
                "1", Keys.FRONTZAHN,
                "2", Keys.FRONTZAHN,
                "3", Keys.ECKZAHN,
                "4", Keys.PRAEMOLAR,
                "5", Keys.PRAEMOLAR
        );
        return types.getOrDefault(position(tooth), Keys.MOLAR);
    }

    public static String jaw(String tooth) {
        return isInJaw(tooth, Keys.UPPER_JAW)
               ? Keys.UPPER_JAW
               : Keys.LOWER_JAW;
    }

    private static String firstToothInNeighbouringQuadrant(String tooth,
                                                           String quadrant) {
        if (isInQuadrant(tooth, "1") || isInQuadrant(tooth, "3")) {
            return String.format("%d1", Integer.parseInt(quadrant) + 1);
        }
        return String.format("%d1", Integer.parseInt(quadrant) - 1);
    }

    private static Set<String> handleChunk(String chunk) {
        chunk = chunk.trim();
        if (containsRange(chunk)) {
            return handleRange(chunk);
        }
        return handleEntity(chunk);
    }

    private static Set<String> handleRange(String chunk) {
        Set<String> ret = new HashSet<>();
        String[] delimiters = delimiters(chunk);
        if (validDelimiters(delimiters)) {
            ret.addAll(teethForQuadrants(delimiters));
            return ret;
        }
        ret.add("");
        return ret;
    }

    private static String[] delimiters(String chunk) {
        String[] ret = Arrays.stream(chunk.split("-"))
                             .map(String::trim)
                             .toArray(String[]::new);
        Arrays.sort(ret);
        return ret;
    }

    private static boolean validDelimiters(String[] delimiters) {
        return delimiters.length == 2 && validQuadrants(delimiters);
    }

    private static Set<String> handleEntity(String chunk) {
        Set<String> ret = new HashSet<>();
        for (String tooth : replaceJawDescription(chunk).split(",")) {
            ret.add(handleTooth(tooth));
        }
        return ret;
    }

    private static String handleTooth(String tooth) {
        if (isTooth(tooth)) {
            return tooth;
        }
        return "";
    }

    private static String replaceJawDescription(String chunk) {
        chunk = chunk.replace("OK", String.join(",", Toothnumbers.UPPER_JAW));
        chunk = chunk.replace("UK", String.join(",", Toothnumbers.LOWER_JAW));
        chunk = chunk.replace(" ", ",");    // Without this "UK 15" would become "... 47, 48 15"
        return chunk;
    }

    private static String replaceSeparators(String value) {
        return value.toUpperCase()
                    .replace("+", ",")
                    .replace("/", ",");
    }

    private static boolean containsRange(String chunk) {
        return StringUtils.countMatches(chunk, "-") == 1;
    }

    private static boolean validQuadrants(String[] rangeDelimiters) {
        String quadrant1 = quadrant(rangeDelimiters[0]);
        String quadrant2 = quadrant(rangeDelimiters[1]);
        return isSameQuadrant(quadrant1, quadrant2)
               || isBothUpperJaw(quadrant1, quadrant2)
               || isBothLowerJaw(quadrant1, quadrant2);
    }

    private static boolean isSameQuadrant(String quadrant1,
                                          String quadrant2) {
        return Objects.equals(quadrant1, quadrant2);
    }

    private static boolean isBothUpperJaw(String quadrant1,
                                          String quadrant2) {
        return (Objects.equals(quadrant1, "1") && Objects.equals(quadrant2, "2"))
               || (Objects.equals(quadrant1, "5") && Objects.equals(quadrant2, "6"));
    }

    private static boolean isBothLowerJaw(String quadrant1,
                                          String quadrant2) {
        return (Objects.equals(quadrant1, "3") && Objects.equals(quadrant2, "4"))
               || (Objects.equals(quadrant1, "7") && Objects.equals(quadrant2, "7"));
    }

    private static Set<String> teethForQuadrants(String[] rangeDelimiters) {
        Set<String> ret = new HashSet<>();
        Map<String, String> range = explodeRange(rangeDelimiters);
        if (isSameQuadrant(range.get("quadrant1"), range.get("quadrant2"))) {
            ret.addAll(teethForQuadrant(range.get("quadrant1"), range.get("tooth2"), range.get("tooth1")));
            return ret;
        }
        ret.addAll(teethForQuadrant(range.get("quadrant1"), range.get("tooth1")));
        ret.addAll(teethForQuadrant(range.get("quadrant2"), range.get("tooth2")));
        return ret;
    }

    private static Map<String, String> explodeRange(String[] range) {
        Map<String, String> ret = new HashMap<>();
        ret.put("quadrant1", quadrant(range[0]));
        ret.put("tooth1", position(range[0]));
        ret.put("quadrant2", quadrant(range[1]));
        ret.put("tooth2", position(range[1]));
        return ret;
    }

    private static Set<String> teethForQuadrant(String quadrant,
                                                String tooth,
                                                String limit) {
        Set<String> ret = new HashSet<>();
        for (int i = Integer.parseInt(tooth); i >= Integer.parseInt(limit); i--) {
            ret.add(String.format("%s%s", quadrant, i));
        }
        return ret;
    }

    private static Set<String> teethForQuadrant(String quadrant,
                                                String tooth) {
        return teethForQuadrant(quadrant, tooth, "1");
    }

    private static List<String> asSortedList(Set<String> toothlist) {
        List<String> ret = new ArrayList<>(toothlist);
        Collections.sort(ret);
        return ret;
    }
}