package com.interface21.webmvc.servlet.view;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.AnnotationHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecution;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.DelegatingServletOutputStream;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JsonViewTest {
    private AnnotationHandlerMapping handlerMapping;

    @BeforeEach
    void setUp() {
        handlerMapping = new AnnotationHandlerMapping("samples");
        handlerMapping.initialize();
    }

    @Test
    void test() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        ByteArrayOutputStream targetStream = new ByteArrayOutputStream();
        DelegatingServletOutputStream t = new DelegatingServletOutputStream(targetStream);

        when(request.getRequestURI()).thenReturn("/api/user");
        when(request.getMethod()).thenReturn("GET");
        when(response.getOutputStream()).thenReturn(t);

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final ModelAndView modelAndView = handlerExecution.handle(request, response);
        Map<String, Object> model = modelAndView.getModel();

        modelAndView.getView().render(model, request, response);

        assertThat(targetStream.toString()).isEqualTo("{\"a\":\"b\"}");
    }

    // XXX: 테스트 추가
    // user:{a:b} 말고 좀더 여러개 있는 걸로

    // XXX: 테스트추가
    // 헤더 체크


}
