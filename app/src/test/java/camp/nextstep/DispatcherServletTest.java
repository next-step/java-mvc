package camp.nextstep;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

class DispatcherServletTest {

    private DispatcherServlet dispatcherServlet;

    @BeforeEach
    void setUp() {
        dispatcherServlet = new DispatcherServlet("samples");
        dispatcherServlet.init();
    }

    @Test
    void get_root_path() throws ServletException, IOException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/");
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestDispatcher("/index.jsp")).thenReturn(requestDispatcher);

        // when
        dispatcherServlet.service(request, response);

        // then
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void get_register_view_path() throws ServletException, IOException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/register/view");
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestDispatcher("/register.jsp")).thenReturn(requestDispatcher);

        // when
        dispatcherServlet.service(request, response);

        // then
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void get_test_path() throws ServletException, IOException {
        // given
        HttpServletRequest request = mock(HttpServletRequest.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getAttribute("id")).thenReturn("gugu");
        when(request.getRequestURI()).thenReturn("/get-test");
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestDispatcher("")).thenReturn(requestDispatcher);

        // when
        dispatcherServlet.service(request, response);

        // then
        verify(requestDispatcher).forward(request, response);
    }
}
