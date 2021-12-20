package com.movies.api.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoginCache {
    private static Map<String, String> cache = new ConcurrentHashMap<>();

    public static void set(String key, String value) {
        cache.put(key, value);
    }

    public static String get(String key) {
        return cache.get(key);
    }

    public static void remove(String key){
        cache.remove(key);
    }

    public static boolean verify(String key, String token) {
        boolean valid = false;
        if (key != null) {
            if (cache.containsKey(key)) {
                String value = cache.get(key);
                if (token.equals(value)) {
                    valid = true;
                }
            }
        }
        return valid;
    }
}
