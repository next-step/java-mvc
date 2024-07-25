package com.interface21.core.util;

import java.util.*;
import java.util.stream.Stream;

public final class StreamUtils {

    private StreamUtils() {}

    public static <E> Set<E> addAll(Set<E> a, Set<E> b) {
        a.addAll(b);
        return a;
    }

    /**
     * <b> allowed Immutable Collection parameter <b>
     */
    public static <K, V> Map<K, V> pullAll(Map<K, V> a, Map<K, V> b) {
        var aMap = new HashMap<>(a);
        aMap.putAll(b);
        return aMap;
    }

    public static <K, V> Stream<Map.Entry<K, V>> flattenValues(Map.Entry<K, List<V>> entry) {
        final var key = entry.getKey();
        return entry.getValue().stream().map(value -> Map.entry(key, value));
    }
}
