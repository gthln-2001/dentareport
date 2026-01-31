package de.dentareport.utils;

import com.google.common.collect.ImmutableList;

import java.util.*;

// TODO: TEST?
public class Toothnumbers {

    public static final List<String> ALL = ImmutableList.of(
            "11", "12", "13", "14", "15", "16", "17", "18",
            "21", "22", "23", "24", "25", "26", "27", "28",
            "31", "32", "33", "34", "35", "36", "37", "38",
            "41", "42", "43", "44", "45", "46", "47", "48"
    );

    public static final List<String> ALL_WITH_MILKTEETH = ImmutableList.of(
            "11", "12", "13", "14", "15", "16", "17", "18",
            "21", "22", "23", "24", "25", "26", "27", "28",
            "31", "32", "33", "34", "35", "36", "37", "38",
            "41", "42", "43", "44", "45", "46", "47", "48",
            "51", "52", "53", "54", "55", "56", "57", "58",
            "61", "62", "63", "64", "65", "66", "67", "68",
            "71", "72", "73", "74", "75", "76", "77", "78",
            "81", "82", "83", "84", "85", "86", "87", "88"
    );

    public static final Map<String, List<String>> QUADRANT = quadrant();

    public static final List<String> LOWER_JAW = ImmutableList.of(
            "31", "32", "33", "34", "35", "36", "37", "38",
            "41", "42", "43", "44", "45", "46", "47", "48"
    );

    public static final List<String> UPPER_JAW = ImmutableList.of(
            "11", "12", "13", "14", "15", "16", "17", "18",
            "21", "22", "23", "24", "25", "26", "27", "28"
    );

    public static final List<String> WITHOUT_8 = ImmutableList.of(
            "11", "12", "13", "14", "15", "16", "17",
            "21", "22", "23", "24", "25", "26", "27",
            "31", "32", "33", "34", "35", "36", "37",
            "41", "42", "43", "44", "45", "46", "47"
    );

    private static Map<String, List<String>> quadrant() {
        Map<String, List<String>> ret = new HashMap<>();
        ret.put("1", ImmutableList.of("11", "12", "13", "14", "15", "16", "17", "18"));
        ret.put("2", ImmutableList.of("21", "22", "23", "24", "25", "26", "27", "28"));
        ret.put("3", ImmutableList.of("31", "32", "33", "34", "35", "36", "37", "38"));
        ret.put("4", ImmutableList.of("41", "42", "43", "44", "45", "46", "47", "48"));
        return ret;
    }
}
