package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class HandlerExecutionAdapterTest {

    @DisplayName("supports 메서드 테스트")
    @Test
    void testSupports() throws NoSuchMethodException {
        // given
        final HandlerExecutionAdapter handlerExecutionAdapter = new HandlerExecutionAdapter();
        final HandlerExecution handlerExecution = new HandlerExecution(0, Integer.class.getMethod("intValue"));

        // when
        final boolean supports = handlerExecutionAdapter.supports(handlerExecution);

        // then
        assertThat(supports).isTrue();
    }

    @DisplayName("handle 메서드 테스트")
    @Test
    void testHandle() throws Exception {
        // given
        final HandlerExecutionAdapter handlerExecutionAdapter = new HandlerExecutionAdapter();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HandlerExecution handlerExecution = mock(HandlerExecution.class);

        handlerExecutionAdapter.handle(request, response, handlerExecution);

        verify(handlerExecution, times(1)).handle(request, response);
    }

    @DisplayName("handle 메서드 예외 테스트")
    @Test
    void testHandleThrowsException() {
        // given
        final HandlerExecutionAdapter handlerExecutionAdapter = new HandlerExecutionAdapter();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final Object handler = new Object();

        // when // then
        assertThatThrownBy(() -> handlerExecutionAdapter.handle(request, response, handler))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("This handler is not supported by HandlerExecutionAdapter");
    }
}
