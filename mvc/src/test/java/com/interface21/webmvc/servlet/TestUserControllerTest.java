package com.interface21.webmvc.servlet;

import com.interface21.web.parameter.ParameterParser;
import com.interface21.web.parameter.ParameterParsers;
import com.interface21.web.parameter.ParameterTypedParser;
import com.interface21.web.support.TypedParser;
import com.interface21.web.support.TypedParsers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import samples.TestUser;

import java.lang.reflect.*;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestUserControllerTest {

    private ControllerHandlerMapping handlerMapping;
    private HttpRequestHandlers handlers = new HttpRequestHandlers();
    private ParameterParsers parsers = new ParameterParsers(new ParameterTypedParser(TestUser.class));

    @BeforeEach
    void setUp() {
        handlerMapping = new ControllerHandlerMapping(parsers, "samples");
        handlerMapping.initialize(handlers);
    }

    @Test
    void users_string() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getParameter("userId")).thenReturn("gugu");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getRequestURI()).thenReturn("/users/string");
        when(request.getMethod()).thenReturn("POST");

        final var handlerExecution = (HandlerExecution) handlers.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("userId")).isEqualTo("gugu");
        assertThat(modelAndView.getObject("password")).isEqualTo("password");
    }


    @Test
    void users_number() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getParameter("id")).thenReturn("123");
        when(request.getParameter("age")).thenReturn("22");
        when(request.getRequestURI()).thenReturn("/users/number");
        when(request.getMethod()).thenReturn("POST");

        final var handlerExecution = (HandlerExecution) handlers.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("id")).isEqualTo(123L);
        assertThat(modelAndView.getObject("age")).isEqualTo(22);
    }

    @Test
    void users_bean() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getParameter("userId")).thenReturn("woo-yu");
        when(request.getParameter("password")).thenReturn("pass");
        when(request.getParameter("age")).thenReturn("123");
        when(request.getRequestURI()).thenReturn("/users/bean");
        when(request.getMethod()).thenReturn("POST");

        final var handlerExecution = (HandlerExecution) handlers.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);
        final var result = (TestUser) modelAndView.getObject("testUser");

        assertThat(result.getUserId()).isEqualTo("woo-yu");
        assertThat(result.getPassword()).isEqualTo("pass");
        assertThat(result.getAge()).isEqualTo(123);
    }

    @Test
    void users_pathVariable() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/users/123");
        when(request.getMethod()).thenReturn("GET");

        final var handlerExecution = (HandlerExecution) handlers.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("id")).isEqualTo(123L);
    }
}