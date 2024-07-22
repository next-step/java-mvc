package com.interface21.webmvc.servlet.mvc.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

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
}
