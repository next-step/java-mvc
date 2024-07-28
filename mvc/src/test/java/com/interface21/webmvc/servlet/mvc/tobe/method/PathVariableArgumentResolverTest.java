package com.interface21.webmvc.servlet.mvc.tobe.method;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("PathVariableArgumentResolver 클래스의")
class PathVariableArgumentResolverTest {

    private final PathVariableArgumentResolver pathVariableArgumentResolver = new PathVariableArgumentResolver();
    private final TestController testController = new TestController();

    @DisplayName("supportsParameter 메서드는 PathVariable 어노테이션이 붙은 파라미터를 지원한다.")
    @Test
    void supportsParameter() {
        // given
        MethodParameter methodParameter = getMethodParameter("testMethod");

        // when
        boolean actual = pathVariableArgumentResolver.supportsParameter(methodParameter);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("resolveArgument 메서드는 PathVariable 어노테이션의 value 속성값에 해당하는 URI 값을 반환한다.")
    @Test
    void resolveArgument() {
        // given
        MethodParameter methodParameter = getMethodParameter("testMethod");
        String uri = "/users/1";
        HttpServletRequest webRequest = mock(HttpServletRequest.class);
        when(webRequest.getRequestURI()).thenReturn(uri);
        when(webRequest.getMethod()).thenReturn("GET");

        // when
        Object actual = pathVariableArgumentResolver.resolveArgument(methodParameter, webRequest, null);

        // then
        assertThat(actual).isEqualTo(1);
    }

    private MethodParameter getMethodParameter(String methodName) {
        try {
            Method method = testController.getClass().getDeclaredMethod(methodName, int.class);
            Parameter parameter = method.getParameters()[0];
            return new MethodParameter(method, parameter);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static class TestController {
        @RequestMapping("/users/{id}")
        public void testMethod(@PathVariable int id) {
        }
    }
}
