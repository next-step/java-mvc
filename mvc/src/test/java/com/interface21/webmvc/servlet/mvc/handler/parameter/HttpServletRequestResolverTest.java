package com.interface21.webmvc.servlet.mvc.handler.parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.TestController;

class HttpServletRequestResolverTest {

    @Test
    @DisplayName("HttpServletRequestResolver는 HttpServletRequest 타입을 지원한다.")
    void support() throws NoSuchMethodException {
        // given
        HttpServletRequestResolver resolver = new HttpServletRequestResolver();
        Method method = TestController.class.getMethod("findUserId", HttpServletRequest.class, HttpServletResponse.class);
        Parameter parameter = method.getParameters()[0];

        // when
        boolean result = resolver.supportsParameter(parameter);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("HttpServletRequestResolver는 HttpServletRequest 타입의 메서드 인자를 해결할 수 있다.")
    public void testResolveArgument_WithHttpServletRequest() throws Exception {
        // Arrange
        HttpServletRequestResolver resolver = new HttpServletRequestResolver();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Method method = TestController.class.getMethod("findUserId", HttpServletRequest.class, HttpServletResponse.class);
        Parameter parameter = method.getParameters()[0];
        MethodParameter methodParameter = new MethodParameter(method, parameter);

        // Act
        Object result = resolver.resolveArgument(methodParameter, request, response);

        // Assert
        assertThat(result).isEqualTo(request);
    }
}