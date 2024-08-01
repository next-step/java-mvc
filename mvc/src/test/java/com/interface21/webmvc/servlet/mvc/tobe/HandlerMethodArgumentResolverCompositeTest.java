package com.interface21.webmvc.servlet.mvc.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HandlerMethodArgumentResolverCompositeTest {

    @DisplayName("Parameter 를 지원 하는 resolver 가 있는 경우 true 를 반환 한다")
    @Test
    public void supportArgument() throws Exception {
        // given
        final HandlerMethodArgumentResolverComposite composite = new HandlerMethodArgumentResolverComposite();
        composite.addResolver(new RequestParamMethodArgumentResolver(true));

        // when
        final boolean actual = composite.supportsParameter(mock(MethodParameter.class));

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("Parameter 를 지원 하는 resolver 가 없는 경우 false 를 반환 한다")
    @Test
    public void notSupportArgument() throws Exception {
        // given
        final HandlerMethodArgumentResolverComposite composite = new HandlerMethodArgumentResolverComposite();

        // when
        final boolean actual = composite.supportsParameter(mock(MethodParameter.class));

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("컨트롤러 메서드의 파라미터 이름과 일치하는 웹 요청 파라미터를 찾아 반환 한다")
    @Test
    public void resolveArgument() throws Exception {
        // given
        final HandlerMethodArgumentResolverComposite composite = new HandlerMethodArgumentResolverComposite();
        final HandlerMethodArgumentResolver resolver = mock(HandlerMethodArgumentResolver.class);
        composite.addResolver(resolver);

        when(resolver.supportsParameter(any())).thenReturn(true);
        when(resolver.resolveArgument(any(), any())).thenReturn("result");

        // when
        final Object actual = composite.resolveArgument(mock(MethodParameter.class), mock(ServletWebRequest.class));

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("컨트롤러 메서드의 파라미터를 bind 할 수 없는 경우 예외를 던진다")
    @Test
    public void notResolveArgument() throws Exception {
        // given
        final HandlerMethodArgumentResolverComposite composite = new HandlerMethodArgumentResolverComposite();
        final HandlerMethodArgumentResolver resolver = mock(HandlerMethodArgumentResolver.class);
        composite.addResolver(resolver);
        final MethodParameter parameter = mock(MethodParameter.class);

        when(resolver.supportsParameter(any())).thenReturn(false);
        when(parameter.getName()).thenReturn("String");

        // when then
        assertThatThrownBy(() -> composite.resolveArgument(parameter, mock(ServletWebRequest.class)))
                .isInstanceOf(ArgumentNotResolvedException.class)
                .hasMessage("Unable to resolve Argument = [String]");
    }
}
