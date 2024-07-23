package com.interface21.webmvc.servlet.mvc.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PathVariableArgumentResolverTest {

    private final PathVariableArgumentResolver resolver = new PathVariableArgumentResolver("/users/{id}/cart/{cartId}");

    @Test
    @DisplayName("PathVariable 에 해당하는 값을 반환한다")
    void resolveArgumentTest() {
        final MethodParameter parameter = new FakeMethodParameter(Long.class, "id", "id");
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/users/123/cart/abc");

        final Object result = resolver.resolveArgument(parameter, request, null);

        assertThat(result).isEqualTo(123L);
    }

    @Test
    @DisplayName("PathVariable 타입이 올바르지 않은 경우 예외를 던진다")
    void resolveArgumentInvalidValueTest() {
        final MethodParameter parameter = new FakeMethodParameter(Long.class, "cartId", "cartId");
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/users/123/cart/abc");

        assertThatThrownBy(() -> resolver.resolveArgument(parameter, request, null))
                .isInstanceOf(NumberFormatException.class);
    }


}
