package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("GET 요청을 잘 처리한다.")
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
    @DisplayName("POST 요청을 잘 처리한다.")
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
    @DisplayName("class 가 아닌 record 객체는 처리되지 않는다")
    void testRecordIsNotScanned() {
        final var request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/record/get");
        when(request.getMethod()).thenReturn("GET");

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        assertThat(handlerExecution).isNull();
    }

    @Test
    @DisplayName("추상 클래스에 등록된 URI 는 처리되지 않는다")
    void testAbstractClassIsNotScanned() {
        final var request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/abstract-class/get");
        when(request.getMethod()).thenReturn("GET");

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        assertThat(handlerExecution).isNull();
    }

    @Test
    @DisplayName("요청시 문자열 파라메터를 주입받을 수 있다")
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
    @DisplayName("primitive 타입 파라메터를 잘 주입받을 수 있다")
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
    @DisplayName("primitive 타입 파라메터를 잘 주입받을 수 있다")
    void testUsersPrimitives() throws Exception {
        final var request = mock(HttpServletRequest.class);
        when(request.getParameter("d")).thenReturn("123.45");
        when(request.getParameter("b")).thenReturn("42");
        when(request.getParameter("s")).thenReturn("3");
        when(request.getParameter("f")).thenReturn("4.4");
        when(request.getParameter("bool1")).thenReturn("true");
        when(request.getParameter("bool2")).thenReturn("false");
        when(request.getParameter("ch")).thenReturn("z");
        when(request.getParameter("chars")).thenReturn("abcde");
        when(request.getRequestURI()).thenReturn("/users-another-primitive-types");
        when(request.getMethod()).thenReturn("POST");

        final var response = mock(HttpServletResponse.class);

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("d")).isEqualTo(123.45);
        assertThat(modelAndView.getObject("b")).isEqualTo(Byte.parseByte("42"));
        assertThat(modelAndView.getObject("s")).isEqualTo(Short.parseShort("3"));
        assertThat(modelAndView.getObject("f")).isEqualTo(4.4f);
        assertThat(modelAndView.getObject("bool1")).isEqualTo(true);
        assertThat(modelAndView.getObject("bool2")).isEqualTo(false);
        assertThat(modelAndView.getObject("ch")).isEqualTo('z');
        assertThat(modelAndView.getObject("chars")).isEqualTo("abcde".toCharArray());
    }

    @Test
    @DisplayName("POST 요청 시 Body 를 Java bean 형태로 받을 수 있다")
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
    @DisplayName("@PathVariable 어노테이션이 있는 URI에서 {id} 를 잘 가져온다")
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
