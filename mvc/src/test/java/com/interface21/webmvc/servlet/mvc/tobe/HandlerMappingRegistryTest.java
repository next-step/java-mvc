package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class HandlerMappingRegistryTest {

    @DisplayName("지원하지 않는 request로 요청시 exception을 던진다.")
    @Test
    void getHandlerExceptionTest() {
        // given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HandlerMapping handlerMapping1 = mock(HandlerMapping.class);
        final HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry(handlerMapping1);
        given(handlerMapping1.getHandler(request)).willReturn(null);
        given(request.getRequestURI()).willReturn("/test");

        // when
        assertThatThrownBy(() -> handlerMappingRegistry.getHandlerMapping(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unsupported request: /test");
    }

    @DisplayName("HandlerMapping을 반환한다.")
    @Test
    void getHandlerTest() {
        // given
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HandlerMapping handlerMapping = mock(HandlerMapping.class);
        final HandlerMappingRegistry handlerMappingRegistry = new HandlerMappingRegistry(handlerMapping);
        given(handlerMapping.getHandler(request)).willReturn(new Object());

        // when
        final HandlerMapping result = handlerMappingRegistry.getHandlerMapping(request);

        // then
        assertThat(result).isEqualTo(handlerMapping);
    }
}
