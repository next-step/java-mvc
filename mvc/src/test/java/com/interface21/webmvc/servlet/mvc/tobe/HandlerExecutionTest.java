package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HandlerExecutionTest {

    private final HandlerExecutionController controller = new HandlerExecutionController();

    @Test
    void 생성_시_존재하지_않는_메소드로_생성하는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new HandlerExecution(controller, "error"))
                .isInstanceOf(NoSuchMethodException.class);
    }

    @Test
    void 생성_시_지원하는_파라미터와_불일치하면_예외가_발생한다() {
        assertThatThrownBy(() -> new HandlerExecution(controller, "handleError"))
                .isInstanceOf(NoSuchMethodException.class);
    }

    @Test
    void HandleExecution을_생성한다() {
        Assertions.assertDoesNotThrow(() -> new HandlerExecution(controller, "handle"));
    }

    @Controller
    static class HandlerExecutionController {
        @RequestMapping
        public ModelAndView handleError(HttpServletResponse response, HttpServletRequest request) {
            return null;
        }

        @RequestMapping
        public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
            return null;
        }
    }
}
