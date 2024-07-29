package com.interface21.webmvc.servlet.mvc.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.interface21.web.bind.annotation.RequestBody;
import com.interface21.web.http.MediaType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.DelegatingServletInputStream;
import samples.success.TestUser;

class RequestBodyArgumentResolverTest {
    private static class ParameterProvider {
        public void method(@RequestBody TestUser testUser) {}
    }

    private static Method getMethodFixture() {
        try {
            return ParameterProvider.class.getMethod("method", TestUser.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static Parameter getParameterFixture(Method method) {
        return method.getParameters()[0];
    }

    @DisplayName("@RequestBody 애노테이션이 있는 파라미터만 지원한다")
    @Test
    void supports() {
        Method method = getMethodFixture();
        Parameter parameter = getParameterFixture(method);

        RequestBodyArgumentResolver requestBodyArgumentResolver = new RequestBodyArgumentResolver();
        assertThat(requestBodyArgumentResolver.supports(parameter)).isTrue();
    }

    @DisplayName("Content-Type 헤더가 application/json이면 Request Body를 이용해 객체를 생성한다.")
    @Test
    void resolve() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/users-json");
        when(request.getHeader("Content-Type")).thenReturn(MediaType.APPLICATION_JSON_VALUE);
        when(request.getMethod()).thenReturn("POST");

        String body = "{\"userId\":\"gugu\",\"password\":\"123\",\"age\":\"19\"}";
        InputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(byteArrayInputStream));

        Method method = getMethodFixture();
        Parameter parameter = getParameterFixture(method);

        RequestBodyArgumentResolver requestBodyArgumentResolver = new RequestBodyArgumentResolver();
        Object actual = requestBodyArgumentResolver.resolve(parameter, method, request, response);

        assertAll(
                () -> assertThat(actual).isInstanceOf(TestUser.class),
                () -> assertThat(((TestUser) actual).getUserId()).isEqualTo("gugu"),
                () -> assertThat(((TestUser) actual).getPassword()).isEqualTo("123"),
                () -> assertThat(((TestUser) actual).getAge()).isEqualTo(19)
        );
    }

    @DisplayName("Content-Type이 application/json이 아닌 경우 예외를 발생시킨다.")
    @Test
    void resolveException() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/users-json");
        when(request.getHeader("Content-Type")).thenReturn(MediaType.FORM_URL_ENCODED);
        when(request.getMethod()).thenReturn("POST");

        String body = "{\"userId\":\"gugu\",\"password\":\"123\",\"age\":\"19\"}";
        InputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(byteArrayInputStream));

        Method method = getMethodFixture();
        Parameter parameter = getParameterFixture(method);

        RequestBodyArgumentResolver requestBodyArgumentResolver = new RequestBodyArgumentResolver();

        assertThatThrownBy(() -> requestBodyArgumentResolver.resolve(parameter, method, request, response))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Content-Type이 application/json이어야 합니다. contentType=application/x-www-form-urlencoded");
    }
}