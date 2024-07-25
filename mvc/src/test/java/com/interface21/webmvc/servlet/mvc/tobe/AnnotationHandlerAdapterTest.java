package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnnotationHandlerAdapterTest {

    private final AnnotationHandlerAdapter adapter = new AnnotationHandlerAdapter();
    
    @DisplayName("애너테이션 컨트롤러의 @RequestMapping 메서드가 handler 로 들어오면 true 를 반환 한다")
    @Test
    public void support() throws Exception {
        // given
        final Object handler = mock(HandlerExecution.class);

        // when
        final boolean actual = adapter.supports(handler);

        // then
        assertThat(actual).isTrue();
    }

    @DisplayName("@RequestMapping handler 가 아닌 객체는 false 를 반환 한다")
    @Test
    public void notSupport() throws Exception {
        // given
        final Object mock = mock(Object.class);

        // when
        final boolean actual = adapter.supports(mock);

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("@RequestMapping handler 를 실행하고 ModelAndView 를 반환 한다")
    @Test
    public void handle() throws Exception {
        // given
        final HandlerExecution handler = mock(HandlerExecution.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        when(handler.handle(request, response)).thenReturn(mock(ModelAndView.class));

        // when
        final ModelAndView actual = adapter.handle(request, response, handler);

        // then
        assertThat(actual).isNotNull();
    }

}
