package camp.nextstep;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import camp.nextstep.controller.RegisterViewController;
import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ManualHandlerAdapterTest {
    @Test
    @DisplayName("String 이 아닌 ModelView를 반환한다.")
    void handlerAdapter() throws Exception {
        ManualHandlerAdapter manualHandlerAdapter = new ManualHandlerAdapter();

        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getAttribute("id")).thenReturn("gugu");
        when(request.getRequestURI()).thenReturn("/no-method");

        final var modelAndView = manualHandlerAdapter.handler(new RegisterViewController(), request, response);
        assertThat(modelAndView).isInstanceOf(ModelAndView.class);
    }
}
