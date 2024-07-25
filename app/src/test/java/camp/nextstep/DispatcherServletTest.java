package camp.nextstep;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
        final var request = Mockito.mock(HttpServletRequest.class);
        final var response = Mockito.mock(HttpServletResponse.class);
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
        final var request = Mockito.mock(HttpServletRequest.class);
        final var response = Mockito.mock(HttpServletResponse.class);
        sut.init();

        // when
        when(request.getRequestURI()).thenReturn("/wrong");
        when(request.getMethod()).thenReturn("GET");

        // then
        assertThatThrownBy(() -> sut.service(request, response))
                .isInstanceOf(ServletException.class);
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
