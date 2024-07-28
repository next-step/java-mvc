package com.interface21.webmvc.servlet.mvc.handlermapping;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

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
                              .filter(Objects::nonNull)
                              .findAny()
                              .orElseThrow(() -> new IllegalArgumentException("Unsupported Request"));
    }
}
