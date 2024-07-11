package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnnotationHandlerMappingTest {

    private AnnotationHandlerMapping handlerMapping;

    @BeforeEach
    void setUp() {
        handlerMapping = new AnnotationHandlerMapping("samples");
        handlerMapping.initialize();
    }

    @Test
    void get() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getAttribute("id")).thenReturn("gugu");
        when(request.getRequestURI()).thenReturn("/get-test");
        when(request.getMethod()).thenReturn("GET");

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("id")).isEqualTo("gugu");
    }

    @Test
    void post() throws Exception {
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getAttribute("id")).thenReturn("gugu");
        when(request.getRequestURI()).thenReturn("/post-test");
        when(request.getMethod()).thenReturn("POST");

        final var handlerExecution = (HandlerExecution) handlerMapping.getHandler(request);
        final var modelAndView = handlerExecution.handle(request, response);

        assertThat(modelAndView.getObject("id")).isEqualTo("gugu");
    }

    @Test
    void 지원하지_않는_url_method로_요청하면_예외가_발생한다() {
        final var request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/no-support");
        when(request.getMethod()).thenReturn("GET");
        assertThatThrownBy(() -> handlerMapping.getHandler(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("지원하지 않는 request 요청입니다.");
    }

    @Test
    void Controller_어노테이션이_인터페이스에_붙는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new AnnotationHandlerMapping("constructor.instantiation").initialize())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("클래스가 아닌 추상클래스, 인터페이스는 생성자를 만들 수 없습니다.");
    }

    @Test
    void Controller가_생성자가_접근불가능한_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new AnnotationHandlerMapping("constructor.access").initialize())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("클래스의 생성자에 접근할 수 없습니다.");
    }

    @Test
    void Controller생성자에서_예외가_발생하는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new AnnotationHandlerMapping("constructor.invocation").initialize())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("생성자에서 예외가 발생했습니다.");
    }

    @Test
    void Controller가_지원생성자가_없는_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new AnnotationHandlerMapping("constructor.nosuch").initialize())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("지원하는 생성자가 존재하지 않습니다.");
    }
}
