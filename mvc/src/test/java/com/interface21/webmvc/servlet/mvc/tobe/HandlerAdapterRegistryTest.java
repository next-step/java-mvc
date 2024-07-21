package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.exception.HandlerAdapterException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class HandlerAdapterRegistryTest {
    private Class<FakeHandlerExecution> clazz;
    private FakeHandlerExecution instance;
    private Class<HttpServletRequest> requestClass;
    private Class<HttpServletResponse> responseClass;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HandlerAdapterRegistry handlerAdapterRegistry;

    @BeforeEach
    void setUp() throws Exception {
        clazz = FakeHandlerExecution.class;
        instance = clazz.getDeclaredConstructor().newInstance();
        requestClass = HttpServletRequest.class;
        responseClass = HttpServletResponse.class;
        request = mock(requestClass);
        response = mock(responseClass);
        handlerAdapterRegistry = new HandlerAdapterRegistry(new HandlerExecutionHandlerAdapter());
    }

    @Test
    @DisplayName("등록된 HandlerAdapter 을 통해 ModelAndView 를 반환받을 수 있다")
    void handlerAdapterTest() throws Exception {
        final ModelAndView modelAndView = handlerAdapterRegistry.handle(request, response, new HandlerExecution(instance, clazz.getDeclaredMethod("successMethod", requestClass, responseClass)));

        assertThat(modelAndView).isEqualTo(ModelAndView.ofJspView("test"));
    }

    @Test
    @DisplayName("등록된 HandlerAdapter 가 존재하지 않으면 예외를 던진다")
    void handlerAdapterNotExistTest() {
        assertThatThrownBy(() -> handlerAdapterRegistry.handle(request, response, new FakeHandlerExecution()))
                .isInstanceOf(HandlerAdapterException.class)
                .hasMessageStartingWith("Unknown handler type");
    }

    static class FakeHandlerExecution {
        public ModelAndView successMethod(final HttpServletRequest request, final HttpServletResponse response) {
            return ModelAndView.ofJspView("test");
        }
    }

}
