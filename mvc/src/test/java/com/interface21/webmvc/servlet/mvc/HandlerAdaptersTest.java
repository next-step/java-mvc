package com.interface21.webmvc.servlet.mvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.interface21.webmvc.servlet.mvc.handler.HandlerExecution;
import com.interface21.webmvc.servlet.mvc.handler.RequestMappingHandlerAdapter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import samples.success.TestController;

class HandlerAdaptersTest {
    @DisplayName("지원하는 HandlerAdapter가 있을 경우 찾아서 반환 해준다.")
    @Test
    void test() throws NoSuchMethodException {
        HandlerAdapters handlerAdapters = HandlerAdapters.create();
        handlerAdapters.add(new RequestMappingHandlerAdapter());

        TestController testController = new TestController();
        Class<TestController> testControllerClass = TestController.class;
        Method method = testControllerClass.getMethod("findUserId", HttpServletRequest.class, HttpServletResponse.class);
        HandlerExecution handlerExecution = new HandlerExecution(testController, method);

        HandlerAdapter handlerAdapter = handlerAdapters.findBy(handlerExecution);
        assertAll(
                () -> assertThat(handlerAdapter).isNotNull(),
                () -> assertThat(handlerAdapter).isInstanceOf(RequestMappingHandlerAdapter.class)
        );
    }

    @DisplayName("지원하는 HandlerAdapter가 없을 경우 예외를 발생시킨다.")
    @Test
    void test2() {
        HandlerAdapters handlerAdapters = HandlerAdapters.create();

        assertThatThrownBy(() -> handlerAdapters.findBy("thisIsHandler"))
                .isInstanceOf(HandlerAdapterNotFoundException.class);
    }
}