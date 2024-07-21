package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class HandlerExecutionTest {
    private final TestController controller = new TestController();

    @DisplayName("HandleExecution을 생성한다")
    @Test
    void createHandleExecution() {
        Assertions.assertDoesNotThrow(() -> new HandlerExecution(controller, "handle"));
    }

    @DisplayName("존재하지 않는 메소드로 생성하는 경우 예외가 발생한다.")
    @Test
    void createHandlerExecutionNotExistMethod() {
        assertThatThrownBy(() -> new HandlerExecution(controller, "error"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("요청된 method name에 해당하는 핸들링 메소드가 없습니다.");
    }

    @DisplayName("불일치한 파라미터로 생성하는 경우 예외가 발생한다.")
    @Test
    void createHandlerExecutionNotMatchParameter() {
        assertThatThrownBy(() -> new HandlerExecution(controller, "handleError"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("요청된 method name에 해당하는 핸들링 메소드가 없습니다.");
    }

    @DisplayName("handle 메소드를 실행한다.")
    @Test
    void handle() throws Exception {
        HandlerExecution handlerExecution = new HandlerExecution(controller, "handle");
        ModelAndView actual = handlerExecution.handle(mock(HttpServletRequest.class), mock(HttpServletResponse.class));
        assertThat(actual.getObject("id")).isEqualTo("sm9171");
    }

    @Controller
    private static class TestController {
        @RequestMapping
        public ModelAndView handleError(HttpServletResponse response, HttpServletRequest request) {
            return null;
        }

        @RequestMapping
        public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
            return new ModelAndView(new JspView(""))
                    .addObject("id", "sm9171");
        }
    }
}