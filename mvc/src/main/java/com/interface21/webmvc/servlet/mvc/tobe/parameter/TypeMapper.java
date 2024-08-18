package com.interface21.webmvc.servlet.mvc.tobe.parameter;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public final class TypeMapper {

    private final static Map<Class<?>, Function<String, ?>> typeToParser
        = Map.of(
        long.class, Long::parseLong,
        Long.class, Long::parseLong,
        String.class, s -> s,
        boolean.class, Boolean::parseBoolean,
        Boolean.class, Boolean::parseBoolean,
        int.class, Integer::parseInt,
        Integer.class, Integer::parseInt
    );

    private TypeMapper() {
    }

    public static Optional<Function<String, ?>> getParser(Class<?> clazz) {

        return Optional.ofNullable(typeToParser.get(clazz));
    }

    public static Object parse(Class<?> targetType, Object requestValue) {
        return getParser(targetType).orElseThrow(
                () -> new IllegalArgumentException("지원하지 않는 형식의 데이터 형식입니다."))
            .apply((String) requestValue);
    }


}
