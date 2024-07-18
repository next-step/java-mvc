package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import java.util.Arrays;
import java.util.List;

public enum SupportParameterType {
    INT(List.of(int.class, Integer.class)),
    LONG(List.of(long.class, Long.class)),
    STRING(List.of(String.class)),
    ;

    private final List<Class<?>> types;

    SupportParameterType(List<Class<?>> types) {
        this.types = types;
    }

    public static boolean isSupport(Class<?> clazz) {
        return Arrays.stream(values())
                .anyMatch(value -> value.types.contains(clazz));
    }
}
