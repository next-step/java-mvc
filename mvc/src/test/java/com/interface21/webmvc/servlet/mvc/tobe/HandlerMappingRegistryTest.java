package com.interface21.webmvc.servlet.mvc.tobe;

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

    registry = new HandlerMappingRegistry(handlerMapping1, handlerMapping2);
    registry.initialize();
  }

  @Test
  @DisplayName("등록한 HandlerMapping 객체를 조회한다")
  public void testGetHandlerMapping_Found() {
    when(handlerMapping1.getHandler(request)).thenReturn(Optional.empty());
    when(handlerMapping2.getHandler(request)).thenReturn(Optional.of(new Object()));

    HandlerMapping result = registry.getHandlerMapping(request);

    assertThat(result).isEqualTo(handlerMapping2);
  }

  @Test
  @DisplayName("조회할 수 없는 request 일 때는 exception 이 발생한다")
  public void testGetHandlerMapping_NotFound() {
    when(handlerMapping1.getHandler(request)).thenReturn(Optional.empty());
    when(handlerMapping2.getHandler(request)).thenReturn(Optional.empty());

    final var exception = assertThrows(UnSupportedHandlerException.class,
        () -> registry.getHandlerMapping(request)
    );

    assertThat(exception.getMessage()).isEqualTo("Unsupported request: " + request.getRequestURI());
  }
}