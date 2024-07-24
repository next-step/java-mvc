package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Parameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ServletHandlerMethodArgumentResolverTest {
    private final ServletHandlerMethodArgumentResolver resolver = new ServletHandlerMethodArgumentResolver();

    @DisplayName("파라미터가 ServletRequest, ServletResponse 인 경우 true 를 반환 한다")
    @Test
    public void supportParameter() throws Exception {
        // given
        final Parameter request = mock(Parameter.class);
        final Parameter response = mock(Parameter.class);

        when(request.getType()).thenReturn((Class) HttpServletRequest.class);
        when(response.getType()).thenReturn((Class) HttpServletResponse.class);

        // when
        final boolean actual1 = resolver.supportsParameter(request);
        final boolean actual2 = resolver.supportsParameter(response);

        // then
        assertAll(
                () -> assertThat(actual1).isTrue(),
                () -> assertThat(actual2).isTrue()
        );
    }

    @DisplayName("파라미터가 ServletRequest, ServletResponse 가 아닌 경우 false 를 반환 한다")
    @Test
    public void notSupportParameter() throws Exception {
        // given
        final Parameter parameter = mock(Parameter.class);

        when(parameter.getType()).thenReturn((Class) String.class);

        // when
        final boolean actual = resolver.supportsParameter(parameter);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("ServletRequest 타입의 파라미터를 반환 한다")
    @Test
    public void resolveServletRequestArgument() throws Exception {
        // given
        final Parameter parameter = mock(Parameter.class);
        final ServletWebRequest webRequest = mock(ServletWebRequest.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);

        when(parameter.getType()).thenReturn((Class) HttpServletRequest.class);
        when(webRequest.getRequest()).thenReturn(request);

        // when
        final Object actual = resolver.resolveArgument(parameter, webRequest);

        // then
        assertThat(actual).isNotNull().isEqualTo(request);
    }

    @DisplayName("ServletResponse 타입의 파라미터를 반환 한다")
    @Test
    public void resolveServletResponseArgument() throws Exception {
        // given
        final Parameter parameter = mock(Parameter.class);
        final ServletWebRequest webRequest = mock(ServletWebRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        when(parameter.getType()).thenReturn((Class) HttpServletResponse.class);
        when(webRequest.getResponse()).thenReturn(response);

        // when
        final Object actual = resolver.resolveArgument(parameter, webRequest);

        // then
        assertThat(actual).isNotNull().isEqualTo(response);
    }
}
