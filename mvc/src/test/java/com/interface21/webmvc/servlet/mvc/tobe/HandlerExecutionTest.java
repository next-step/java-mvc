package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HandlerExecutionTest {

    @Test
    void 생성_시_존재하지_않는_메소드로_생성하는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new HandlerExecution(new HandlerExecutionController(), "error"))
                .isInstanceOf(NoSuchMethodException.class);
    }

    @Test
    void 생성_시_지원하는_파라미터와_불일치하면_예외가_발생한다() {
        assertThatThrownBy(() -> new HandlerExecution(new HandlerExecutionController(), "handleError"))
                .isInstanceOf(NoSuchMethodException.class);
    }

    @Controller
    static class HandlerExecutionController {
        @RequestMapping
        public ModelAndView handleError(HttpServletResponse response, HttpServletRequest request) {
            return null;
        }
    }
}
