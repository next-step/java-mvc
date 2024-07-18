package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.mvc.exception.HandlerMappingException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HandlerMappingRegistryTest {

    @Test
    @DisplayName("등록된 handlerMapping 을 통해 handler 를 반환받을 수 있다")
    void handlerMappingTest() {
        final AnnotationHandlerMapping handlerMapping = new AnnotationHandlerMapping("samples");
        final HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry(handlerMapping);
        final HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getRequestURI()).thenReturn("/get-test");
        when(request.getMethod()).thenReturn("GET");
        handlerMappingRegistry.initialize();
        final Object handler = handlerMappingRegistry.getHandler(request);

        assertThat(handler).isInstanceOf(HandlerExecution.class);
    }

    @Test
    @DisplayName("handler 가 initialize 되어있지 않으면 예외를 던진다")
    void handlerMappingNotInitializedTest() {
        final AnnotationHandlerMapping handlerMapping = new AnnotationHandlerMapping("samples");
        final HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry(handlerMapping);
        final HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getRequestURI()).thenReturn("/get-test");
        when(request.getMethod()).thenReturn("GET");

        assertThatThrownBy(() -> handlerMappingRegistry.getHandler(request))
                .isInstanceOf(HandlerMappingException.class)
                .hasMessageStartingWith("No handler found");
    }

    @Test
    @DisplayName("등록된 handler 가 존재하지 않으면 예외를 던진다")
    void handlerMappingNotExistTest() {
        final AnnotationHandlerMapping handlerMapping = new AnnotationHandlerMapping("samples");
        handlerMapping.initialize();
        final HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry(handlerMapping);
        final HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getRequestURI()).thenReturn("/get-test");
        when(request.getMethod()).thenReturn("POST");

        assertThatThrownBy(() -> handlerMappingRegistry.getHandler(request))
                .isInstanceOf(HandlerMappingException.class)
                .hasMessageStartingWith("No handler found");
    }
}
