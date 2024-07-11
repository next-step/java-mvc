package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HandlerExecutionTest {

    @Test
    void 생성_시_존재하지_않는_메소드로_생성하는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new HandlerExecution(new HandlerExecutionController(), "error"))
                .isInstanceOf(NoSuchMethodException.class);
    }

    @Controller
    static class HandlerExecutionController {

    }
}
