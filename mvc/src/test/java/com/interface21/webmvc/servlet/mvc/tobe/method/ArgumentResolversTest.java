package com.interface21.webmvc.servlet.mvc.tobe.method;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("ArgumentResolvers 클래스의")
class ArgumentResolversTest {

    @DisplayName("getInstance 메서드는")
    @Nested
    class GetInstance {
        @DisplayName("ArgumentResolvers 인스턴스를 반환한다.")
        @Test
        void it_returns_instance() {
            // when
            ArgumentResolvers argumentResolvers = ArgumentResolvers.getInstance();

            // then
            assertNotNull(argumentResolvers);
        }
    }

    @DisplayName("resolveArgument 메서드는")
    @Nested
    class ResolveArgument {
        @DisplayName("argument를 반환한다.")
        @Test
        void it_returns_argument() {
            // given
            ArgumentResolvers argumentResolvers = ArgumentResolvers.getInstance();
            final var request = mock(HttpServletRequest.class);
            final var response = mock(HttpServletResponse.class);
            final var method = mock(Method.class);
            final var parameter = mock(Parameter.class);
            final var methodParameter = new MethodParameter(method, parameter);

            when(parameter.getAnnotations()).thenReturn(new Annotation[0]);
            when(parameter.getName()).thenReturn("userId");
            when(parameter.getType()).thenReturn((Class) String.class);
            when(request.getParameter("userId")).thenReturn("gugu");

            // when
            Object handlerMethodArgumentResolver = argumentResolvers.resolveArguments(methodParameter.getMethod(), request, response);

            // then
            assertNotNull(handlerMethodArgumentResolver);
            assertThat(handlerMethodArgumentResolver).isEqualTo("gugu");
        }

        @DisplayName("argument가 없는 경우 null을 반환한다.")
        @Test
        void it_returns_null_when_no_argument() {
            // given
            ArgumentResolvers argumentResolvers = ArgumentResolvers.getInstance();
            final var request = mock(HttpServletRequest.class);
            final var response = mock(HttpServletResponse.class);
            final var method = mock(Method.class);
            final var parameter = mock(Parameter.class);
            final var methodParameter = new MethodParameter(method, parameter);

            when(parameter.getAnnotations()).thenReturn(new Annotation[0]);
            when(parameter.getName()).thenReturn("userId");
            when(parameter.getType()).thenReturn((Class) String.class);
            when(request.getParameter("userId")).thenReturn(null);

            // when
            Object handlerMethodArgumentResolver = argumentResolvers.resolveArguments(methodParameter.getMethod(), request, response);

            // then
            assertThat(handlerMethodArgumentResolver).isNull();
        }
    }
}
