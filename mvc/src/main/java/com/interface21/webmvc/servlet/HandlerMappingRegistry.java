package com.interface21.webmvc.servlet;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HandlerMappingRegistry {
    private final List<HandlerMapping> handlerMappings = new ArrayList<>();


    public void addHandlerMapping(final HandlerMapping handlerMapping) {
        handlerMappings.add(handlerMapping);
    }

    public void initialize() {
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    public HandlerExecution getHandler(final HttpServletRequest request) {
        return handlerMappings.stream().map(mapping -> mapping.getHandler(request))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}
