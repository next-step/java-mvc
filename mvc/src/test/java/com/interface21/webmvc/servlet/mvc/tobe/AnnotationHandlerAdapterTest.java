package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AnnotationHandlerAdapterTest {

    private final AnnotationHandlerAdapter annotationHandlerAdapter = new AnnotationHandlerAdapter();

    @Test
    void HandlerExecution_handler를_지원가능으로_판단한다() {
        boolean actual = annotationHandlerAdapter.accept(new HandlerExecution(new HandlerExecutionController(), "handle"));
        assertThat(actual).isTrue();
    }

    @Test
    void HandlerExecution가_아닌_handler는_지원불가능으로_판단한다() {
        boolean actual = annotationHandlerAdapter.accept("error");
        assertThat(actual).isFalse();
    }

    @Controller
    static class HandlerExecutionController {

        @RequestMapping
        public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) {
            return new ModelAndView(new JspView(""))
                    .addObject("name", "jinyoung");
        }
    }
}
