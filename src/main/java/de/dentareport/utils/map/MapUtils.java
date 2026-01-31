package de.dentareport.utils.map;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO: TEST?
public class MapUtils {

    public static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(Map<K, V> mapToSort) {
        Map<K, V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K, V>> sequentialStream = mapToSort.entrySet().stream();

        // comparingByKey() returns a comparator that compares Map.Entry in natural order on key.
        sequentialStream.sorted(Map.Entry.comparingByKey())
                .forEachOrdered(c -> result.put(c.getKey(), c.getValue()));
        return result;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(final Map<K, V> mapToSort) {
        List<Map.Entry<K, V>> entries = new ArrayList<>(mapToSort.size());
        entries.addAll(mapToSort.entrySet());
        entries.sort(Comparator.comparing(Map.Entry::getValue));
        Map<K, V> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public static Collector<String, ?, LinkedHashMap<String, Map<String, String>>> toLinkedMap(
            Function<String, String> keyMapper,
            Function<String, Map<String, String>> valueMapper) {
        return Collectors.toMap(
                keyMapper,
                valueMapper,
                (u, v) -> {
                    throw new IllegalStateException(String.format("Duplicate key %s", u));
                },
                LinkedHashMap::new);
    }
}
