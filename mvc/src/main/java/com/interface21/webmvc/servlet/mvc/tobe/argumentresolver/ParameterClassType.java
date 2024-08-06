package com.interface21.webmvc.servlet.mvc.tobe.argumentresolver;

import java.util.Arrays;
import java.util.function.Function;

public enum ParameterClassType {
    STRING(String.class, String.class, Function.identity()),
    INTEGER(int.class, Integer.class, Integer::parseInt),
    LONG(long.class, Long.class, Long::parseLong),
    DOUBLE(double.class, Double.class, Double::parseDouble),
    FLOAT(float.class, Float.class, Float::parseFloat),
    BOOLEAN(boolean.class, Boolean.class, Boolean::parseBoolean),
    SHORT(short.class, Short.class, Short::parseShort),
    BYTE(byte.class, Byte.class, Byte::parseByte),
    CHARACTER(char.class, Character.class, s -> s.charAt(0)),
    ;

    private final Class<?> primitiveType;
    private final Class<?> wrapperType;
    private final Function<String, ?> converter;

    ParameterClassType(final Class<?> primitiveType, final Class<?> wrapperType, final Function<String, ?> converter) {
        this.primitiveType = primitiveType;
        this.wrapperType = wrapperType;
        this.converter = converter;
    }

    public static boolean isSupportedType(final Class<?> type) {
        return Arrays.stream(values())
                .anyMatch(parameterClassType -> isSupportedType(parameterClassType, type));
    }

    public static Object parse(final Class<?> type, final String value) {
        return Arrays.stream(values())
                .filter(parameterClassType -> isSupportedType(parameterClassType, type))
                .findFirst()
                .map(parameterClassType -> parameterClassType.converter.apply(value))
                .orElseThrow(() -> new IllegalArgumentException("No such type : " + type));
    }

    private static boolean isSupportedType(final ParameterClassType parameterClassType, final Class<?> type) {
        return parameterClassType.primitiveType.equals(type) || parameterClassType.wrapperType.equals(type);
    }
}
