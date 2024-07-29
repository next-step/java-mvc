package com.interface21.webmvc.servlet.mvc.tobe;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.CanIgnoreReturnValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DispatcherServletTest {

    private DispatcherServlet sut;

    @BeforeEach
    void setUp() {
        sut = new DispatcherServlet();
    }

    @DisplayName("초기화 테스트")
    @Test
    public void test() throws Exception {
        Assertions.assertThatNoException().isThrownBy(() -> sut.init());
    }

    @DisplayName("DispatcherServlet 가 정상적으로 초기화 되었다면 service 메소드가 정상적으로 동작해야 한다.")
    @Test
    public void serviceTest() throws ServletException {

        // given
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);
        sut.init();

        // when
        when(request.getRequestURI()).thenReturn("/register/view");
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestDispatcher("/register.jsp")).thenReturn(mockRequestDispatcher());

        // then
        assertThatNoException().isThrownBy(() -> sut.service(request, response));
        ;
    }

    @DisplayName("handlerMapping 정보에 없는 URI로 요청이 들어오면 ServletException이 발생해야 한다.")
    @Test
    public void serviceTestFailTest() {

        // given
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);
        sut.init();

        // when
        when(request.getRequestURI()).thenReturn("/wrong");
        when(request.getMethod()).thenReturn("GET");

        // then
        assertThatThrownBy(() -> sut.service(request, response))
                .isInstanceOf(ServletException.class);
    }

    @Test
    @DisplayName("JsonView 를 통해 렌더링 된다")
    @CanIgnoreReturnValue
    public void jsonRenderTest() throws ServletException, IOException {

        // given
        sut.init();
        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);
        final var writer = mock(PrintWriter.class);

        // when
        when(request.getRequestURI()).thenReturn("/api/user");
        when(request.getMethod()).thenReturn("GET");
        when(request.getParameter("account")).thenReturn("gugu");
        when(response.getWriter()).thenReturn(writer);

        // then
        sut.service(request, response);

        verify(response).getWriter();
        verify(writer)
                .write(
                        "{user=User{id=1, account='gugu', email='hkkang@woowahan.com', password='password'}}");
    }

    private static RequestDispatcher mockRequestDispatcher() {
        return new RequestDispatcher() {
            @Override
            public void forward(ServletRequest servletRequest, ServletResponse servletResponse)
                    throws ServletException {
                System.out.println("DispatcherServletTest.forward");
            }

            @Override
            public void include(ServletRequest servletRequest, ServletResponse servletResponse)
                    throws ServletException {
                System.out.println("DispatcherServletTest.include");
            }
        };
    }
}
