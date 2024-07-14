package camp.nextstep;

import camp.nextstep.controller.LoginController;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HandlerMappingRegistryTest {

    @Test
    void Registry생성과_동시에_초기화한다() {
        ManualHandlerMapping givenHandlerMapping = new ManualHandlerMapping();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/login");

        new HandlerMappingRegistry(givenHandlerMapping);
        assertThat(givenHandlerMapping.getHandler(request)).isInstanceOf(LoginController.class);
    }
}
