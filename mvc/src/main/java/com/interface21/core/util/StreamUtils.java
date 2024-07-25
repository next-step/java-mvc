package com.interface21.core.util;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

public final class StreamUtils {

    private StreamUtils() {}

    public static <E> Set<E> addAll(Set<E> a, Set<E> b) {
        a.addAll(b);
        return a;
    }

    public static <K, V> Map<K, V> pullAll(Map<K, V> a, Map<K, V> b) {
        a.putAll(b);
        return a;
    }

    public static Stream<Map.Entry<Object, Method>> flattenValues(
            Map.Entry<Object, List<Method>> entry) {
        return entry.getValue().stream().map(method -> Map.entry(entry.getKey(), method));
    }
}
