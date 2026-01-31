package de.dentareport.utils;

import java.util.HashMap;
import java.util.Map;

// TODO: TEST?
public class MiniCache {

    private static Map<String, String> cache = new HashMap<>();

    public static void put(String key,
                           String value) {
        cache.put(key, value);
    }

    public static String get(String key) {
        String ret = cache.get(key);
        if (ret == null) {
            throw new IllegalArgumentException(key);
        }
        return ret;
    }

    public static String getOrDefault(String key, String defaultValue) {
        String ret = cache.getOrDefault(key, defaultValue);
        cache.put(key, ret);
        return ret;
    }

    public static void clear() {
        cache.clear();
    }
}
