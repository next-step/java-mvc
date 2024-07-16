package com.interface21.webmvc.servlet.mvc.asis;

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

class ControllerHandlerAdapterTest {
    @Test
    @DisplayName("Controller 의 구현체면 true 를 반환한다")
    void supportTestTrue() {
        final ControllerHandlerAdapter controllerHandlerAdapter = new ControllerHandlerAdapter();
        final ForwardController handler = new ForwardController("test");

        assertThat(controllerHandlerAdapter.supports(handler)).isTrue();
    }

    @Test
    @DisplayName("handler 가 Controller 의 구현체면 execute 를 실행한다")
    void handleTest() throws Exception {
        final ControllerHandlerAdapter controllerHandlerAdapter = new ControllerHandlerAdapter();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final Controller controller = mock(Controller.class);

        controllerHandlerAdapter.handle(request, response, controller);

        verify(controller, times(1)).execute(request, response);
    }

    @Test
    @DisplayName("handler 가 Controller 의 구현체가 아니면 예외를 던진다")
    void handleFailTest() {
        final ControllerHandlerAdapter controllerHandlerAdapter = new ControllerHandlerAdapter();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        assertThatThrownBy(() -> controllerHandlerAdapter.handle(request, response, "test"))
                .isInstanceOf(HandlerAdapterException.class)
                .hasMessageStartingWith("Not supported handler for");
    }
}
