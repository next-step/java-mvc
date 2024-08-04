package com.interface21.core.util;

import java.lang.reflect.Type;

public class ConversionUtil {
    public static Object boxPrimitiveValue(String value, Type parameterType) {
        if (parameterType.equals(int.class)) {
            return Integer.parseInt(value);
        }
        if (parameterType.equals(long.class)) {
            return Long.parseLong(value);
        }
        if (parameterType.equals(boolean.class)) {
            return Boolean.valueOf(value);
        }
        if (parameterType.equals(float.class)) {
            return Float.valueOf(value);
        }
        if (parameterType.equals(short.class)) {
            return Short.parseShort(value);
        }
        if (parameterType.equals(byte.class)) {
            return Byte.parseByte(value);
        }
        if (parameterType.equals(double.class)) {
            return Double.parseDouble(value);
        }
        if (parameterType.equals(char.class)) {
            return value.charAt(0);
        }
        if (parameterType.equals(char[].class)) {
            return value.toCharArray();
        }

        return value;
    }
}
