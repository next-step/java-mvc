package com.interface21.webmvc.servlet.mvc.tobe;

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
}
