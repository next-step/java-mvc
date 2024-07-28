package com.interface21.webmvc.servlet.mvc.tobe.method;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("ArgumentResolvers 클래스의")
class ArgumentResolversTest {

    @DisplayName("getInstance 메서드는 ArgumentResolvers 인스턴스를 반환한다.")
    @Test
    void getInstance() {
        // when
        ArgumentResolvers argumentResolvers = ArgumentResolvers.getInstance();

        // then
        assertNotNull(argumentResolvers);
    }

    @DisplayName("resolveArgument 메서드는 argument를 반환한다.")
    @Test
    void HandlerMethodArgumentResolver() {
        // given
        ArgumentResolvers argumentResolvers = ArgumentResolvers.getInstance();
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);
        final var methodParameter = mock(MethodParameter.class);
        when(methodParameter.getParameterName()).thenReturn("userId");
        when(methodParameter.getParameterType()).thenReturn((Class) String.class);
        when(request.getParameter("userId")).thenReturn("gugu");

        // when
        Object handlerMethodArgumentResolver = argumentResolvers.resolveArgument(methodParameter, request, response);

        // then
        assertNotNull(handlerMethodArgumentResolver);
        assertThat(handlerMethodArgumentResolver).isEqualTo("gugu");
    }
}
