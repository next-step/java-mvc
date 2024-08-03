package com.interface21.webmvc.servlet.mvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HandlerMappingRegistryTest {

  private HandlerMapping handlerMapping1;
  private HandlerMapping handlerMapping2;
  private HttpServletRequest request;
  private HandlerMappingRegistry registry;

  @BeforeEach
  void setUp() {
    handlerMapping1 = mock(HandlerMapping.class);
    handlerMapping2 = mock(HandlerMapping.class);
    request = mock(HttpServletRequest.class);

    registry = new HandlerMappingRegistry();
    registry.addHandlerMapping(handlerMapping1);
    registry.addHandlerMapping(handlerMapping2);
    registry.initialize();
  }

  @Test
  @DisplayName("등록한 HandlerMapping 객체를 조회한다")
  public void testGetHandler_Found() {
    final var handlerExecution = mock(HandlerExecution.class);
    when(handlerMapping1.getHandler(request)).thenReturn(null);
    when(handlerMapping2.getHandler(request)).thenReturn(handlerExecution);

    HandlerExecution result = registry.getHandler(request);

    assertThat(result).isEqualTo(handlerExecution);
  }

  @Test
  @DisplayName("조회할 수 없는 request 일 때는 exception 이 발생한다")
  public void testGetHandler_NotFound() {
    when(handlerMapping1.getHandler(request)).thenReturn(null);
    when(handlerMapping2.getHandler(request)).thenReturn(null);

    final var exception = assertThrows(UnSupportedHandlerException.class,
        () -> registry.getHandler(request)
    );

    assertThat(exception.getMessage()).isEqualTo("Unsupported request: " + request.getRequestURI());
  }
}