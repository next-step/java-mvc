package com.interface21.webmvc.servlet.mvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.interface21.webmvc.servlet.mvc.resolver.PathVariableHandlerMethodArgumentResolver;
import com.interface21.webmvc.servlet.mvc.resolver.RequestParamHandlerMethodArgumentResolver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RequestHandlerAdapterTest {

  @Test
  @DisplayName("기본 HandlerMethodArgumentResolver 를 조회한다")
  void getDefaultResolversTest() {
    final RequestHandlerAdapter requestHandlerAdapter = RequestHandlerAdapter.getInstance();

    final var result = requestHandlerAdapter.getDefaultArgumentResolvers();

    assertAll(
        () -> assertThat(result.size()).isEqualTo(2),
        () -> assertThat(result.get(0)).isInstanceOf(PathVariableHandlerMethodArgumentResolver.class),
        () -> assertThat(result.get(1)).isInstanceOf(RequestParamHandlerMethodArgumentResolver.class)
    );
  }
}