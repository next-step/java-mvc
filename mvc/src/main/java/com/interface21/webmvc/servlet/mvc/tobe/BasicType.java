package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.mvc.exception.BasicTypeNotSupportedException;

import java.util.Arrays;
import java.util.function.Function;

public enum BasicType {
    BOOLEAN(boolean.class, Boolean.class, Boolean::parseBoolean),
    BYTE(byte.class, Byte.class, Byte::parseByte),
    CHAR(char.class, Character.class, s -> s.charAt(0)),
    DOUBLE(double.class, Double.class, Double::parseDouble),
    FLOAT(float.class, Float.class, Float::parseFloat),
    INT(int.class, Integer.class, Integer::parseInt),
    LONG(long.class, Long.class, Long::parseLong),
    SHORT(short.class, Short.class, Short::parseShort),
    STRING(String.class, String.class, Function.identity());

    private final Class<?> primitiveType;
    private final Class<?> wrapperType;
    private final Function<String, ?> converter;

    BasicType(final Class<?> primitiveType, final Class<?> wrapperType, final Function<String, ?> converter) {
        this.primitiveType = primitiveType;
        this.wrapperType = wrapperType;
        this.converter = converter;
    }

    public static boolean isSupportedType(final Class<?> type) {
        return Arrays.stream(values())
                .anyMatch(supportType -> isSupportedType(type, supportType));
    }

    public static Object convert(final Class<?> type, final String value) {
        return Arrays.stream(values())
                .filter(supportType -> isSupportedType(type, supportType))
                .findFirst()
                .map(supportType -> supportType.converter.apply(value))
                .orElseThrow(() -> new BasicTypeNotSupportedException("BasicType does not support type: " + type));
    }

    private static boolean isSupportedType(final Class<?> type, final BasicType supportType) {
        return supportType.primitiveType.equals(type) || supportType.wrapperType.equals(type);
    }
}
