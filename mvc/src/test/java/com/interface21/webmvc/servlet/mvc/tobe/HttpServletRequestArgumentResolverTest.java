package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;

class HttpServletRequestArgumentResolverTest {
    @Test
    @DisplayName("HttpServletRequest 파라미터를 지원한다")
    void supportsParameterTest() throws NoSuchMethodException {
        final HandlerMethodArgumentResolver resolver = new HttpServletRequestArgumentResolver();
        final Method testMethod = TestClass.class.getDeclaredMethod("testMethod", HttpServletRequest.class);
        final Parameter parameter = testMethod.getParameters()[0];
        final MethodParameter methodParameter = new MethodParameter(parameter);

        final boolean supports = resolver.supportsParameter(methodParameter);

        assertThat(supports).isTrue();
    }

    @Test
    @DisplayName("HttpServletRequest 파라미터를 동일하게 반환한다")
    void resolveArgumentTest() throws Exception {
        final HandlerMethodArgumentResolver resolver = new HttpServletRequestArgumentResolver();
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final Method testMethod = TestClass.class.getDeclaredMethod("testMethod", HttpServletRequest.class);
        final Parameter parameter = testMethod.getParameters()[0];
        final MethodParameter methodParameter = new MethodParameter(parameter);

        final Object resolvedArgument = resolver.resolveArgument(methodParameter, request, null);

        assertThat(resolvedArgument).isSameAs(request);
    }

    static class TestClass {
        public void testMethod(final HttpServletRequest request) {
        }
    }
}
