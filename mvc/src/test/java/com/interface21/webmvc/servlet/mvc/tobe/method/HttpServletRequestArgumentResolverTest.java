package com.interface21.webmvc.servlet.mvc.tobe.method;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
@DisplayName("HttpServletRequestArgumentResolver 클래스의")
class HttpServletRequestArgumentResolverTest {

    private final HttpServletRequestArgumentResolver httpServletRequestArgumentResolver = new HttpServletRequestArgumentResolver();
    private final TestController testController = new TestController();

    @DisplayName("supportsParameter 메서드는 HttpServletRequest 타입을 지원한다.")
    @Test
    void supportsParameter() {
        // given
        MethodParameter methodParameter = getMethodParameter("testMethod");

        // when
        boolean result = httpServletRequestArgumentResolver.supportsParameter(methodParameter);

        // then
        assertTrue(result);
    }

    @DisplayName("resolveArgument 메서드는 HttpServletRequest 객체를 반환한다.")
    @Test
    void resolveArgument() {
        // given
        MethodParameter methodParameter = getMethodParameter("testMethod");
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        // when
        Object result = httpServletRequestArgumentResolver.resolveArgument(methodParameter, httpServletRequest, null);

        // then
        assertEquals(httpServletRequest, result);
    }

    private MethodParameter getMethodParameter(String methodName) {
        try {
            Method method = testController.getClass().getDeclaredMethod(methodName, HttpServletRequest.class);
            Parameter parameter = method.getParameters()[0];
            return new MethodParameter(method, parameter);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static class TestController {
        public void testMethod(HttpServletRequest request) {
        }
    }
}
