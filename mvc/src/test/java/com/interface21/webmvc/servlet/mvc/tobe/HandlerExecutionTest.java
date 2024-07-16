package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

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



    static class TestClass{
        public void test(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse){
        }
    }
}
