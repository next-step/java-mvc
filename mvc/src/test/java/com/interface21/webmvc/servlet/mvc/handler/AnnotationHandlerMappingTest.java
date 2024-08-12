package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.web.http.MediaType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.DelegatingServletInputStream;
import samples.TestUser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
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

    @DisplayName("GET 요청일 때, 파라미터에 @RequestParam이 있으면 QueryString을 각 파라미터의 타입에 맞게 파싱한다.")
    @Test
    void requestParamGetString() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getAttribute("userId")).thenReturn("gugu");
        when(request.getRequestURI()).thenReturn("/users-string");
        when(request.getQueryString()).thenReturn("userId=gugu&password=password");
        when(request.getMethod()).thenReturn("GET");

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertAll(
                () -> assertThat(modelAndView.getObject("userId")).isEqualTo("gugu"),
                () -> assertThat(modelAndView.getObject("password")).isEqualTo("password")
        );
    }

    @DisplayName("GET 요청일 때, 파라미터에 @RequestParam이 있으면 QueryString을 각 파라미터의 타입에 맞게 파싱한다.")
    @Test
    void requestParamGetLongAndInt() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/users-number");
        when(request.getQueryString()).thenReturn("id=1&age=31");
        when(request.getMethod()).thenReturn("GET");

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertAll(
                () -> assertThat(modelAndView.getObject("id")).isEqualTo(1L),
                () -> assertThat(modelAndView.getObject("age")).isEqualTo(31)
        );
    }

    @DisplayName("요청과 상관 없이 @PathVariable은 url의 {} 에 작성된 이름과 동일하면 파싱된다.")
    @Test
    void pathVariable() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/users/1");
        when(request.getMethod()).thenReturn("GET");

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertAll(
                () -> assertThat(modelAndView.getObject("id")).isEqualTo(1L)
        );
    }

    @DisplayName("POST 요청이고, 콘텐츠 타입 헤더에 application/json이 있고, 파라미터에 @RequestBody 있고 객체가 있으면 RequestBody의 JSON을 파싱해서 객체를 만들어준다.")
    @Test
    void requestBodyPost() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/users-json");
        when(request.getHeader("Content-Type")).thenReturn(MediaType.APPLICATION_JSON_UTF8_VALUE.getValue());
        when(request.getMethod()).thenReturn("POST");

        String body = "{\"userId\":\"gugu\",\"password\":\"123\",\"age\":\"19\"}";
        InputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(byteArrayInputStream));

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        TestUser testUser = (TestUser) modelAndView.getObject("testUser");
        assertAll(
                () -> assertThat(testUser.getUserId()).isEqualTo("gugu"),
                () -> assertThat(testUser.getPassword()).isEqualTo("123"),
                () -> assertThat(testUser.getAge()).isEqualTo(19)
        );
    }

    @DisplayName("GET 요청일 때, 파라미터에 @ModelAttribute가 있고 객체가 있으면 QueryString을 파싱해서 객체를 만들어준다.")
    @Test
    void modelAttributeGet() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/users-model");
        when(request.getQueryString()).thenReturn("userId=gugu&password=123&age=19");
        when(request.getMethod()).thenReturn("GET");

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        TestUser testUser = (TestUser) modelAndView.getObject("testUser");
        assertAll(
                () -> assertThat(testUser.getUserId()).isEqualTo("gugu"),
                () -> assertThat(testUser.getPassword()).isEqualTo("123"),
                () -> assertThat(testUser.getAge()).isEqualTo(19)
        );
    }

    @DisplayName("POST 요청일 때, 파라미터에 @ModelAttribute가 있고 객체가 있으면 RequestBody를 파싱해서 객체를 만들어준다.")
    @Test
    void modelAttributePost() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/users-model");
        when(request.getHeader("Content-Type")).thenReturn(MediaType.FORM_URL_ENCODED.getValue());
        when(request.getMethod()).thenReturn("POST");

        String body = "userId=gugu&password=123&age=19";
        InputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(byteArrayInputStream));

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        TestUser testUser = (TestUser) modelAndView.getObject("testUser");
        assertAll(
                () -> assertThat(testUser.getUserId()).isEqualTo("gugu"),
                () -> assertThat(testUser.getPassword()).isEqualTo("123"),
                () -> assertThat(testUser.getAge()).isEqualTo(19)
        );
    }
}
