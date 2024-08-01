package com.interface21.webmvc.servlet.mvc.handler.parameter;

import java.util.Arrays;
import java.util.function.Function;

public enum BasicArgumentType {
    STRING(String.class, value -> value),
    INTEGER(int.class, Integer::parseInt),
    INTEGER_OBJECT(Integer.class, Integer::valueOf),
    LONG(long.class, Long::parseLong),
    LONG_OBJECT(Long.class, Long::valueOf);

    private final Class<?> type;
    private final Function<String, ?> converter;

    BasicArgumentType(Class<?> type, Function<String, ?> converter) {
        this.type = type;
        this.converter = converter;
    }

    public static Object convert(String value, Class<?> targetType) {
        return Arrays.stream(values())
                     .filter(argumentType -> argumentType.type.equals(targetType))
                     .findAny()
                     .map(argumentType -> argumentType.converter.apply(value))
                     .orElseThrow(() -> new IllegalArgumentException("Unsupported type: " + targetType.getName()));
    }

    public static boolean isBasicType(Class<?> type) {
        return Arrays.stream(values())
                     .anyMatch(argumentType -> argumentType.type.equals(type));
    }
}
