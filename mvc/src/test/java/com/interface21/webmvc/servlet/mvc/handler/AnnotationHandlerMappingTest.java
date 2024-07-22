package com.interface21.webmvc.servlet.mvc.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AnnotationHandlerMappingTest {

    private AnnotationHandlerMapping handlerMapping;

    @BeforeEach
    void setUp() {
        handlerMapping = new AnnotationHandlerMapping("samples.success");
        handlerMapping.initialize();
    }

    @Test
    void get() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getAttribute("id")).thenReturn("gugu");
        when(request.getRequestURI()).thenReturn("/get-test");
        when(request.getMethod()).thenReturn("GET");

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("id")).isEqualTo("gugu");
    }

    @Test
    void post() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getAttribute("id")).thenReturn("gugu");
        when(request.getRequestURI()).thenReturn("/post-test");
        when(request.getMethod()).thenReturn("POST");

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("id")).isEqualTo("gugu");
    }

    @DisplayName("클래스 수준에 선언된 @RequestMapping의 uri는 prefix로 사용된다.")
    @ParameterizedTest(name = "method = {0}")
    @ValueSource(strings = {"GET", "POST"})
    void withClassRequestMapping(String method) throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getAttribute("name")).thenReturn("kim");
        when(request.getRequestURI()).thenReturn("/prefix");
        when(request.getMethod()).thenReturn(method);

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("name")).isEqualTo("kim");
    }

    @DisplayName("@Controller 애노테이션이 없는 경우 등록되지 않는다.")
    @Test
    void withNoControllerAnnotation() throws Exception {
        final var request = mock(HttpServletRequest.class);

        when(request.getRequestURI()).thenReturn("/test-no-annotation");
        when(request.getMethod()).thenReturn("POST");

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        assertThat(handlerExecution).isNull();
    }

    @DisplayName("HTTP 메서드가 지정되지 않으면 모든 메서드가 타겟이다.")
    @ParameterizedTest(name = "method = {0}")
    @ValueSource(strings = {"GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE"})
    void withNoHttpMethod(String method) {
        final var request = mock(HttpServletRequest.class);

        when(request.getRequestURI()).thenReturn("/with-no-http-method");
        when(request.getMethod()).thenReturn(method);

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        assertThat(handlerExecution).isNotNull();
    }

    @DisplayName("기본 생성자가 없는 Controller가 있을 경우 예외를 발생시킨다")
    @Test
    void with() {
        handlerMapping = new AnnotationHandlerMapping("samples.fail");
        assertThatThrownBy(() -> handlerMapping.initialize())
                .isInstanceOf(ControllerDefaultConstructorNotFoundException.class);
    }
}
