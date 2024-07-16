package com.interface21.webmvc.servlet.view;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.mockito.Mockito.*;

class JspViewTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);

    @Test
    void redirect가_포함되는_경우_response에_redirect를_전달한다() throws Exception {
        doNothing().when(response).sendRedirect("/index.jsp");

        new JspView("redirect:/index.jsp").render(Map.of(), request, response);
        verify(response).sendRedirect("/index.jsp");
    }

    @Test
    void model을_포함하여_forward한다() throws Exception {
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        doNothing().when(request).setAttribute("name", "jinyoung");
        when(request.getRequestDispatcher("/index.jsp")).thenReturn(requestDispatcher);
        doNothing().when(requestDispatcher).forward(request, response);

        new JspView("/index.jsp").render(Map.of("name", "jinyoung"), request, response);
        verify(request).setAttribute("name", "jinyoung");
    }
}
