package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.method.ArgumentResolvers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import samples.TestController;

@DisplayName("HandlerExecution 클래스는")
class HandlerExecutionTest {

    private final ArgumentResolvers argumentResolvers = ArgumentResolvers.getInstance();

    @DisplayName("handle 메서드는")
    @Nested
    class Handle {

        @DisplayName("컨트롤러의 메서드를 정상적으로 실행한다.")
        @Test
        void it_invokes_controller_method() throws Exception {
            // given
            HttpServletRequest request = mock(HttpServletRequest.class);
            HttpServletResponse response = mock(HttpServletResponse.class);
            when(request.getRequestURI()).thenReturn("/get-test");
            when(request.getMethod()).thenReturn("GET");
            when(request.getParameter("userId")).thenReturn("gugu");
            final TestController testController = new TestController();
            final HandlerExecution handlerExecution = new HandlerExecution(testController,
                testController.getClass().getMethod("findUserId", String.class));

            // when
            final ModelAndView result = (ModelAndView) handlerExecution.handle(argumentResolvers, request, response);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getObject("userId")).isEqualTo("gugu");
        }

        @DisplayName("request parameter가 없는 경우 null을 반환한다.")
        @Test
        void it_returns_null_when_no_return_value() throws Exception {
            // given
            HttpServletRequest request = mock(HttpServletRequest.class);
            HttpServletResponse response = mock(HttpServletResponse.class);
            when(request.getRequestURI()).thenReturn("/get-test");
            when(request.getMethod()).thenReturn("GET");
            when(request.getParameter("userId")).thenReturn(null);
            final TestController testController = new TestController();
            final HandlerExecution handlerExecution = new HandlerExecution(testController,
                testController.getClass().getMethod("findUserId", String.class));

            // when
            final ModelAndView result = (ModelAndView) handlerExecution.handle(argumentResolvers, request, response);

            // then
            assertThat(result).isNotNull();
            assertThat(result.getObject("userId")).isNull();
        }
    }
}
