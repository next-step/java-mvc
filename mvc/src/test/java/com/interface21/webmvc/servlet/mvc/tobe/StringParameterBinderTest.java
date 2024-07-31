package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StringParameterBinderTest {

    private final StringParameterBinder binder = new StringParameterBinder();

    @DisplayName("support 메서드 호출 시 파라미터가 String 인 경우 true 를 반환 한다")
    @Test
    public void support() throws Exception {
        // given
        final Parameter parameter = mock(Parameter.class);

        when(parameter.getType()).thenReturn((Class) String.class);

        // when
        final boolean actual = binder.supports(parameter);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("support 메서드 호출 시 파라미터가 String 이 아닌 경우 true 를 반환 한다")
    @Test
    public void notSupport() throws Exception {
        // given
        final Parameter parameter = mock(Parameter.class);

        when(parameter.getType()).thenReturn((Class) Object.class);

        // when
        final boolean actual = binder.supports(parameter);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("Object 타입의 파라미터 값을 String 으로 변환 하여 반환 한다")
    @Test
    public void bind() throws Exception {
        // given
        final Object foo = "foo";

        // when
        final Object actual = binder.bind(foo, mock(Parameter.class));

        // then
        assertThat(actual).isEqualTo("foo");
    }

    @DisplayName("파라미터의 값이 null 이라면 빈 문자열을 반환 한다")
    @Test
    public void bindNull() throws Exception {
        // given
        final Object foo = null;

        // when
        final Object actual = binder.bind(foo, mock(Parameter.class));

        // then
        assertThat(actual).isEqualTo("");
    }
}
