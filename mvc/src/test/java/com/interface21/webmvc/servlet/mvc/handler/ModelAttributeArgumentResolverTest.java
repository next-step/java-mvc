package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.web.bind.annotation.ModelAttribute;
import com.interface21.web.http.MediaType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.DelegatingServletInputStream;
import samples.TestUser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ModelAttributeArgumentResolverTest {
    private static class ParameterProvider {
        public void method(@ModelAttribute TestUser testUser) {}
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

    @DisplayName("ModelAttributeArgumentResolver는 @ModelAttribute 애노테이션이 달려있는 파라미터를 지원한다.")
    @Test
    void supports() {
        Method method = getMethodFixture();
        Parameter parameter = getParameterFixture(method);

        ModelAttributeArgumentResolver modelAttributeArgumentResolver = new ModelAttributeArgumentResolver();
        assertThat(modelAttributeArgumentResolver.supports(parameter)).isTrue();
    }

    @DisplayName("요청이 POST이고 Content-Type 헤더가 application/x-www-form-urlencoded이면 Request body를 파싱해서 객체를 만든다.")
    @Test
    void resolvePostAndFormData() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Content-Type")).thenReturn(MediaType.FORM_URL_ENCODED.getValue());
        when(request.getMethod()).thenReturn("POST");

        String body = "userId=gugu&password=123&age=19";
        InputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(byteArrayInputStream));

        HttpServletResponse response = mock(HttpServletResponse.class);

        Method method = getMethodFixture();
        Parameter parameter = getParameterFixture(method);

        ModelAttributeArgumentResolver modelAttributeArgumentResolver = new ModelAttributeArgumentResolver();
        Object actual = modelAttributeArgumentResolver.resolve(parameter, method, request, response);

        assertThat(actual).isInstanceOf(TestUser.class);

        TestUser testUser = (TestUser) actual;
        assertAll(
                () -> assertThat(testUser.getUserId()).isEqualTo("gugu"),
                () -> assertThat(testUser.getPassword()).isEqualTo("123"),
                () -> assertThat(testUser.getAge()).isEqualTo(19)
        );
    }

    @DisplayName("요청이 POST & Content-Type 헤더가 application/x-www-form-urlencoded가 아니면 QueryString을 파싱해 객체를 만든다.")
    @ParameterizedTest(name = "method={0}")
    @ValueSource(strings = {"GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE"})
    void resolveNoPostAndFormData(String httpMethod) throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getQueryString()).thenReturn("userId=gugu&password=123&age=19");
        when(request.getMethod()).thenReturn(httpMethod);
        when(request.getHeader("Content-Type")).thenReturn("text/pain");

        HttpServletResponse response = mock(HttpServletResponse.class);

        Method method = getMethodFixture();
        Parameter parameter = getParameterFixture(method);

        ModelAttributeArgumentResolver modelAttributeArgumentResolver = new ModelAttributeArgumentResolver();
        Object actual = modelAttributeArgumentResolver.resolve(parameter, method, request, response);

        assertThat(actual).isInstanceOf(TestUser.class);

        TestUser testUser = (TestUser) actual;
        assertAll(
                () -> assertThat(testUser.getUserId()).isEqualTo("gugu"),
                () -> assertThat(testUser.getPassword()).isEqualTo("123"),
                () -> assertThat(testUser.getAge()).isEqualTo(19)
        );
    }

    @DisplayName("객체의 필드명이 파싱한 값의 key와 일치하지 않으면 예외를 발생시킨다.")
    @Test
    void resolveException() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getQueryString()).thenReturn("newUserId=gugu&password=123&age=19");
        when(request.getMethod()).thenReturn("GET");

        HttpServletResponse response = mock(HttpServletResponse.class);

        Method method = getMethodFixture();
        Parameter parameter = getParameterFixture(method);

        ModelAttributeArgumentResolver modelAttributeArgumentResolver = new ModelAttributeArgumentResolver();
        assertThatThrownBy(() -> modelAttributeArgumentResolver.resolve(parameter, method, request, response))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("newUserId에 해당하는 객체 필드명이 없습니다.");
    }
}