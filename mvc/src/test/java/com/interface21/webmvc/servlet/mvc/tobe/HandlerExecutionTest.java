package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

class HandlerExecutionTest {

    @DisplayName("메서드가 존재하지 않을 때 NoSuchMethodException을 던진다.")
    @Test
    void throwNosuchMethodException() {
        // given // when // then
        assertThatThrownBy(()->{new HandlerExecution(new Object(), "nonExistMethod");})
                .isInstanceOf(NoSuchMethodException.class);
    }

    @DisplayName("메서드가 존재할 때 HandlerExecution 객체를 생성한다.")
    @Test
    void createHandlerExecution() throws NoSuchMethodException {
        // given
        final TestClass object = new TestClass();
        final String methodName = "test";

        // when
        final HandlerExecution handlerExecution = new HandlerExecution(object, methodName);

        // then
        assertThat(handlerExecution).isNotNull();
    }

    @DisplayName("handle 메서드를 실행하면 ModelAndView 객체를 반환한다.")
    @Test
    void handleTest() throws Exception {
        // given
        final TestClass object = new TestClass();
        final String methodName = "test";
        final HandlerExecution handlerExecution = new HandlerExecution(object, methodName);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        // when
        final ModelAndView result = handlerExecution.handle(request, response);

        // then
        assertThat(result.getObject("test")).isEqualTo("test");
    }



    static class TestClass{
        public ModelAndView test(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse){
            final ModelAndView modelAndView = new ModelAndView(new JspView("test"));
            modelAndView.addObject("test","test");
            return modelAndView;
        }
    }
}
