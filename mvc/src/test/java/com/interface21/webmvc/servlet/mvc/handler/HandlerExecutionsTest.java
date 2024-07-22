package com.interface21.webmvc.servlet.mvc.handler;

import static org.assertj.core.api.Assertions.assertThat;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.ModelAndView;
import com.interface21.webmvc.servlet.mvc.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HandlerExecutionsTest {
    @DisplayName("Controller 클래스와 Controller 인스턴스를 받아 @RequestMapping이 달린 메서드에 한해 HandlerExecution을 생성한다.")
    @Test
    void test() {
        Class<ControllerForThisTest> controllerClass = ControllerForThisTest.class;
        ControllerForThisTest controllerInstance = new ControllerForThisTest();

        HandlerExecutions handlerExecutions = HandlerExecutions.of(controllerClass, controllerInstance);

        assertThat(handlerExecutions).hasSize(1);
    }

    @Controller
    private static class ControllerForThisTest {
        @RequestMapping(value = "/test", method = RequestMethod.GET)
        public ModelAndView method(final HttpServletRequest request, final HttpServletResponse response) {
            return new ModelAndView(new JspView(""));
        }

        public ModelAndView method2(final HttpServletRequest request, final HttpServletResponse response) {
            return new ModelAndView(new JspView(""));
        }
    }
}