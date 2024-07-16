package com.interface21.webmvc.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class DispatcherServletTest {

    private final DispatcherServlet dispatcherServlet  = new DispatcherServlet("samples");;
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);

    @BeforeEach
    void setUp() {
        dispatcherServlet.init();
    }

    @Test
    void get_root_path() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/");
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestDispatcher("/index.jsp")).thenReturn(requestDispatcher);

        dispatcherServlet.service(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void get_register_view_path() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/register/view");
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestDispatcher("/register.jsp")).thenReturn(requestDispatcher);

        dispatcherServlet.service(request, response);

        verify(requestDispatcher).forward(request, response);
    }
}
