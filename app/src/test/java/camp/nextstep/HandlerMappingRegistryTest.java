package camp.nextstep;

import com.interface21.webmvc.servlet.mvc.tobe.annotation.AnnotationHandlerMapping;
import com.interface21.webmvc.servlet.mvc.tobe.annotation.HandlerExecution;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HandlerMappingRegistryTest {

    private HandlerMappingRegistry handlerMappingRegistry;

    @BeforeEach
    void setUp() {
        this.handlerMappingRegistry = new HandlerMappingRegistry(new AnnotationHandlerMapping("samples"));
        this.handlerMappingRegistry.initialize();
    }

    @Test
    void 지원하는_handler가_없는_경우_예외가_발생한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/error_path");
        when(request.getMethod()).thenReturn("GET");

        assertThatThrownBy(() -> handlerMappingRegistry.getHandler(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("요청된 request를 처리할 수 있는 handler가 없습니다.");
    }

    @Test
    void 지원하는_handler를_찾아_반환한다() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/get-test");
        when(request.getMethod()).thenReturn("GET");

        Object actual = handlerMappingRegistry.getHandler(request);
        assertThat(actual).isInstanceOf(HandlerExecution.class);
    }
}
