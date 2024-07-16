package com.interface21.webmvc.servlet.view;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JspViewTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    @DisplayName("viewName 이 없을 경우 예외를 던진다")
    void createFailTest(final String viewName) {
        assertThatThrownBy(() -> new JspView(viewName));
    }

    @Test
    @DisplayName("viewName 이 REDIRECT_PREFIX 로 시작하면 해당 prefix 뒤의 페이지로 redirect 한다")
    void startsWith_REDIRECT_PREFIX_test() throws Exception {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final JspView jspView = new JspView("redirect:/test.jsp");

        jspView.render(Map.of(), request, response);

        verify(response, times(1)).sendRedirect("/test.jsp");
    }

    @Test
    @DisplayName("viewName 이 REDIRECT_PREFIX 로 시작하지 않으면 forward 된다")
    void not_startWith_REDIRECT_PREFIX_test() throws Exception {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        final JspView jspView = new JspView("/test.jsp");

        when(request.getRequestDispatcher("/test.jsp")).thenReturn(requestDispatcher);
        jspView.render(Map.of(), request, response);

        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    @DisplayName("forward 될때 request.attribute 에 값을 추가할 수 있다")
    void not_startWith_REDIRECT_PREFIX_with_model_test() throws Exception {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        final JspView jspView = new JspView("/test.jsp");

        when(request.getRequestDispatcher("/test.jsp")).thenReturn(requestDispatcher);
        jspView.render(Map.of("testKey", "testValue"), request, response);

        verify(request, times(1)).setAttribute("testKey", "testValue");
        verify(requestDispatcher, times(1)).forward(request, response);
    }

}
