package com.interface21.webmvc.servlet.mvc.tobe;

import static org.junit.jupiter.api.Assertions.*;

import com.interface21.web.bind.annotation.RequestMethod;
import org.junit.jupiter.api.DisplayName;

@DisplayName("HandlerKey 클래스는")
class HandlerKeyTest {

    @DisplayName("HandlerKey 인스턴스를 생성한다.")
    @org.junit.jupiter.api.Test
    void createHandlerKey() {
        // given
        final String url = "/test";
        final RequestMethod requestMethod = RequestMethod.GET;

        // when
        final HandlerKey handlerKey = new HandlerKey(url, requestMethod);

        // then
        assertAll(
            () -> assertEquals(url, handlerKey.getUrl()),
            () -> assertEquals(requestMethod, handlerKey.getRequestMethod())
                 );
    }
}
