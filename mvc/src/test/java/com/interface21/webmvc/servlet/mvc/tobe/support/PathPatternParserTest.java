package com.interface21.webmvc.servlet.mvc.tobe.support;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PathPatternParserTest {

    @ParameterizedTest
    @CsvSource(value = {
        "/users/{id},/users/123456",
        "/users/{id}/products/{productid},/users/123456/products/123456"
    })
    @DisplayName("url 패턴에 따른 변수 값들을 가져온다.")
    void patternPatternParser(String pattern, String url) {
        PathPatternParser pathPatternParser = new PathPatternParser(pattern);
        Map<String, String> params = pathPatternParser.extractUriVariables(url);

        assertThat(pathPatternParser.matches(url)).isTrue();
        assertThat(params).containsKey("id");
        assertThat(params).containsValues("123456");
    }
}
