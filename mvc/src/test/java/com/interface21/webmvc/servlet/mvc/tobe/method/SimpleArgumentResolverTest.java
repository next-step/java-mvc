package com.interface21.webmvc.servlet.mvc.tobe.method;

import jakarta.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("SimpleArgumentResolver 클래스는")
class SimpleArgumentResolverTest {

    @DisplayName("supportsParameter 메서드는 SimpleArgument 타입을 지원한다.")
    @Test
    void supportsParameter() {
        // given
        final var simpleArgumentResolver = new SimpleArgumentResolver();
        final var methodParameter = getMethodParameter("testMethod");

        // when
        final var result = simpleArgumentResolver.supportsParameter(methodParameter);

        // then
        assertTrue(result);
    }

    @DisplayName("resolveArgument 메서드는 SimpleArgument 값을 반환한다.")
    @Test
    void resolveArgument() {
        // given
        final var simpleArgumentResolver = new SimpleArgumentResolver();
        final var methodParameter = getMethodParameter("testMethod");
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getParameter(methodParameter.getParameterName())).thenReturn("1");

        // when
        final var result = simpleArgumentResolver.resolveArgument(methodParameter, httpServletRequest, null);

        // then
        assertEquals(1, result);
    }

    private MethodParameter getMethodParameter(String methodName) {
        try {
            final var method = TestController.class.getDeclaredMethod(methodName, int.class);
            final var parameter = method.getParameters()[0];
            return new MethodParameter(method, parameter);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static class TestController {
        public void testMethod(int userId) {
        }
    }

}
