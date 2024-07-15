package com.interface21.webmvc.servlet.view;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.mockito.Mockito.*;

class JspViewTest {

    @Test
    void redirect가_포함되는_경우_response에_redirect를_전달한다() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        doNothing().when(response).sendRedirect("/index.jsp");

        new JspView("redirect:/index.jsp").render(Map.of(), request, response);
        verify(response).sendRedirect("/index.jsp");
    }
}
