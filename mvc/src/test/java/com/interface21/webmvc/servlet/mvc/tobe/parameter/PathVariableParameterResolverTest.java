package com.interface21.webmvc.servlet.mvc.tobe.parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.TestUserController;

class PathVariableParameterResolverTest {
    @Test
    @DisplayName("PathVariableParameter 지원하는지 확인한다.")
    void support(){
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        Method method = Arrays.stream(TestUserController.class.getDeclaredMethods())
            .filter(methods -> methods.getName() == "show_pathvariable")
            .findFirst()
            .orElseThrow(RuntimeException::new);

        Parameter parameter = method.getParameters()[0];
        ParameterResolver resolver = new PathVariableParameterResolver("/users/{id}");

        assertThat(resolver.support(parameter))
            .isTrue();
    }
    @Test
    @DisplayName("PathVariableParameter 인수를 가진 요청에 대해 request를 반환합니다.")
    void returnPathVariableParameter(){
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);
        when(request.getRequestURI()).thenReturn("/users/12345");

        Method method = Arrays.stream(TestUserController.class.getDeclaredMethods())
            .filter(methods -> methods.getName() == "show_pathvariable")
            .findFirst()
            .orElseThrow(RuntimeException::new);

        Parameter parameter = method.getParameters()[0];
        ParameterResolver resolver = new PathVariableParameterResolver("/users/{id}");

        assertThat(resolver.parseValue(parameter, request, response))
            .isEqualTo(12345L);
    }
}
