package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.mvc.exception.BasicTypeNotSupportedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BasicTypeTest {

    @Test
    @DisplayName("convert 메서드는 주어진 타입에 대해 문자열을 변환할 수 있다")
    void convertTest() {
        final Object result = BasicType.convert(Long.class, "123");

        assertThat(result).isEqualTo(123L);
    }

    @Test
    @DisplayName("convert 메서드는 지원되지 않는 타입에 대해 예외를 던져야 한다")
    void convertFailTest() {
        assertThatThrownBy(() -> BasicType.convert(Object.class, "test"))
                .isInstanceOf(BasicTypeNotSupportedException.class)
                .hasMessageStartingWith("BasicType does not support type");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "int",
            "long",
            "java.lang.Integer",
            "java.lang.Long",
            "java.lang.String",
    })
    @DisplayName("지원되는 타입에 대해 true 를 반환한다")
    void supportTest(final Class<?> type) {
        assertThat(BasicType.isSupportedType(type)).isTrue();
    }

    @Test
    @DisplayName("지원되지 않는 타입에 대해 false 를 반환한다")
    void notSupportTest() {
        assertThat(BasicType.isSupportedType(Object.class)).isFalse();
    }
}
