package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class HandlerExecutionTest {

    private final HandlerExecutionController controller = new HandlerExecutionController();
    private final Method method = HandlerExecutionController.class
            .getMethod("handle", HttpServletRequest.class, HttpServletResponse.class);

    HandlerExecutionTest() throws NoSuchMethodException {
    }

    @Test
    void HandleExecution을_생성한다() {
        Assertions.assertDoesNotThrow(() -> new HandlerExecution(controller, method, ""));
    }

    @Test
    void request_와_response를_받아_특정_메소드를_실행한다() throws Exception {
        HandlerExecution handlerExecution = new HandlerExecution(controller, method, "");
        ModelAndView actual = handlerExecution.handle(mock(HttpServletRequest.class), mock(HttpServletResponse.class));
        assertThat(actual.getObject("name")).isEqualTo("jinyoung");
    }

    @Controller
    private static class HandlerExecutionController {
        @RequestMapping
        public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
            return new ModelAndView(new JspView(""))
                    .addObject("name", "jinyoung");
        }
    }
}
