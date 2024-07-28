package com.interface21.webmvc.servlet.mvc.tobe.method;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import samples.TestUser;

@DisplayName("ObjectArgumentResolver 클래스는")
class ObjectArgumentResolverTest {

    private final HandlerMethodArgumentResolver objectArgumentResolver = new ObjectArgumentResolver();
    private final TestController testController = new TestController();

    @DisplayName("supportsParameter 메서드는 Object 타입을 지원한다.")
    @Test
    void supportsParameter() {
        // given
        MethodParameter methodParameter = getMethodParameter("testMethod");

        // when
        boolean result = objectArgumentResolver.supportsParameter(methodParameter);

        // then
        assertTrue(result);
    }

    @DisplayName("resolveArgument 메서드는 TestUser 객체를 반환한다.")
    @Test
    void resolveArgument() {
        // given
        MethodParameter methodParameter = getMethodParameter("testMethod");
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        when(httpServletRequest.getParameter("userId")).thenReturn("gugu");
        when(httpServletRequest.getParameter("password")).thenReturn("password");
        when(httpServletRequest.getParameter("age")).thenReturn("20");


        // when
        Object result = objectArgumentResolver.resolveArgument(methodParameter, httpServletRequest, null);

        // then
        TestUser testUser = (TestUser) result;
        assertThat(testUser.getUserId()).isEqualTo("gugu");
        assertThat(testUser.getPassword()).isEqualTo("password");
        assertThat(testUser.getAge()).isEqualTo(20);
    }


    private MethodParameter getMethodParameter(String methodName) {
        try {
            Method method = testController.getClass().getDeclaredMethod(methodName, TestUser.class);
            Parameter parameter = method.getParameters()[0];
            return new MethodParameter(method, parameter);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static class TestController {
        public void testMethod(TestUser testUser) {
        }
    }
}
