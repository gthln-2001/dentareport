package de.dentareport.utils;

import de.dentareport.models.Evidence01Interface;
import de.dentareport.models.NullEvidence01;
import de.dentareport.models.RawData;

import java.util.stream.Stream;

// TODO: TEST?
public class Evidences01 {

    public static Evidence01Interface first(RawData rawData) {
        return firstOf(rawData.evidences01().stream());
    }

    public static Evidence01Interface firstFrom(RawData rawData, String from) {
        return firstOf(rawData.evidences01().stream()
                              .filter(e -> e.isFrom(from)));
    }

    public static Evidence01Interface firstAfter(RawData rawData, String after) {
        return firstOf(rawData.evidences01().stream()
                              .filter(e -> e.isAfter(after)));
    }

    public static Evidence01Interface firstBefore(RawData rawData, String before) {
        return firstOf(rawData.evidences01().stream()
                              .filter(e -> e.isBefore(before)));
    }

    public static Evidence01Interface firstUntil(RawData rawData, String until) {
        return firstOf(rawData.evidences01().stream()
                              .filter(e -> e.isUntil(until)));
    }

    public static Evidence01Interface firstFromBefore(RawData rawData, String from, String before) {
        return firstOf(rawData.evidences01().stream()
                              .filter(e -> e.isFrom(from))
                              .filter(e -> e.isBefore(before)));
    }

    public static Evidence01Interface firstFromUntil(RawData rawData, String from, String until) {
        return firstOf(rawData.evidences01().stream()
                              .filter(e -> e.isFrom(from))
                              .filter(e -> e.isUntil(until)));
    }

    public static Evidence01Interface firstAfterBefore(RawData rawData, String after, String before) {
        return firstOf(rawData.evidences01().stream()
                              .filter(e -> e.isAfter(after))
                              .filter(e -> e.isBefore(before)));
    }

    public static Evidence01Interface firstAfterUntil(RawData rawData, String after, String until) {
        return firstOf(rawData.evidences01().stream()
                              .filter(e -> e.isAfter(after))
                              .filter(e -> e.isUntil(until)));
    }

    public static Evidence01Interface last(RawData rawData) {
        return lastOf(rawData.evidences01().stream());
    }

    public static Evidence01Interface lastFrom(RawData rawData, String from) {
        return lastOf(rawData.evidences01().stream()
                             .filter(e -> e.isFrom(from)));
    }

    public static Evidence01Interface lastAfter(RawData rawData, String after) {
        return lastOf(rawData.evidences01().stream()
                             .filter(e -> e.isAfter(after)));
    }

    public static Evidence01Interface lastBefore(RawData rawData, String before) {
        return lastOf(rawData.evidences01().stream()
                             .filter(e -> e.isBefore(before)));
    }

    public static Evidence01Interface lastUntil(RawData rawData, String until) {
        return lastOf(rawData.evidences01().stream()
                             .filter(e -> e.isUntil(until)));
    }

    public static Evidence01Interface lastFromBefore(RawData rawData, String from, String before) {
        return lastOf(rawData.evidences01().stream()
                             .filter(e -> e.isFrom(from))
                             .filter(e -> e.isBefore(before)));
    }

    public static Evidence01Interface lastFromUntil(RawData rawData, String from, String until) {
        return lastOf(rawData.evidences01().stream()
                             .filter(e -> e.isFrom(from))
                             .filter(e -> e.isUntil(until)));
    }

    public static Evidence01Interface lastAfterBefore(RawData rawData, String after, String before) {
        return lastOf(rawData.evidences01().stream()
                             .filter(e -> e.isAfter(after))
                             .filter(e -> e.isBefore(before)));
    }

    public static Evidence01Interface lastAfterUntil(RawData rawData, String after, String until) {
        return lastOf(rawData.evidences01().stream()
                             .filter(e -> e.isAfter(after))
                             .filter(e -> e.isUntil(until)));
    }

    public static String count(RawData rawData) {
        return String.valueOf(rawData.evidences01().size());
    }

    public static String countFrom(RawData rawData, String from) {
        return String.valueOf(rawData.evidences01().stream()
                                     .filter(t -> t.isFrom(from))
                                     .count());
    }

    public static String countAfter(RawData rawData, String after) {
        return String.valueOf(rawData.evidences01().stream()
                                     .filter(t -> t.isAfter(after))
                                     .count());
    }

    public static String countBefore(RawData rawData, String before) {
        return String.valueOf(rawData.evidences01().stream()
                                     .filter(t -> t.isBefore(before))
                                     .count());
    }

    public static String countUntil(RawData rawData, String until) {
        return String.valueOf(rawData.evidences01().stream()
                                     .filter(t -> t.isUntil(until))
                                     .count());
    }

    public static String countFromBefore(RawData rawData, String from, String before) {
        return String.valueOf(rawData.evidences01().stream()
                                     .filter(t -> t.isFrom(from))
                                     .filter(t -> t.isBefore(before))
                                     .count());
    }

    public static String countFromUntil(RawData rawData, String from, String until) {
        return String.valueOf(rawData.evidences01().stream()
                                     .filter(t -> t.isFrom(from))
                                     .filter(t -> t.isUntil(until))
                                     .count());
    }

    public static String countAfterBefore(RawData rawData, String after, String before) {
        return String.valueOf(rawData.evidences01().stream()
                                     .filter(t -> t.isAfter(after))
                                     .filter(t -> t.isBefore(before))
                                     .count());
    }

    public static String countAfterUntil(RawData rawData, String after, String until) {
        return String.valueOf(rawData.evidences01().stream()
                                     .filter(t -> t.isAfter(after))
                                     .filter(t -> t.isUntil(until))
                                     .count());
    }

    private static Evidence01Interface firstOf(Stream<Evidence01Interface> stream) {
        return stream.findFirst().orElse(new NullEvidence01());
    }

    private static Evidence01Interface lastOf(Stream<Evidence01Interface> stream) {
        return stream.reduce((first, second) -> second) /* Get the last element of the stream*/
                     .orElse(new NullEvidence01());
    }
}
