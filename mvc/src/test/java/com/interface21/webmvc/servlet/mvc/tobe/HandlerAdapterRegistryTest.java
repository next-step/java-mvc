package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.asis.ControllerHandlerAdapter;
import com.interface21.webmvc.servlet.mvc.asis.ForwardController;
import com.interface21.webmvc.servlet.mvc.exception.HandlerAdapterException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class HandlerAdapterRegistryTest {

    @Test
    @DisplayName("등록된 HandlerAdapter 을 통해 ModelAndView 를 반환받을 수 있다")
    void handlerAdapterTest() throws Exception {
        final ControllerHandlerAdapter handlerAdapter = new ControllerHandlerAdapter();
        final HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry(handlerAdapter);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        final ModelAndView modelAndView = handlerAdapterRegistry.handle(request, response, new ForwardController("test"));

        assertThat(modelAndView).isEqualTo(ModelAndView.ofJspView("test"));
    }

    @Test
    @DisplayName("등록된 HandlerAdapter 가 존재하지 않으면 예외를 던진다")
    void handlerAdapterNotExistTest() {
        final ControllerHandlerAdapter handlerAdapter = new ControllerHandlerAdapter();
        final HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry(handlerAdapter);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        assertThatThrownBy(() -> handlerAdapterRegistry.handle(request, response, new HandlerExecution("test", String.class.getMethod("charAt", int.class))))
                .isInstanceOf(HandlerAdapterException.class)
                .hasMessageStartingWith("Unknown handler type");
    }
}
