package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public enum SupportParameterType {
    INT(List.of(int.class, Integer.class), Integer::parseInt),
    LONG(List.of(long.class, Long.class), Long::parseLong),
    STRING(List.of(String.class), value -> value),
    ;

    private final List<Class<?>> types;
    private final Function<String, ?> parser;

    SupportParameterType(List<Class<?>> types, Function<String, ?> parser) {
        this.types = types;
        this.parser = parser;
    }

    public static boolean isSupport(Class<?> clazz) {
        return Arrays.stream(values())
                .anyMatch(value -> value.types.contains(clazz));
    }

    public static Object parse(Class<?> requestType, Object request) {
        return Arrays.stream(values())
                .filter(value -> value.types.contains(requestType))
                .findAny()
                .map(value -> value.parser.apply((String) request))
                .orElseThrow(() -> new IllegalStateException("지원가능한 타입이 없습니다."));
    }
}
