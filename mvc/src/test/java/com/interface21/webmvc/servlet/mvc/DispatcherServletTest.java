package com.interface21.webmvc.servlet.mvc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("DispatcherServlet 클래스는")
class DispatcherServletTest {

    private final DispatcherServlet dispatcherServlet = new DispatcherServlet("samples");
    ;
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);

    @BeforeEach
    void setUp() {
        dispatcherServlet.init();
    }

    @DisplayName("GET /get-test 요청을 처리한다.")
    @Test
    void get() throws Exception {
        when(request.getRequestURI()).thenReturn("/get-test");
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestDispatcher("/get-test.jsp")).thenReturn(requestDispatcher);

        dispatcherServlet.service(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @DisplayName("POST /post-test 요청을 처리한다.")
    @Test
    void post() throws Exception {
        when(request.getRequestURI()).thenReturn("/post-test");
        when(request.getMethod()).thenReturn("POST");
        when(request.getRequestDispatcher("post-test.jsp")).thenReturn(requestDispatcher);

        dispatcherServlet.service(request, response);

        verify(requestDispatcher).forward(request, response);
    }
}
