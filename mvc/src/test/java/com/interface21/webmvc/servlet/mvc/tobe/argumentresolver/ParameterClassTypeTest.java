package com.interface21.webmvc.servlet.mvc.tobe.argumentresolver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class ParameterClassTypeTest {

    private static Object[][] provideParameterClassType() {
        return new Object[][]{
                {String.class, "test", "test"},
                {int.class, "1", 1},
                {long.class, "1", 1L},
                {double.class, "1.0", 1.0},
                {float.class, "1.0", 1.0f},
                {boolean.class, "true", true},
                {short.class, "1", (short) 1},
                {byte.class, "1", (byte) 1},
                {char.class, "a", 'a'},
                {Integer.class, "1", 1},
                {Long.class, "1", 1L},
                {Double.class, "1.0", 1.0},
                {Float.class, "1.0", 1.0f},
                {Boolean.class, "true", true},
                {Short.class, "1", (short) 1},
                {Byte.class, "1", (byte) 1},
                {Character.class, "a", 'a'},
        };
    }

    @DisplayName("ParameterClassType.parse 메서드 테스트")
    @ParameterizedTest
    @MethodSource("provideParameterClassType")
    void parseTest(final Class<?> type, final String value, final Object expect) {
        // given // when
        final Object result = ParameterClassType.parse(type, value);

        // then
        assertThat(result).isEqualTo(expect);
    }
}
