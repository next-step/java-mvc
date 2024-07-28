package com.interface21.webmvc.servlet.mvc.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.interface21.web.http.MediaType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.DelegatingServletInputStream;
import samples.success.TestUser;

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
        when(request.getHeader("Content-Type")).thenReturn(MediaType.FORM_URL_ENCODED);
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

    @DisplayName("POST 요청이고, 콘텐츠 타입 헤더에 application/json이 있고, 파라미터에 @RequestBody 있고 객체가 있으면 RequestBody의 JSON을 파싱해서 객체를 만들어준다.")
    @Test
    void requestBodyPost() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/users-json");
        when(request.getHeader("Content-Type")).thenReturn(MediaType.APPLICATION_JSON_VALUE);
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

    @DisplayName("요청과 상관 없이 @PathVariable은 url의 {} 에 작성된 이름과 동일하면 파싱된다.")
    @ParameterizedTest(name = "method = {0}")
    @ValueSource(strings = {"GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE"})
    void pathVariable1(String method) throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/users/1");
        when(request.getMethod()).thenReturn(method);

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertAll(
                () -> assertThat(modelAndView.getObject("id")).isEqualTo(1L)
        );
    }

    @DisplayName("요청에 해당하는 핸들러가 있을 경우 이를 반환한다.")
    @Test
    void getHandler() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/get-test");
        when(request.getAttribute("id")).thenReturn("id");
        when(request.getMethod()).thenReturn("GET");

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("id")).isEqualTo("id");
    }

    @DisplayName("Url이 완벽하게 일치하지 않고 패턴만 일치해도 핸들러를 찾는다.")
    @Test
    void getHandlerWithUrlPatternMatched() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/users/1");
        when(request.getMethod()).thenReturn("GET");

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("id")).isEqualTo(1L);
    }

    @DisplayName("요청에 해당하는 핸들러가 없을 경우 null 반환한다.")
    @Test
    void getHandlerNull() {
        final var request = mock(HttpServletRequest.class);

        when(request.getRequestURI()).thenReturn("/no");
        when(request.getMethod()).thenReturn("GET");

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);

        assertThat(handlerExecution).isNull();
    }
}
