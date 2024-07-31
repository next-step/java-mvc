package com.interface21.webmvc.servlet.mvc.handler.parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.TestUserController;

class BasicArgumentTypeResolverTest {

    @Test
    @DisplayName("supportsParameter는 BasicArgumentType 중 String을 지원한다.")
    void supports_String() throws NoSuchMethodException {
        // given
        BasicArgumentTypeResolver resolver = new BasicArgumentTypeResolver();
        Method method = TestUserController.class.getMethod("create_string", String.class, String.class);
        Parameter parameter = method.getParameters()[0];

        // when
        boolean result = resolver.supportsParameter(parameter);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("supportsParameter는 BasicArgumentType 중 int와 Long를 지원한다.")
    void supports_intAndLong() throws NoSuchMethodException {
        // given
        BasicArgumentTypeResolver resolver = new BasicArgumentTypeResolver();
        Method method = TestUserController.class.getMethod("create_int_long", long.class, int.class);
        Parameter intParameter = method.getParameters()[0];
        Parameter longParameter = method.getParameters()[1];

        // when
        boolean intResult = resolver.supportsParameter(intParameter);
        boolean longResult = resolver.supportsParameter(longParameter);

        // then
        assertAll(
            () -> assertThat(intResult).isTrue(),
            () -> assertThat(longResult).isTrue()
        );
    }

    @Test
    @DisplayName("resolveArgument는 BasicArgumentType 중 String 타입의 메서드 인자를 해결할 수 있다.")
    void testResolveArgument_WithString() throws Exception {
        // given
        BasicArgumentTypeResolver resolver = new BasicArgumentTypeResolver();
        Method method = TestUserController.class.getMethod("create_string", String.class, String.class);
        MethodParameter idParameter = new MethodParameter(method, method.getParameters()[0]);
        MethodParameter passwordParameter = new MethodParameter(method, method.getParameters()[1]);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("userId")).thenReturn("myId");
        when(request.getParameter("password")).thenReturn("myPassword");

        // when
        Object id = resolver.resolveArgument(idParameter, request, null);
        Object password = resolver.resolveArgument(passwordParameter, request, null);

        // then
        assertThat(id).isEqualTo("myId");
        assertThat(password).isEqualTo("myPassword");
    }

    @Test
    @DisplayName("resolveArgument는 BasicArgumentType 중 int 타입의 메서드 인자를 해결할 수 있다.")
    void testResolveArgument_WithInt() throws Exception {
        // given
        BasicArgumentTypeResolver resolver = new BasicArgumentTypeResolver();
        Method method = TestUserController.class.getMethod("create_int_long", long.class, int.class);
        MethodParameter idParameter = new MethodParameter(method, method.getParameters()[0]);
        MethodParameter ageParameter = new MethodParameter(method, method.getParameters()[1]);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("age")).thenReturn("20");

        // when
        Object id = resolver.resolveArgument(idParameter, request, null);
        Object age = resolver.resolveArgument(ageParameter, request, null);

        // then
        assertAll(
            () -> assertThat(id).isEqualTo(1L),
            () -> assertThat(age).isEqualTo(20)
        );
    }
}