package com.interface21.webmvc.servlet;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

public class HandlerMappingRegistry {
    private final List<HandlerMapping<HttpRequestHandlers>> handlerMappings = new ArrayList<>();
    private final HttpRequestHandlers handlers = new HttpRequestHandlers();

    public void addHandlerMapping(final HandlerMapping<HttpRequestHandlers> handlerMapping) {
        handlerMappings.add(handlerMapping);
    }

    public void initialize() {
        handlerMappings.forEach(mapping -> mapping.initialize(handlers));
    }

    public HandlerExecution getHandler(final HttpServletRequest request) {
        return handlers.getHandler(request);
    }
}
