package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HandlerKeyTest {

    @Test
    void HttpServletRequest로_HandlerKey를_생성할_수_있다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/login");
        when(request.getMethod()).thenReturn("GET");

        HandlerKey actual = HandlerKey.from(request);
        assertThat(actual).isEqualTo(new HandlerKey("/login", RequestMethod.GET));
    }

    @Test
    void handlerKey가_일치하면_true를_반환한다() {
        HandlerKey handlerKey = new HandlerKey("/users", RequestMethod.GET);
        boolean actual = handlerKey.isMatchUrl(new HandlerKey("/users", RequestMethod.GET));
        assertThat(actual).isTrue();
    }

    @Test
    void handlerKey가_패턴이_일치하면_true를_반환한다() {
        HandlerKey handlerKey = new HandlerKey("/users/{id}", RequestMethod.GET);
        boolean actual = handlerKey.isMatchUrl(new HandlerKey("/users/123", RequestMethod.GET));
        assertThat(actual).isTrue();
    }
}
