package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("HandlerMappingRegistry 클래스")
class HandlerMappingRegistryTest {

    @DisplayName("HandlerMapping을 등록하고 getHandler 메서드를 호출하면 Handler를 반환한다.")
    @Test
    void getHandler() {
        // given
        final var request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/get-test");
        when(request.getMethod()).thenReturn("GET");

        HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry();
        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping("samples");
        annotationHandlerMapping.initialize();
        handlerMappingRegistry.addHandlerMapping(annotationHandlerMapping);

        // when
        Object handler = handlerMappingRegistry.getHandler(request);

        // then
        assertNotNull(handler);
    }


}
