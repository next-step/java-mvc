package com.interface21.webmvc.servlet.mvc.tobe.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class PathPatternParserTest {

    @DisplayName("path variable patten과 path 패턴 매칭이면 true 반환한다")
    @ParameterizedTest
    @CsvSource(
            value = {
                "/user/{test}, /user/id, true",
                "/{foo}/{bar}, /foo/bar, true",
                "/{var1}/{var2}, /var1/var2, true",
                "/users/name, /users/name, true"
            })
    public void matchTest(String pattern, String path, boolean expected) {
        PathPatternParser pathPatternParser = new PathPatternParser(pattern);
        boolean matches = pathPatternParser.matches(path);
        assertThat(matches).isEqualTo(expected);
    }

    @DisplayName("path에서 path variable 패턴에 해당하는 값을 추출한다")
    @ParameterizedTest
    @MethodSource("argumentsStream")
    public void extractVariablesTest(
            String pattern, String path, Map<String, String> extractedVariables) {

        PathPatternParser pathPatternParser = new PathPatternParser(pattern);

        Map<String, String> stringStringMap = pathPatternParser.extractUriVariables(path);

        assertThat(stringStringMap).isEqualTo(extractedVariables);
    }

    static Stream<Arguments> argumentsStream() {
        return Stream.of(
                Arguments.of("/user/{id}", "/user/gugu", Map.of("id", "gugu")),
                Arguments.of("/{foo}/{bar}", "/foo/bar", Map.of("foo", "foo", "bar", "bar")));
    }
}
