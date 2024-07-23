package com.interface21.webmvc.servlet.mvc.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HandlerAdapterRegistryTest {

    @DisplayName("지원하지 않는 클래스를 조회하면 예외를 던진다.")
    @Test
    void getHandleExceptionTest() {
        // given
        final HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry();

        // when // then
        assertThatThrownBy(() -> handlerAdapterRegistry.getHandlerAdapter(new Object()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Unsupported handler type: java.lang.Object");
    }

    @DisplayName("HandlerExecutionAdapter를 반환한다.")
    @Test
    void getHandlerTest() {
        // given
        final HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry(new HandlerExecutionAdapter());

        // when
        final HandlerAdapter handlerAdapter = handlerAdapterRegistry.getHandlerAdapter(new HandlerExecution(new TestClass(), "method"));

        // then
        assertThat(handlerAdapter).isInstanceOf(HandlerExecutionAdapter.class);
    }

    private static class TestClass {
        public void method() {

        }
    }
}
