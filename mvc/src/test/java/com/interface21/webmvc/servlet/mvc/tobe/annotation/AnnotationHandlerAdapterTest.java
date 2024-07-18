package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;

class AnnotationHandlerAdapterTest {

    private final AnnotationHandlerAdapter annotationHandlerAdapter = new AnnotationHandlerAdapter();
    private final Method method = HandlerExecutionController.class
            .getMethod("handle", HttpServletRequest.class, HttpServletResponse.class);

    AnnotationHandlerAdapterTest() throws NoSuchMethodException {
    }

    @Test
    void HandlerExecution_handler를_지원가능으로_판단한다() {
        HandlerExecution handlerExecution = new HandlerExecution(new HandlerExecutionController(), method);
        boolean actual = annotationHandlerAdapter.accept(handlerExecution);
        assertThat(actual).isTrue();
    }

    @Test
    void HandlerExecution가_아닌_handler는_지원불가능으로_판단한다() {
        boolean actual = annotationHandlerAdapter.accept("error");
        assertThat(actual).isFalse();
    }

    @Test
    void handling된_ModelAndView를_반환한다() throws Exception {
        HandlerExecution handlerExecution = new HandlerExecution(new HandlerExecutionController(), method);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ModelAndView actual = annotationHandlerAdapter.handle(handlerExecution, request, response);
        assertAll(
                () -> assertThat(actual.getView()).isEqualTo(new JspView("")),
                () -> assertThat(actual.getObject("name")).isEqualTo("jinyoung")
        );
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
