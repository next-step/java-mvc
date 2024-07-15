package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class HandlerExecutionTest {

    private final HandlerExecutionController controller = new HandlerExecutionController();

    @Test
    void 생성_시_존재하지_않는_메소드로_생성하는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new HandlerExecution(controller, "error"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("요청된 method name에 해당하는 핸들링 메소드가 없습니다.");
    }

    @Test
    void 생성_시_지원하는_파라미터와_불일치하면_예외가_발생한다() {
        assertThatThrownBy(() -> new HandlerExecution(controller, "handleError"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("요청된 method name에 해당하는 핸들링 메소드가 없습니다.");
    }

    @Test
    void HandleExecution을_생성한다() {
        Assertions.assertDoesNotThrow(() -> new HandlerExecution(controller, "handle"));
    }

    @Test
    void request_와_response를_받아_특정_메소드를_실행한다() throws Exception {
        HandlerExecution handlerExecution = new HandlerExecution(controller, "handle");
        ModelAndView actual = handlerExecution.handle(mock(HttpServletRequest.class), mock(HttpServletResponse.class));
        assertThat(actual.getObject("name")).isEqualTo("jinyoung");
    }

    @Controller
    static class HandlerExecutionController {
        @RequestMapping
        public ModelAndView handleError(HttpServletResponse response, HttpServletRequest request) {
            return null;
        }

        @RequestMapping
        public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
            return new ModelAndView(new JspView(""))
                    .addObject("name", "jinyoung");
        }
    }
}
