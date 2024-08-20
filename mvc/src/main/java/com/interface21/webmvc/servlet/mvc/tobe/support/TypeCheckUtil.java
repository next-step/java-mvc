package com.interface21.webmvc.servlet.mvc.tobe.support;

public class TypeCheckUtil {

    @SuppressWarnings("unchecked")
    public static  <T> T convertStringToTargetType(String value, Class<?> targetType) {
        if (targetType == Integer.class || targetType == int.class) {
            return (T) Integer.valueOf(value);
        } else if (targetType == Double.class || targetType == double.class) {
            return (T) Double.valueOf(value);
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            return (T) Boolean.valueOf(value);
        } else if (targetType == Long.class || targetType == long.class) {
            return (T) Long.valueOf(value);
        } else if (targetType == String.class) {
            return (T) value;
        } else {
            throw new IllegalArgumentException("지원 가능한 타입이 압니다. type :: " + targetType.getName());
        }
    }
}
