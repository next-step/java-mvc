package camp.nextstep;

import com.interface21.webmvc.servlet.mvc.tobe.HandlerMapping;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public class HandlerMappingRegistry {

    private final List<HandlerMapping> handlerMappings;

    public HandlerMappingRegistry(HandlerMapping... handlerMappings) {
        this.handlerMappings = List.of(handlerMappings);
    }

    public void initialize() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    public Object getHandler(HttpServletRequest request) {
        return handlerMappings.stream()
                              .map(handlerMapping -> handlerMapping.getHandler(request))
                              .findFirst()
                              .orElseThrow(IllegalStateException::new);
    }
}
