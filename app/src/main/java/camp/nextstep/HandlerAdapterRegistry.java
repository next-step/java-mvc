package camp.nextstep;

import com.interface21.webmvc.servlet.mvc.tobe.AdapterRegistry;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerAdapter;
import java.util.List;
import java.util.Optional;

public class HandlerAdapterRegistry implements AdapterRegistry {

    private final List<HandlerAdapter> handlerAdapters;

    public HandlerAdapterRegistry(List<HandlerAdapter> handlerAdapters) {
        this.handlerAdapters = handlerAdapters;
    }

    @Override
    public Optional<HandlerAdapter> getHandlerAdapter(Object handler) {
        return handlerAdapters.stream()
            .filter(handlerAdapter -> handlerAdapter.supports(handler))
            .findFirst();
    }
}
