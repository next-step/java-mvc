package com.interface21.webmvc.servlet.mvc.tobe.support;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PathPatternParserTest {

    @DisplayName("matches 메서드 테스트")
    @Test
    void matchesTest() {
        // given
        final PathPatternParser parser = new PathPatternParser("/hello/{name}");

        // when
        final boolean result = parser.matches("/hello/test");

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("파라미터 1개 : path가 pattern과 일치하는 경우 extractUriVariables 메서드 테스트")
    @Test
    void extractUriVariableTest() {
        // given
        final PathPatternParser parser = new PathPatternParser("/hello/{name}");

        // when
        final Map<String, String> result = parser.extractUriVariables("/hello/test");

        // then
        assertThat(result.get("name")).isEqualTo("test");
    }

    @DisplayName("파라미터 2개 이상 : path가 pattern과 일치하는 않는 경우 extractUriVariables 메서드 테스트")
    @Test
    void extractUriVariablesTest() {
        // given
        final PathPatternParser parser = new PathPatternParser("/hello/{name}/{age}");

        // when
        final Map<String, String> result = parser.extractUriVariables("/hello/test/20");

        // then
        assertThat(result.get("name")).isEqualTo("test");
        assertThat(result.get("age")).isEqualTo("20");
    }

    @DisplayName("path가 pattern과 일치하지 않는 경우 extractUriVariables 메서드 테스트")
    @Test
    void extractUriVariablesNotMatchTest() {
        // given
        final PathPatternParser parser = new PathPatternParser("/hello/{name}/{age}");

        // when
        final Map<String, String> result = parser.extractUriVariables("/test/test/20");

        // then
        assertThat(result.size()).isZero();
    }
}
