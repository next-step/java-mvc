package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnnotationHandlerMappingTest {

    private AnnotationHandlerMapping handlerMapping;

    @BeforeEach
    void setUp() {
        handlerMapping = new AnnotationHandlerMapping("samples");
        handlerMapping.initialize();
    }

    @Test
    @DisplayName("get 요청을 처리할 수 있다")
    void get() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getAttribute("id")).thenReturn("gugu");
        when(request.getRequestURI()).thenReturn("/get-test");
        when(request.getMethod()).thenReturn("GET");

        final var handlerExecution = handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("id")).isEqualTo("gugu");
    }

    @Test
    @DisplayName("post 요청을 처리할 수 있다")
    void post() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getAttribute("id")).thenReturn("gugu");
        when(request.getRequestURI()).thenReturn("/post-test");
        when(request.getMethod()).thenReturn("POST");

        final var handlerExecution = handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("id")).isEqualTo("gugu");
    }

    @ParameterizedTest
    @EnumSource
    @DisplayName("method 선언이 안되어있으면 모든 HTTP method 를 지원한다")
    void noMethod(final RequestMethod requestMethod) throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getAttribute("id")).thenReturn("gugu");
        when(request.getRequestURI()).thenReturn("/no-method-test");
        when(request.getMethod()).thenReturn(requestMethod.name());

        final var handlerExecution = handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("id")).isEqualTo("gugu");
    }

    @Test
    @DisplayName("String 파라미터를 바인딩한 Controller 메서드를 호출할 수 있다")
    void createStringArgumentsHandleTest() throws Exception {
        final var request = new MockHttpServletRequest("POST", "/users-string");
        final var response = new MockHttpServletResponse();
        request.addParameter("userId", "jongmin");
        request.addParameter("password", "1234");

        final var handlerExecution = handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertSoftly(softly -> {
            softly.assertThat(modelAndView.getObject("userId")).isEqualTo("jongmin");
            softly.assertThat(modelAndView.getObject("password")).isEqualTo("1234");
        });
    }

    @Test
    @DisplayName("long 과 int 파라미터를 바인딩한 Controller 메서드를 호출할 수 있다")
    void createLongIntArgumentsHandleTest() throws Exception {
        final var request = new MockHttpServletRequest("POST", "/users-int-long");
        final var response = new MockHttpServletResponse();

        request.addParameter("id", "1");
        request.addParameter("age", "30");

        final var handlerExecution = handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertSoftly(softly -> {
            softly.assertThat(modelAndView.getObject("id")).isEqualTo(1L);
            softly.assertThat(modelAndView.getObject("age")).isEqualTo(30);
        });
    }

    @Test
    @DisplayName("javabean 타입의 파라미터를 바인딩한 Controller 메서드를 호출할 수 있다")
    void createJavabeanArgumentsHandleTest() throws Exception {
        final var request = new MockHttpServletRequest("POST", "/users-javabean");
        final var response = new MockHttpServletResponse();

        request.addParameter("userId", "1");
        request.addParameter("password", "1234");
        request.addParameter("age", "31");

        final var handlerExecution = handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("testUser"))
                .extracting("userId", "password", "age")
                .containsExactly("1", "1234", 31);
    }

    @Test
    @DisplayName("pathVariable 타입의 파라미터를 바인딩한 Controller 메서드를 호출할 수 있다")
    void createPathVariableArgumentsHandleTest() throws Exception {
        final var request = new MockHttpServletRequest("GET", "/users/1");
        final var response = new MockHttpServletResponse();

        final var handlerExecution = handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("id")).isEqualTo(1L);
    }
}
