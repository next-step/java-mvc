package com.interface21.webmvc.servlet.mvc.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.interface21.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestParamArgumentResolverTest {
    private static class ParameterProvider {
        public void method(@RequestParam long id, @RequestParam(required = false) String name) {}
    }

    private static Method getMethodFixture() {
        try {
            return ParameterProvider.class.getMethod("method", long.class, String.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static Parameter getParameterFixture(Method method, int index) {
        return method.getParameters()[index];
    }

    @DisplayName("@RequestParam 애노테이션이 있는 파라미터만 지원한다")
    @Test
    void supports() {
        Method method = getMethodFixture();
        Parameter parameter = getParameterFixture(method, 0);

        RequestParamArgumentResolver requestParamArgumentResolver = new RequestParamArgumentResolver();
        assertThat(requestParamArgumentResolver.supports(parameter)).isTrue();
    }

    @DisplayName("QueryString을 각 파라미터의 타입에 맞게 파싱한다.")
    @Test
    void resolve() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);
        when(request.getQueryString()).thenReturn("id=1&name=kim");
        when(request.getMethod()).thenReturn("GET");

        Method method = getMethodFixture();
        Parameter idParameter = getParameterFixture(method, 0);
        Parameter nameParameter = getParameterFixture(method, 1);

        RequestParamArgumentResolver requestParamArgumentResolver = new RequestParamArgumentResolver();
        Object resolvedId = requestParamArgumentResolver.resolve(idParameter, method, request, response);
        Object resolvedName = requestParamArgumentResolver.resolve(nameParameter, method, request, response);

        assertAll(
                () -> assertThat(resolvedId).isInstanceOf(Long.class),
                () -> assertThat((Long) resolvedId).isEqualTo(1),
                () -> assertThat(resolvedName).isInstanceOf(String.class),
                () -> assertThat((String) resolvedName).isEqualTo("kim")
        );
    }

    @DisplayName("@RequestParam이 선언되었으나 QueryString에 누락된 경우 예외를 발생시킨다. required=false인 값은 예외를 발생시키지 않는다.")
    @Test
    void resolveException() {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);
        when(request.getQueryString()).thenReturn("");
        when(request.getMethod()).thenReturn("GET");

        Method method = getMethodFixture();
        Parameter requiredTrue = getParameterFixture(method, 0);
        Parameter requiredFalse = getParameterFixture(method, 1);

        RequestParamArgumentResolver requestParamArgumentResolver = new RequestParamArgumentResolver();
        assertThatThrownBy(() -> requestParamArgumentResolver.resolve(requiredTrue, method, request, response))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("필수 값이 누락되었습니다. key=id");

        assertThatCode(() -> requestParamArgumentResolver.resolve(requiredFalse, method, request, response))
                .doesNotThrowAnyException();
    }
}