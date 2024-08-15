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
import samples.TestUser;
import samples.TestUserController;

class ObjectParameterResolverTest {

    @Test
    @DisplayName("Object Type을  지원하는지 확인하면 실패한다.")
    void supportFail() {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getParameter("userId")).thenReturn("1234");
        when(request.getParameter("password")).thenReturn("12345");
        when(request.getParameter("age")).thenReturn("123456");

        Method method = Arrays.stream(TestUserController.class.getDeclaredMethods())
            .filter(methods -> methods.getName() == "create_javabean")
            .findFirst()
            .orElseThrow(RuntimeException::new);

        Parameter parameter = method.getParameters()[0];
        ParameterResolver resolver = new ObjectParameterResolver();

        assertThat(resolver.support(parameter))
            .isFalse();
    }

    @Test
    @DisplayName("Object Type 인수를 가진 요청에 대해 request를 반환합니다.")
    void returnObjectParameterParsed() {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getParameter("userId")).thenReturn("1234");
        when(request.getParameter("password")).thenReturn("12345");
        when(request.getParameter("age")).thenReturn("123456");

        Method method = Arrays.stream(TestUserController.class.getDeclaredMethods())
            .filter(methods -> methods.getName() == "create_javabean")
            .findFirst()
            .orElseThrow(RuntimeException::new);

        Parameter parameter = method.getParameters()[0];
        ParameterResolver resolver = new ObjectParameterResolver();

        assertThat(resolver.parseValue(parameter,request, response))
            .isEqualTo(new TestUser("1234", "12345",123456));
    }
}
