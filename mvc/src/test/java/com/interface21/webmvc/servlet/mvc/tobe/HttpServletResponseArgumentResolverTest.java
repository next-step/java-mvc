package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;

class HttpServletResponseArgumentResolverTest {
    @Test
    @DisplayName("HttpServletResponse 파라미터를 지원한다")
    void supportsParameterTest() throws NoSuchMethodException {
        final HandlerMethodArgumentResolver resolver = new HttpServletResponseArgumentResolver();
        final Method testMethod = HttpServletResponseArgumentResolverTest.TestClass.class.getDeclaredMethod("testMethod", HttpServletResponse.class);
        final Parameter parameter = testMethod.getParameters()[0];
        final MethodParameter methodParameter = new MethodParameter(parameter);

        final boolean supports = resolver.supportsParameter(methodParameter);

        assertThat(supports).isTrue();
    }

    @Test
    @DisplayName("HttpServletResponse 파라미터를 동일하게 반환한다")
    void resolveArgumentTest() throws Exception {
        final HandlerMethodArgumentResolver resolver = new HttpServletResponseArgumentResolver();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final Method testMethod = HttpServletResponseArgumentResolverTest.TestClass.class.getDeclaredMethod("testMethod", HttpServletResponse.class);
        final Parameter parameter = testMethod.getParameters()[0];
        final MethodParameter methodParameter = new MethodParameter(parameter);

        final Object resolvedArgument = resolver.resolveArgument(methodParameter, null, response);

        assertThat(resolvedArgument).isSameAs(response);
    }

    static class TestClass {
        public void testMethod(final HttpServletResponse response) {
        }
    }
}
