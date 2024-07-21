package com.interface21.webmvc.servlet.mvc.tobe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class LongArgumentResolverTest {

    private LongArgumentResolver resolver;

    @BeforeEach
    void setUp() {
        resolver = new LongArgumentResolver();
    }

    @Test
    @DisplayName("long 원시 타입에 대해 true 를 반환해야 한다")
    void longPrimitiveTest() {
        final MethodParameter parameter = new FakeMethodParameter(long.class, "id");
        assertThat(resolver.supportsParameter(parameter)).isTrue();
    }

    @Test
    @DisplayName("Long 래퍼 타입에 대해 true 를 반환한다")
    void longWrapperTest() {
        final MethodParameter parameter = new FakeMethodParameter(Long.class, "id");
        assertThat(resolver.supportsParameter(parameter)).isTrue();
    }

    @Test
    @DisplayName("long 타입이 아닌 경우 false 를 반환한다")
    void notLongTypeTest() {
        final MethodParameter parameter = new FakeMethodParameter(String.class, "id");
        assertThat(resolver.supportsParameter(parameter)).isFalse();
    }

    @Test
    @DisplayName("resolveArgument 는 long 값을 반환한다")
    void resolveArgumentTest() {
        final MethodParameter parameter = new FakeMethodParameter(long.class, "id");
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("id", "123");

        final Object result = resolver.resolveArgument(parameter, request, null);
        assertThat(result).isEqualTo(123L);
    }

}
