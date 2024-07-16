package com.interface21.webmvc.servlet.view;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;

class JsonViewTest {

    @Test
    void 요청된_model로_json타입에_맞게_랜더링한다() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        MockHttpServletResponse response = new MockHttpServletResponse();

        new JsonView().render(Map.of("name", "jinyoung"), request, response);
        assertAll(
                () -> assertThat(response.getContentType()).isEqualTo("application/json;charset=UTF-8"),
                () -> assertThat(response.getContentLength()).isEqualTo(19),
                () -> assertThat(response.getContentAsString()).isEqualTo("{\"name\":\"jinyoung\"}")
        );
    }
}
