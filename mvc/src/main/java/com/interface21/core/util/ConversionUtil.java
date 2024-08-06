package com.interface21.core.util;

import java.lang.reflect.Type;

public class ConversionUtil {

    public static final char[] EMPTY_CHARS = {};

    public static Object boxPrimitiveValue(String value, Type parameterType) {
        if (parameterType.equals(int.class)) {
            return value == null ? 0 : Integer.parseInt(value);
        }
        if (parameterType.equals(long.class)) {
            return value == null ? 0L : Long.parseLong(value);
        }
        if (parameterType.equals(boolean.class)) {
            return Boolean.parseBoolean(value);
        }
        if (parameterType.equals(float.class)) {
            return value == null ? 0.0 : Float.parseFloat(value);
        }
        if (parameterType.equals(short.class)) {
            return value == null ? 0 : Short.parseShort(value);
        }
        if (parameterType.equals(byte.class)) {
            return value == null ? (byte) 0 : Byte.parseByte(value);
        }
        if (parameterType.equals(double.class)) {
            return value == null ? 0 : Double.parseDouble(value);
        }
        if (parameterType.equals(char.class)) {
            return value.charAt(0);
        }
        if (parameterType.equals(char[].class)) {
            return value == null ? EMPTY_CHARS : value.toCharArray();
        }

        return value;
    }
}
