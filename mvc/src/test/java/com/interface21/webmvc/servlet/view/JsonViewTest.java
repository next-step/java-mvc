package com.interface21.webmvc.servlet.view;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.AnnotationHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecution;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.DelegatingServletOutputStream;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class JsonViewTest {
    private AnnotationHandlerMapping handlerMapping;

    @BeforeEach
    void setUp() {
        handlerMapping = new AnnotationHandlerMapping("samples");
        handlerMapping.initialize();
    }

    @Test
    @DisplayName("모델 안에 1개의 원소만 있을 때는 값만 노출한다.")
    void testOneItem() throws Exception {
        final var request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/user");
        when(request.getMethod()).thenReturn("GET");

        final var targetStream = new ByteArrayOutputStream();
        final var delegatingServletOutputStream = new DelegatingServletOutputStream(targetStream);
        final var response = mock(HttpServletResponse.class);
        when(response.getOutputStream()).thenReturn(delegatingServletOutputStream);

        processRequest(request, response);

        assertThat(targetStream.toString()).isEqualTo("{\"a\":\"b\"}");
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final ModelAndView modelAndView = handlerExecution.handle(request, response);
        final Map<String, Object> model = modelAndView.getModel();
        modelAndView.getView().render(model, request, response);
    }

    @Test
    @DisplayName("모델 안에 2개의 원소만 있을 때는 맵 자체를 노출한다.")
    void testMultipleItems() throws Exception {
        final var request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/json-post-test");
        when(request.getMethod()).thenReturn("POST");

        final var targetStream = new ByteArrayOutputStream();
        final var delegatingServletOutputStream = new DelegatingServletOutputStream(targetStream);
        final var response = mock(HttpServletResponse.class);
        when(response.getOutputStream()).thenReturn(delegatingServletOutputStream);

        processRequest(request, response);

        assertThat(targetStream.toString()).isEqualTo("{\"user\":{\"c\":\"d\"},\"age\":14}");
    }

    @Test
    @DisplayName("JsonView 사용할 때 헤더는 잘 적용됐는지")
    void testContentTypeHeader() throws Exception {
        final var request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/json-post-test");
        when(request.getMethod()).thenReturn("POST");

        final var targetStream = new ByteArrayOutputStream();
        final var delegatingServletOutputStream = new DelegatingServletOutputStream(targetStream);
        final var response = mock(HttpServletResponse.class);
        when(response.getOutputStream()).thenReturn(delegatingServletOutputStream);

        processRequest(request, response);

        assertThat(targetStream.toString()).isEqualTo("{\"user\":{\"c\":\"d\"},\"age\":14}");
        verify(response).addHeader("Content-Type", "application/json;charset=UTF-8");
    }
}
