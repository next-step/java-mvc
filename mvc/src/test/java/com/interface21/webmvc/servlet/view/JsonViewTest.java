package com.interface21.webmvc.servlet.view;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.View;
import com.interface21.webmvc.servlet.mvc.tobe.AnnotationHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecution;
import com.interface21.webmvc.servlet.mvc.tobe.ViewResolver;
import com.interface21.webmvc.servlet.mvc.tobe.viewresolver.JsonViewResolver;
import com.interface21.webmvc.servlet.mvc.tobe.viewresolver.JspViewResolver;
import com.interface21.webmvc.servlet.mvc.tobe.viewresolver.RedirectViewResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.DelegatingServletOutputStream;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class JsonViewTest {
    private final List<ViewResolver> viewResolvers = new ArrayList<>();
    private AnnotationHandlerMapping handlerMapping;

    @BeforeEach
    void setUp() {
        handlerMapping = new AnnotationHandlerMapping("samples");
        handlerMapping.initialize();

        viewResolvers.clear();
        viewResolvers.add(new RedirectViewResolver());
        viewResolvers.add(new JspViewResolver());
        viewResolvers.add(new JsonViewResolver());
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
        final View view = resolveView(modelAndView);
        view.render(model, request, response);
    }

    private View resolveView(ModelAndView modelAndView) {
        String viewName = modelAndView.getViewName();

        return viewResolvers.stream()
                            .map(viewResolver -> viewResolver.resolveView(viewName))
                            .filter(Objects::nonNull)
                            .findFirst()
                            .orElse(null);
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
