package com.interface21.webmvc.servlet;

import com.interface21.webmvc.servlet.mvc.tobe.annotation.AnnotationHandlerAdapter;
import com.interface21.webmvc.servlet.mvc.tobe.annotation.HandlerExecution;
import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import samples.TestController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class HandlerAdapterRegistryTest {

    private final HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry(new AnnotationHandlerAdapter());
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);

    @Test
    void 지원가능한_adapter가_없으면_예외가_발생한다() {
        assertThatThrownBy(() -> handlerAdapterRegistry.handle("error-handler", request, response))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("지원가능한 adapter가 없습니다.");
    }

    @Test
    void handler에_맞는_adapter로_실행한다() throws Exception {
        HandlerExecution handlerExecution = new HandlerExecution(new TestController(), "findUserId");

        ModelAndView actual = handlerAdapterRegistry.handle(handlerExecution, request, response);
        assertThat(actual.getView()).isEqualTo(new JspView(""));
    }
}
