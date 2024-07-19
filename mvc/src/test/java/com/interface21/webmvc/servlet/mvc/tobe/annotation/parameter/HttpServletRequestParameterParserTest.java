package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class HttpServletRequestParameterParserTest {

    @Test
    void 지원가능한_타입이면_true를_반환한다() {
        boolean actual = new HttpServletRequestParameterParser(HttpServletRequest.class).accept();
        assertThat(actual).isTrue();
    }

    @Test
    void 지원불가능한_타입이면_false를_반환한다() {
        boolean actual = new HttpServletRequestParameterParser(HttpServletResponse.class).accept();
        assertThat(actual).isFalse();
    }

    @Test
    void 파싱요청은_request를_그대로_반환한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpServletRequestParameterParser parser = new HttpServletRequestParameterParser(HttpServletRequest.class);

        Object actual = parser.parseValue(request, response);
        assertThat(actual).isEqualTo(request);

    }
}
