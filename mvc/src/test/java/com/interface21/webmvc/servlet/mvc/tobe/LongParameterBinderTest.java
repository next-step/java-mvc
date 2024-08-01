package com.interface21.webmvc.servlet.mvc.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LongParameterBinderTest {

    private final LongParameterBinder binder = new LongParameterBinder();

    @DisplayName("support 매서드의 파라미터로 long 또는 Long 타입의 Parameter 를 받으면 true 를 반환 한다")
    @Test
    public void support() throws Exception {
        // given
        final Parameter parameter1 = mock(Parameter.class);
        final Parameter parameter2 = mock(Parameter.class);

        when(parameter1.getType()).thenReturn((Class) long.class);
        when(parameter2.getType()).thenReturn((Class) Long.class);

        // when
        final boolean actual1 = binder.supports(parameter1);
        final boolean actual2 = binder.supports(parameter2);

        // then
        assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isTrue()
        );
    }

    @DisplayName("support 매서드의 파라미터로 long 또는 Long 가 아닌 타입의 Parameter 를 받으면 false 를 반환 한다")
    @Test
    public void notSupport() throws Exception {
        // given
        final Parameter parameter = mock(Parameter.class);

        when(parameter.getType()).thenReturn((Class) String.class);

        // when
        final boolean actual = binder.supports(parameter);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("Object 타입의 파라미터 값을 Integer 로 변환 하여 반환 한다")
    @Test
    public void bind() throws Exception {
        // given
        final Object foo = "100";

        // when
        final Object actual = binder.bind(foo, mock(Parameter.class));

        // then
        assertThat(actual).isEqualTo(100L);
    }
}
