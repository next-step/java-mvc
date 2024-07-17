package com.interface21.webmvc.servlet.view;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JspViewTest {

  @DisplayName("redirect 테스트")
  @Test
  void redirectTest() throws Exception {
    // given
    final JspView jspView = new JspView("redirect:/test.jsp");
    final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    final HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
    final Map<String, String> model = new HashMap<>();

    // when
    jspView.render(model, httpServletRequest, httpServletResponse);

    // then
    verify(httpServletResponse, times(1)).sendRedirect("/test.jsp");
  }

    @DisplayName("redirect가 아닌 경우 테스트")
    @Test
    void notRedirectTest() throws Exception {
        // given
        final JspView jspView = new JspView("/test.jsp");
        final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        final HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        final Map<String, String> model = new HashMap<>();
        final RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        given(httpServletRequest.getRequestDispatcher("/test.jsp")).willReturn(requestDispatcher);

      // when
        jspView.render(model, httpServletRequest, httpServletResponse);

        // then
        verify(requestDispatcher).forward(httpServletRequest, httpServletResponse);
    }
}
