package com.interface21.webmvc.servlet.mvc.support;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TypeConversionUtilTest {

    public static Stream<Arguments> provideStringAndTargetType() {
        return Stream.of(
                Arguments.of("1", Integer.class, Integer.class),
                Arguments.of("1", int.class, Integer.class),
                Arguments.of("1.0", Double.class, Double.class),
                Arguments.of("1.0", double.class, Double.class),
                Arguments.of("true", Boolean.class, Boolean.class),
                Arguments.of("true", boolean.class, Boolean.class),
                Arguments.of("10000000000", Long.class, Long.class),
                Arguments.of("10000000000", long.class, Long.class),
                Arguments.of("1.0", Float.class, Float.class),
                Arguments.of("1.0", float.class, Float.class),
                Arguments.of("1", Short.class, Short.class),
                Arguments.of("1", short.class, Short.class),
                Arguments.of("1", Byte.class, Byte.class),
                Arguments.of("1", byte.class, Byte.class),
                Arguments.of("string", String.class, String.class)
        );
    }

    @DisplayName("문자열을 각 타겟 타입으로 변환한다.")
    @ParameterizedTest(name = "string={0}, targetType={1}")
    @MethodSource("provideStringAndTargetType")
    void test(String value, Class<?> targetType, Class<?> expectedType) {
        Object result = TypeConversionUtil.convertStringToTargetType(value, targetType);
        assertThat(result).isInstanceOf(expectedType);
    }
}