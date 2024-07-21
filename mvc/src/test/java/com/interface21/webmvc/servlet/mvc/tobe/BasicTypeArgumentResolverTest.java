package com.interface21.webmvc.servlet.mvc.tobe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicTypeArgumentResolverTest {

    private BasicTypeArgumentResolver resolver;

    @BeforeEach
    void setUp() {
        resolver = new BasicTypeArgumentResolver();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "int:true",
            "long:true",
            "java.lang.Integer:true",
            "java.lang.Long:true",
            "java.lang.String:true",
            "java.util.Map:false",
            "java.util.Map:false"
    }, delimiter = ':')
    @DisplayName("BasicType 을 지원하는지 여부를 반환한다")
    void primitiveTest(final Class<?> clazz, final boolean expected) {
        final MethodParameter parameter = new FakeMethodParameter(clazz, "id");
        assertThat(resolver.supportsParameter(parameter)).isEqualTo(expected);
    }

    @Test
    @DisplayName("resolveArgument 는 parameter type 에 맞는 타입의 값을 반환한다")
    void resolveArgumentTest() {
        final MethodParameter parameter = new FakeMethodParameter(long.class, "id");
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("id", "123");

        final Object result = resolver.resolveArgument(parameter, request, null);

        assertThat(result).isEqualTo(123L);
    }

}
