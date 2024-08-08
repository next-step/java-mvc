package camp.nextstep;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.controller.RegisterViewController;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerAdapter;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HandlerAdapterRegistryTest {

    @Test
    @DisplayName("지원하는 HandlerAdapter 반환한다.")
    void getHandlerAdapter() throws Exception {
        ManualHandlerAdapter manualHandlerAdapter = new ManualHandlerAdapter();
        HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry(List.of(manualHandlerAdapter));

        final var handlerAdapter = handlerAdapterRegistry.getHandlerAdapter(new RegisterViewController());
        assertThat(handlerAdapter).isInstanceOf(Optional.class);
        assertThat(handlerAdapter.get()).isInstanceOf(HandlerAdapter.class);

    }

    @Test
    @DisplayName("지원하지 않는 HandlerAdapter 반환에 실패한다.")
    void failGettingHandlerAdapter() throws Exception {
        ManualHandlerAdapter manualHandlerAdapter = new ManualHandlerAdapter();
        HandlerAdapterRegistry handlerAdapterRegistry = new HandlerAdapterRegistry(List.of(manualHandlerAdapter));

        assertThatThrownBy(() -> handlerAdapterRegistry.getHandlerAdapter(new String())
            .orElseThrow(RuntimeException::new)).isInstanceOf(RuntimeException.class);
    }
}
