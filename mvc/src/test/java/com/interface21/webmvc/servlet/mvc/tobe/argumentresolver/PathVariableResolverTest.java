package com.interface21.webmvc.servlet.mvc.tobe.argumentresolver;

import com.interface21.web.bind.annotation.PathVariable;
import com.interface21.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class PathVariableResolverTest {

    @DisplayName("@PathVariable 어노테이션이 붙은 파라미터의 값을 가져온다.")
    @Test
    void resolveArgumentTest() throws Exception {
        // given
        final HandlerMethodArgumentResolver resolver = new PathVariableResolver();
        final Method method = TestClass.class.getDeclaredMethod("handle", String.class);
        final Parameter parameter = method.getParameters()[0];
        final HttpServletRequest request = mock(HttpServletRequest.class);
        given(request.getRequestURI()).willReturn("/hello/test");

        // when
        final Object result = resolver.resolveArgument(parameter, method, request, null);

        // then
        assertThat(result).isEqualTo("test");
    }


    static class TestClass {

        @RequestMapping("/hello/{name}")
        void handle(@PathVariable("name") final String name) {
        }
    }
}
