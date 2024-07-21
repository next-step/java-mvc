package com.interface21.webmvc.servlet.mvc.tobe;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.TestController;

@DisplayName("HandlerAdapterRegistry 클래스는")
class HandlerAdapterRegistryTest {

    @DisplayName("HandlerAdapter를 추가하고, 해당 HandlerAdapter를 반환한다.")
    @Test
    void addHandlerAdapter() {
        // given
        final HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry();
        final HandlerAdapter handlerAdapter = new AnnotationHandlerAdapter();

        // when
        handlerAdapterRegistry.addHandlerAdapter(handlerAdapter);

        // then
        assertEquals(handlerAdapter, handlerAdapterRegistry.getHandlerAdapter(new HandlerExecution(new TestController(), null)));
    }

    @DisplayName("지원되지 않는 HandlerAdapter를 추가하면 IllegalStateException을 발생시킨다.")
    @Test
    void addHandlerAdapterWithUnsupportedHandler() {
        // given
        final HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry();
        final HandlerAdapter handlerAdapter = new AnnotationHandlerAdapter();

        // when
        handlerAdapterRegistry.addHandlerAdapter(handlerAdapter);

        // then
        assertThrows(IllegalStateException.class, () -> handlerAdapterRegistry.getHandlerAdapter(new TestController()));
    }
}
