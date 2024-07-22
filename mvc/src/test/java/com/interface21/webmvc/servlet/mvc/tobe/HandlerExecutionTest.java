package com.interface21.webmvc.servlet.mvc.tobe;


import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.exception.HandlerExecutionFailException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;


class HandlerExecutionTest {
    private Class<FakeClass> clazz;
    private Class<HttpServletRequest> requestClass;
    private Class<HttpServletResponse> responseClass;
    private FakeClass instance;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() throws Exception {
        clazz = FakeClass.class;
        requestClass = HttpServletRequest.class;
        responseClass = HttpServletResponse.class;
        instance = clazz.getDeclaredConstructor().newInstance();
        request = mock(requestClass);
        response = mock(responseClass);
    }

    @Test
    @DisplayName("넘겨 받은 객와의 메소드 이름으로 handle 메서드를 실행할 수 있다")
    void handleTest() throws Exception {
        final HandlerExecution handlerExecution = new HandlerExecution(instance, clazz.getDeclaredMethod("successMethod", requestClass, responseClass), new ArgumentResolvers(""));

        final ModelAndView handle = handlerExecution.handle(request, response);

        assertThat(handle.getObject("name")).isEqualTo("Jongmin");
    }

    @ParameterizedTest
    @ValueSource(strings = {"voidMethod", "wrongReturnMethod"})
    @DisplayName("handle 메서드의 return type 이 ModelAndView 가 아니면 예외를 던진다")
    void handleFailTest(final String methodName) throws Exception {
        final HandlerExecution handlerExecution = new HandlerExecution(instance, clazz.getDeclaredMethod(methodName, requestClass, responseClass), new ArgumentResolvers(""));

        assertThatThrownBy(() -> handlerExecution.handle(request, response))
                .isInstanceOf(HandlerExecutionFailException.class)
                .hasMessage("handle method must return ModelAndView Class");
    }

    static class FakeClass {
        public ModelAndView successMethod(final HttpServletRequest request, final HttpServletResponse response) {
            final ModelAndView modelAndView = new ModelAndView(null);
            modelAndView.addObject("name", "Jongmin");
            return modelAndView;
        }

        public Long wrongReturnMethod(final HttpServletRequest request, final HttpServletResponse response) {
            return 0L;
        }

        public void voidMethod(final HttpServletRequest request, final HttpServletResponse response) {
        }
    }
}
