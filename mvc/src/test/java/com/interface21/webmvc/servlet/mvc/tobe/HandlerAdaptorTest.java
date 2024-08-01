package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.mvc.tobe.config.ValueConfig;
import com.interface21.webmvc.servlet.mvc.tobe.exception.NoExactHandlerException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HandlerAdaptorTest {
    @Test
    void 매핑된_요청이_아닐경우_예외를_발생시킨다() {
        // given
        ValueConfig valueConfig = new ValueConfig();
        var annotationHandlerMapping = new AnnotationHandlerMapping(
                valueConfig,
                "samples"
        );
        annotationHandlerMapping.initialize();
        HandlerAdaptor handlerAdaptor = new HandlerAdaptor(List.of(
                annotationHandlerMapping
        ));

        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/notfound");
        when(request.getMethod()).thenReturn("GET");

        // when & then
        assertThrows(
                NoExactHandlerException.class,
                () -> handlerAdaptor.execute(request, response)
        );
    }
}