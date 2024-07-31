package com.interface21.webmvc.servlet.mvc.handler.parameter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BasicArgumentTypeTest {

    @Test
    @DisplayName("String을 그대로 반환한다.")
    void convert_String() {
        // given
        String value = "StringValue";
        Class<?> targetType = String.class;

        // when
        Object result = BasicArgumentType.convert(value, targetType);

        // then
        assertThat(result).isInstanceOf(String.class);
    }

    @ParameterizedTest
    @DisplayName("String을 Integer로 변환한다.")
    @MethodSource("provideIntegerTargetType")
    void convert_Integer(Class<?> targetType) {
        // given
        String value = "1";

        // when
        Object result = BasicArgumentType.convert(value, targetType);

        // then
        assertThat(result).isInstanceOf(Integer.class);
    }

    public static Stream<Arguments> provideIntegerTargetType() {
        return Stream.of(
            Arguments.of(Integer.class),
            Arguments.of(int.class)
        );
    }

    @ParameterizedTest
    @DisplayName("TargetType이 Long.class, long.class인 경우 Long으로 변환한다.")
    @MethodSource("provideLongTargetType")
    void convert_Long(Class<?> targetType) {
        // given
        String value = "1";

        // when
        Object result = BasicArgumentType.convert(value, targetType);

        // then
        assertThat(result).isInstanceOf(Long.class);
    }

    public static Stream<Arguments> provideLongTargetType() {
        return Stream.of(
            Arguments.of(Long.class),
            Arguments.of(long.class)
        );
    }
}
