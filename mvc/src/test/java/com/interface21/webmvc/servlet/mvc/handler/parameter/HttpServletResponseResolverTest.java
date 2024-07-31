package com.interface21.webmvc.servlet.mvc.handler.parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.TestController;

class HttpServletResponseResolverTest {

    @Test
    @DisplayName("supportsParameter는 HttpServletResponse 타입을 지원한다.")
    void name() throws NoSuchMethodException {
        // given
        HttpServletResponseResolver resolver = new HttpServletResponseResolver();
        Method method = TestController.class.getMethod("findUserId", HttpServletRequest.class, HttpServletResponse.class);
        Parameter parameter = method.getParameters()[1];

        // when
        boolean result = resolver.supportsParameter(parameter);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("resolveArgument는 HttpServletResponse 타입의 메서드 인자를 해결할 수 있다.")
    void testResolveArgument_WithHttpServletResponse() throws Exception {
        // given
        HttpServletResponseResolver resolver = new HttpServletResponseResolver();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Method method = TestController.class.getMethod("findUserId", HttpServletRequest.class, HttpServletResponse.class);
        Parameter parameter = method.getParameters()[1];
        MethodParameter methodParameter = new MethodParameter(method, parameter);

        // when
        Object result = resolver.resolveArgument(methodParameter, request, response);

        // then
        assertThat(result).isEqualTo(response);
    }
}