package com.interface21.webmvc.servlet.mvc.tobe.support;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


class PathPatternParserTest {
    @ParameterizedTest
    @CsvSource(value = {
            "/users/{userId}:/users/123:true",
            "/users/{userId}:/users/123/:false",
            "/users/{userId}/orders/{orderId}:/users/123/orders/456:true",
            "/users/{userId}/orders/{orderId}:/users/123/order/456:false",
    }, delimiter = ':')
    @DisplayName("url pattern 을 등록해, 실제 url 이 해당 pattern 에 맞는지 여부를 반환받을 수 있다")
    void patternMatchingTest(final String pattern, final String url, final boolean expected) {
        final PathPatternParser parser = new PathPatternParser(pattern);

        assertThat(parser.matches(url)).isEqualTo(expected);
    }

    @Test
    @DisplayName("url pattern 을 통해, 실제 url 에서 변수값을 추출할 수 있다")
    void extractUriVariablesTest() {
        final PathPatternParser parser = new PathPatternParser("/users/{userId}/orders/{orderId}");
        final String path = "/users/123/orders/456";

        final Map<String, String> variables = parser.extractUriVariables(path);

        assertThat(variables).isEqualTo(Map.of("userId", "123", "orderId", "456"));
    }
}
