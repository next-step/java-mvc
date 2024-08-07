package com.interface21.webmvc.servlet.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;

class JsonViewTest {

  @Test
  void returnJson() throws Exception {
    HttpServletRequest request = mock(HttpServletRequest.class);
    MockHttpServletResponse response = new MockHttpServletResponse();

    new JsonView().render(Map.of("key", "value"), request, response);
    assertAll(
        () -> assertThat(response.getContentType()).isEqualTo("application/json;charset=UTF-8"),
        () -> assertThat(response.getContentLength()).isEqualTo(19),
        () -> assertThat(response.getContentAsString()).isEqualTo("{\"key\":\"value\"}")
    );
  }

}