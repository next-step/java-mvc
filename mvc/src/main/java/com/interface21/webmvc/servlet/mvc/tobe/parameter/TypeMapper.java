package com.interface21.webmvc.servlet.mvc.tobe.parameter;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public final class TypeMapper {

    private static Map<Class<?>, Function<String, ?>> typeToParser;

    private TypeMapper() {

    }

    public static Optional<Function<String, ?>> getParser(Class<?> clazz) {
        if (typeToParser == null) {
            createTypeToParser();
        }

        return Optional.ofNullable(typeToParser.get(clazz));
    }

    public static Object parse(Class<?> targetType, Object requestValue){
        return getParser(targetType).orElseThrow(() -> new IllegalArgumentException("지원하지 않는 형식의 데이터 형식입니다."))
            .apply((String) requestValue);
    }

    public static void createTypeToParser() {
        typeToParser.put(long.class, Boolean::parseBoolean);
        typeToParser.put(Long.class, Long::parseLong);
        typeToParser.put(String.class, s -> s);
        typeToParser.put(boolean.class, Boolean::parseBoolean);
        typeToParser.put(Boolean.class, Boolean::parseBoolean);
        typeToParser.put(int.class, Integer::parseInt);
        typeToParser.put(Integer.class, Integer::parseInt);
    }

}
