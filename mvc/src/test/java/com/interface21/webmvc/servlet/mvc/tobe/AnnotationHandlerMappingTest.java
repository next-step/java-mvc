package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import samples.TestUser;

import static org.assertj.core.api.Assertions.assertThat;
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
    void get() throws Exception {
        final var request = mock(HttpServletRequest.class);
        when(request.getAttribute("id")).thenReturn("gugu");
        when(request.getRequestURI()).thenReturn("/get-test");
        when(request.getMethod()).thenReturn("GET");

        final var response = mock(HttpServletResponse.class);

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("id")).isEqualTo("gugu");
    }

    @Test
    void post() throws Exception {
        final var request = mock(HttpServletRequest.class);
        when(request.getAttribute("id")).thenReturn("gugu");
        when(request.getRequestURI()).thenReturn("/post-test");
        when(request.getMethod()).thenReturn("POST");

        final var response = mock(HttpServletResponse.class);

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("id")).isEqualTo("gugu");
    }

    @Test
    void testRecordIsNotScanned() {
        final var request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/record/get");
        when(request.getMethod()).thenReturn("GET");

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        assertThat(handlerExecution).isNull();
    }

    @Test
    void testAbstractClassIsNotScanned() {
        final var request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/abstract-clas/get");
        when(request.getMethod()).thenReturn("GET");

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        assertThat(handlerExecution).isNull();
    }

    @Test
    void testUsersCreateString() throws Exception {
        final var request = mock(HttpServletRequest.class);
        when(request.getParameter("userId")).thenReturn("gugu");
        when(request.getParameter("password")).thenReturn("passw0rd");
        when(request.getRequestURI()).thenReturn("/users-create-string");
        when(request.getMethod()).thenReturn("POST");

        final var response = mock(HttpServletResponse.class);

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("userId")).isEqualTo("gugu");
        assertThat(modelAndView.getObject("password")).isEqualTo("passw0rd");

    }

    @Test
    void testUsersCreateIntLong() throws Exception {
        final var request = mock(HttpServletRequest.class);
        when(request.getParameter("id")).thenReturn("123");
        when(request.getParameter("age")).thenReturn("45");
        when(request.getRequestURI()).thenReturn("/users-create-int-long");
        when(request.getMethod()).thenReturn("POST");

        final var response = mock(HttpServletResponse.class);

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("id")).isEqualTo(123L);
        assertThat(modelAndView.getObject("age")).isEqualTo(45);
    }

    @Test
    void testUsersCreateJavabean() throws Exception {
        final var request = mock(HttpServletRequest.class);
        when(request.getParameter("userId")).thenReturn("userid");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("age")).thenReturn("56");
        when(request.getRequestURI()).thenReturn("/users-create-javabean");
        when(request.getMethod()).thenReturn("POST");

        final var response = mock(HttpServletResponse.class);

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        TestUser testUser = (TestUser) modelAndView.getObject("testUser");
        assertThat(testUser.getUserId()).isEqualTo("userid");
        assertThat(testUser.getPassword()).isEqualTo("password");
        assertThat(testUser.getAge()).isEqualTo(56);
    }

    @Test
    void testUsersShowPathvariable() throws Exception {
        final var request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/users/123");
        when(request.getMethod()).thenReturn("GET");

        final var response = mock(HttpServletResponse.class);

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("id")).isEqualTo(123L);
    }

}
