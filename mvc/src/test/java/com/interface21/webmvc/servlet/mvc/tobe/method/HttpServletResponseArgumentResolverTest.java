package com.interface21.webmvc.servlet.mvc.tobe.method;

import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

@DisplayName("HttpServletResponseArgumentResolver 클래스의")
class HttpServletResponseArgumentResolverTest {

    private final HandlerMethodArgumentResolver httpServletRequestArgumentResolver = new HttpServletResponseArgumentResolver();
    private final TestController testController = new TestController();

    @DisplayName("supportsParameter 메서드는 HttpServletResponse 타입을 지원한다.")
    @Test
    void supportsParameter() {
        // given
        MethodParameter methodParameter = getMethodParameter("testMethod");

        // when
        boolean result = httpServletRequestArgumentResolver.supportsParameter(methodParameter);

        // then
        assertTrue(result);
    }

    @DisplayName("resolveArgument 메서드는 HttpServletResponse 객체를 반환한다.")
    @Test
    void resolveArgument() {
        // given
        MethodParameter methodParameter = getMethodParameter("testMethod");
        HttpServletResponse httpServletRequest = mock(HttpServletResponse.class);

        // when
        Object result = httpServletRequestArgumentResolver.resolveArgument(methodParameter, null, httpServletRequest);

        // then
        assertEquals(httpServletRequest, result);
    }

    private MethodParameter getMethodParameter(String methodName) {
        try {
            Method method = testController.getClass().getDeclaredMethod(methodName, HttpServletResponse.class);
            Parameter parameter = method.getParameters()[0];
            return new MethodParameter(method, parameter);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static class TestController {
        public void testMethod(HttpServletResponse response) {
        }
    }
}
