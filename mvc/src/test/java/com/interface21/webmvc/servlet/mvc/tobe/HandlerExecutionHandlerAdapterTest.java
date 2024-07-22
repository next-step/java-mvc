package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.mvc.exception.HandlerAdapterException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class HandlerExecutionHandlerAdapterTest {
    @Test
    @DisplayName("HandlerExecution 의 구현체면 true 를 반환한다")
    void supportTestTrue() throws NoSuchMethodException {
        final HandlerExecutionHandlerAdapter handlerExecutionHandlerAdapter = new HandlerExecutionHandlerAdapter();
        final HandlerExecution handler = new HandlerExecution("test", String.class.getMethod("charAt", int.class), new ArgumentResolvers(""));

        assertThat(handlerExecutionHandlerAdapter.supports(handler)).isTrue();
    }

    @Test
    @DisplayName("handler 가 HandlerExecution 의 구현체면 handle 를 실행한다")
    void handleTest() throws Exception {
        final HandlerExecutionHandlerAdapter handlerExecutionHandlerAdapter = new HandlerExecutionHandlerAdapter();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HandlerExecution handlerExecution = mock(HandlerExecution.class);

        handlerExecutionHandlerAdapter.handle(request, response, handlerExecution);

        verify(handlerExecution, times(1)).handle(request, response);
    }

    @Test
    @DisplayName("handler 가 HandlerExecution 의 구현체가 아니면 예외를 던진다")
    void handleFailTest() {
        final HandlerExecutionHandlerAdapter handlerExecutionHandlerAdapter = new HandlerExecutionHandlerAdapter();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        assertThatThrownBy(() -> handlerExecutionHandlerAdapter.handle(request, response, "test"))
                .isInstanceOf(HandlerAdapterException.class)
                .hasMessageStartingWith("Not supported handler for");
    }
}
