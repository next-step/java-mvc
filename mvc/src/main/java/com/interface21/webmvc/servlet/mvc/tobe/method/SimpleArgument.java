package com.interface21.webmvc.servlet.mvc.tobe.method;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum SimpleArgument {

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


    SimpleArgument(Class<?> primitiveType, Class<?> wrapperType, Function<String, ?> converter) {
        this.primitiveType = primitiveType;
        this.wrapperType = wrapperType;
        this.converter = converter;
    }
    private static final Map<Class<?>, SimpleArgument> PRIMITIVE_TYPE_MAP = Arrays.stream(values())
        .collect(Collectors.toMap(simpleArgument -> simpleArgument.primitiveType, Function.identity()));

    private static final Map<Class<?>, SimpleArgument> WRAPPER_TYPE_MAP = Arrays.stream(values())
        .collect(Collectors.toMap(simpleArgument -> simpleArgument.wrapperType, Function.identity()));

    private static final Map<Class<?>, Object> DEFAULT_VALUES = Map.of(
        int.class, 0,
        long.class, 0L,
        double.class, 0.0,
        float.class, 0.0f,
        boolean.class, false,
        short.class, (short) 0,
        byte.class, (byte) 0,
        char.class, '\u0000'
    );

    public static Object convert(Class<?> type, String variableValue) {
        if (type.isPrimitive()) {
            return primitiveConvert(type, variableValue);
        }
        return WRAPPER_TYPE_MAP.get(type).converter.apply(variableValue);
    }

    private static Object primitiveConvert(Class<?> type, String variableValue) {
        if (variableValue == null) {
            return DEFAULT_VALUES.get(type);
        }
        return PRIMITIVE_TYPE_MAP.get(type).converter.apply(variableValue);
    }

    public static boolean isSimpleParameter(Class<?> type) {
        return PRIMITIVE_TYPE_MAP.containsKey(type) || WRAPPER_TYPE_MAP.containsKey(type);
    }
}
