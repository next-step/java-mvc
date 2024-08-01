package com.interface21.webmvc.servlet.mvc.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ParameterBindersTest {

    private final ParameterBinders parameterBinders = new ParameterBinders();

    @DisplayName("지원하는 ParameterBinder 를 적절히 찾아 반환 한다")
    @Test
    public void getBinder() throws Exception {
        // given
        final Parameter mock = mock(Parameter.class);

        when(mock.getType()).thenReturn((Class) String.class);

        // when
        final ParameterBinder actual = parameterBinders.getBinder(mock);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("지원하는 ParameterBinder 가 없는 경우 예외를 반환 한다")
    @Test
    public void getBinderNotFound() throws Exception {
        // given
        final Parameter mock = mock(Parameter.class);

        when(mock.getType()).thenReturn((Class) Object.class);

        // when then
        assertThatThrownBy(() -> parameterBinders.getBinder(mock))
                .isInstanceOf(ParameterNotBindException.class)
                .hasMessage("No binder was found to support parameter type - [class java.lang.Object]");
    }
}
