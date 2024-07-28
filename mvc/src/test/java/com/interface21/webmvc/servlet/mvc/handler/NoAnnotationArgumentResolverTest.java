package com.interface21.webmvc.servlet.mvc.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NoAnnotationArgumentResolverTest {
    private static class ParameterProvider {
        public void method(HttpServletRequest request, HttpServletResponse response) {}

        public void method2(int number) {}
    }

    private static Method getMethodFixture(String methodName, Class<?>... parameterTypes) {
        try {
            return ParameterProvider.class.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static Parameter getParameterFixture(Method method, int index) {
        return method.getParameters()[index];
    }

    @DisplayName("애노테이션이 없는 파라미터인 경우에만 지원한다.")
    @Test
    void supports() {
        Method method = getMethodFixture("method", HttpServletRequest.class, HttpServletResponse.class);
        Parameter parameter = getParameterFixture(method, 0);

        NoAnnotationArgumentResolver noAnnotationArgumentResolver = new NoAnnotationArgumentResolver();
        assertThat(noAnnotationArgumentResolver.supports(parameter)).isTrue();
    }

    @DisplayName("애노테이션이 없는 파라미터는 HttpServletRequest와 HttpServletResponse만 사용할 수 있다.")
    @Test
    void resolve() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Method method = getMethodFixture("method", HttpServletRequest.class, HttpServletResponse.class);
        Parameter requestParameter = getParameterFixture(method, 0);
        Parameter responseParameter = getParameterFixture(method, 1);

        NoAnnotationArgumentResolver noAnnotationArgumentResolver = new NoAnnotationArgumentResolver();
        Object resolvedRequest = noAnnotationArgumentResolver.resolve(requestParameter, method, request, response);
        Object resolvedResponse = noAnnotationArgumentResolver.resolve(responseParameter, method, request, response);

        assertAll(
                () -> assertThat(resolvedRequest).isInstanceOf(HttpServletRequest.class),
                () -> assertThat(resolvedResponse).isInstanceOf(HttpServletResponse.class)
        );
    }

    @DisplayName("애노테이션이 없는 파라미터가 HttpServletRequest와 HttpServletResponse가 아닌 경우 예외를 발생시킨다.")
    @Test
    void resolveException() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Method method = getMethodFixture("method2", int.class);
        Parameter parameter = getParameterFixture(method, 0);

        NoAnnotationArgumentResolver noAnnotationArgumentResolver = new NoAnnotationArgumentResolver();
        assertThatThrownBy(() -> noAnnotationArgumentResolver.resolve(parameter, method, request, response))
                .isInstanceOf(IllegalArgumentException.class);
    }
}