package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HandlerKeyTest {

    @Test
    @DisplayName("HandlerKey 는 url 과 method 가 같으면 동등한 객체이다")
    void equalityTest() {
        final HandlerKey handlerKey = new HandlerKey("test", RequestMethod.GET);

        assertThat(handlerKey).isEqualTo(new HandlerKey("test", RequestMethod.GET));
    }

    @Test
    @DisplayName("HandlerKey 를 HttpServletRequest 로 생성할 수 있다")
    void createFromHttpRequestTest() {
        final var request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("test");
        when(request.getMethod()).thenReturn("GET");

        final HandlerKey handlerKey = HandlerKey.from(request);

        assertThat(handlerKey).isEqualTo(new HandlerKey("test", RequestMethod.GET));
    }


    @ParameterizedTest
    @CsvSource(value = {
            "/test/{id}:GET:/test/123:GET:true",
            "/test/{id}:POST:/test/123:GET:false",
            "/test/{id}:GET:/test/:GET:false",
            "/test/{id}:GET:/test:GET:false"
    }, delimiter = ':')
    @DisplayName("url pattern 에 맞고 method 가 같은지 여부를 반환한다")
    void matchesTest(final String pattern, final RequestMethod method, final String url, final RequestMethod requestMethod, final boolean expected) {
        final HandlerKey handlerKey = new HandlerKey(pattern, method);

        assertThat(handlerKey.matches(new HandlerKey(url, requestMethod))).isEqualTo(expected);
    }
}
