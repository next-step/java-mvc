package com.interface21.webmvc.servlet.mvc.tobe.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PathPatternUtilTest {

    @Test
    void getUriValue() {
        final var uriValue = PathPatternUtil.getUriValue("/users/{id}", "/users/1", "id");
        assertThat(uriValue).isEqualTo("1");
    }

    @Test
    void matchAndExtract() {
        final var uriVariables = PathPatternUtil.getUriVariables("/{var1}/{var2}", "/foo/bar");
        assertThat(uriVariables.get("var1")).isEqualTo("foo");
        assertThat(uriVariables.get("var2")).isEqualTo("bar");
    }

    @DisplayName("path가 url pattern에 일치하면 true를 반환한다")
    @ParameterizedTest
    @CsvSource(
            value = {
                "/user/{id}, /user/12, true",
                "/{foo}/{var}, /users/names, true",
                "/user/{id}, /books/{id}, false"
            },
            delimiter = ',')
    public void isUrlMatchTest(String pattern, String path, boolean expected) {

        assertThat(PathPatternUtil.isUrlMatch(pattern, path)).isEqualTo(expected);
    }
}
