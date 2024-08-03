package com.interface21.core;

import java.lang.reflect.Parameter;
import java.util.Map;

public final class TypeResolver {

    private TypeResolver() {}

    private static final Map<Class<?>, Resolvable> RESOLVER_MAP =
            Map.of(
                    int.class, obj -> Integer.parseInt((String) obj),
                    String.class, value -> value);

    public static Object resolve(Parameter parameter, Object value) {
        var type = parameter.getType();
        var resolvable = RESOLVER_MAP.get(type);
        if (resolvable == null) {
            throw new IllegalArgumentException("unsupported type: " + type);
        }
        return resolvable.resolve(value);
    }

    interface Resolvable {
        Object resolve(Object value);
    }
}
