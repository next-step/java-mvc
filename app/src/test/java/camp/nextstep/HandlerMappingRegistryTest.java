package camp.nextstep;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.interface21.webmvc.servlet.mvc.asis.Controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HandlerMappingRegistryTest {

    @Test
    @DisplayName("지원하는 Handler를 반환한다.")
    void getHandler() {

        ManualHandlerMapping manualHandlerMapping = new ManualHandlerMapping();
        manualHandlerMapping.initialize();
        HandlerMappingRegistry registry = new HandlerMappingRegistry(
            List.of(manualHandlerMapping));

        final var request = mock(HttpServletRequest.class);
        final var response = mock(HttpServletResponse.class);

        when(request.getAttribute("id")).thenReturn("gugu");
        when(request.getRequestURI()).thenReturn("/register");
        when(request.getMethod()).thenReturn("POST");


        assertThat(registry.getHandler(request)).isInstanceOf(Optional.class);
        assertThat(registry.getHandler(request).get()).isInstanceOf(Controller.class);
    }

    @Test
    @DisplayName("지원하지 않는 Handler는 반환에 실패한다.")
    void failGettingHandler() {
        ManualHandlerMapping manualHandlerMapping = new ManualHandlerMapping();
        manualHandlerMapping.initialize();
        HandlerMappingRegistry registry = new HandlerMappingRegistry(
            List.of(manualHandlerMapping));

        final var request = mock(HttpServletRequest.class);

        when(request.getAttribute("id")).thenReturn("gugu");
        when(request.getRequestURI()).thenReturn("/hello");
        when(request.getMethod()).thenReturn("POST");

        assertThat(registry.getHandler(request)).isInstanceOf(Optional.class);
        assertThatThrownBy(() -> registry.getHandler(request)
            .orElseThrow(RuntimeException::new))
            .isInstanceOf(RuntimeException.class);
    }
}
