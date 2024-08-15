package com.interface21.webmvc.servlet.mvc.tobe.parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.interface21.webmvc.servlet.mvc.tobe.parameter.config.ResolverRegistryConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.TestUserController;

class MethodArgumentResolverRegistryTest {
    @Test
    @DisplayName("MethodArgumentResolverRegistry 지원하는지 확인한다.")
    void support(){
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);
        when(request.getParameter("id")).thenReturn("123");
        when(request.getParameter("age")).thenReturn("1234");

        Method method = Arrays.stream(TestUserController.class.getDeclaredMethods())
            .filter(methods -> methods.getName() == "create_int_long")
            .findFirst()
            .orElseThrow(RuntimeException::new);

        Parameter parameter = method.getParameters()[0];
        ResolverRegistry MethodArgumentResolverRegistry = ResolverRegistryConfig.getResolverRegistry();


        assertThat(MethodArgumentResolverRegistry.resolve(parameter, request, response))
            .isEqualTo(123L);

    }
}
