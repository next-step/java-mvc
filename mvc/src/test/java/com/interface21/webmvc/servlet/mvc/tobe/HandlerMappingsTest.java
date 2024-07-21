package com.interface21.webmvc.servlet.mvc.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HandlerMappingsTest {
    @DisplayName("요청에 해당하는 Handler를 찾아서 반환한다.")
    @Test
    void test() {
        HandlerMappings handlerMappings = HandlerMappings.create();
        handlerMappings.add(new AnnotationHandlerMapping("samples.success"));
        handlerMappings.initialize();

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/get-test");
        when(request.getMethod()).thenReturn("GET");

        Object handler = handlerMappings.getHandler(request);

        assertThat(handler).isInstanceOf(HandlerExecution.class);
    }

    @DisplayName("요청에 해당하는 Handler가 없을 경우 예외를 발생시킨다.")
    @Test
    void test2() {
        HandlerMappings handlerMappings = HandlerMappings.create();
        handlerMappings.add(new AnnotationHandlerMapping("samples.success"));
        handlerMappings.initialize();

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/없어요");
        when(request.getMethod()).thenReturn("GET");

        assertThatThrownBy(() -> handlerMappings.getHandler(request))
                .isInstanceOf(HandlerNotFoundException.class);
    }
}