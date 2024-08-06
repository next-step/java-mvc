package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMethod;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class HandlerKeyTest {

    @ParameterizedTest
    @CsvSource(
            delimiter = ',',
            value = {
                    "/test,GET,/test,GET,true",
                    "/test,GET,/test,POST,false",
                    "/test,GET,/test2,GET,false",
                    "/test,GET,/test2,POST,false"
            }
    )
    void matches(final String url, final RequestMethod requestMethod, final String url2, final RequestMethod requestMethod2, final boolean expect) {
        // given
        final HandlerKey handlerKey = new HandlerKey(url, requestMethod);
        final HandlerKey handlerKey2 = new HandlerKey(url2, requestMethod2);

        // when
        final boolean result = handlerKey.matches(handlerKey2);

        // then
        assertThat(result).isEqualTo(expect);
    }
}
