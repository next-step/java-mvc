package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
