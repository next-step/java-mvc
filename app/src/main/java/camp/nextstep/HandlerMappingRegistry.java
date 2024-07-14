package camp.nextstep;

import com.interface21.webmvc.servlet.mvc.tobe.HandlerMapping;

import java.util.Arrays;
import java.util.List;

public class HandlerMappingRegistry {

    private final List<HandlerMapping> handlerMappings;

    public HandlerMappingRegistry(HandlerMapping... handlerMappings) {
        this.handlerMappings = Arrays.stream(handlerMappings)
                .toList();
        this.handlerMappings.forEach(HandlerMapping::initialize);
    }
}
