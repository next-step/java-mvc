package com.interface21.webmvc.servlet.mvc.tobe.parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.TestController;

class HttpServletResponseResolverTest {
    @Test
    @DisplayName("HttpServletResponse 지원하는지 확인한다.")
    void support(){
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        Method method = Arrays.stream(TestController.class.getDeclaredMethods())
            .filter(methods -> methods.getName() == "findUserId")
            .findFirst()
            .orElseThrow(RuntimeException::new);

        Parameter parameter = method.getParameters()[1];
        ParameterResolver resolver = new HttpServletResponseResolver();

        assertThat(resolver.support(parameter))
            .isTrue();
    }
    @Test
    @DisplayName("HttpServletResponse 인수를 가진 요청에 대해 request를 반환합니다.")
    void returnServletResponse(){
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        Method method = Arrays.stream(TestController.class.getDeclaredMethods())
            .filter(methods -> methods.getName() == "findUserId")
            .findFirst()
            .orElseThrow(RuntimeException::new);

        Parameter parameter = method.getParameters()[1];
        ParameterResolver resolver = new HttpServletResponseResolver();

        assertThat(resolver.parseValue(parameter, request, response))
            .isEqualTo(response);
    }
}
