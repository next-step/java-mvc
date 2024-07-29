package com.interface21.webmvc.servlet.mvc.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.DelegatingServletInputStream;

class HttpRequestBodyParserTest {
    @DisplayName("HttpServletRequest의 InputStream을 이용해 body를 파싱한다.")
    @Test
    void parse() throws IOException {
        HttpRequestBodyParser httpRequestBodyParser = new HttpRequestBodyParser();

        HttpServletRequest request = mock(HttpServletRequest.class);
        String body = "{\"userId\":\"gugu\",\"password\":\"123\",\"age\":\"19\"}";
        InputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        when(request.getInputStream()).thenReturn(new DelegatingServletInputStream(byteArrayInputStream));

        assertThat(httpRequestBodyParser.parse(request)).isEqualTo(body);
    }
}