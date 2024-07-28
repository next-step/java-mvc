package com.interface21.webmvc.servlet;

import java.util.ArrayList;
import java.util.List;

public class ExceptionHandlerRegistry {
    private final List<HandlerMapping<ExceptionHandlers>> handlerMappings = new ArrayList<>();
    private final ExceptionHandlers handlers = new ExceptionHandlers();

    public void addHandlerMapping(final HandlerMapping<ExceptionHandlers> handlerMapping) {
        handlerMappings.add(handlerMapping);
    }

    public void initialize() {
        handlerMappings.forEach(mapping -> mapping.initialize(handlers));
    }

    public HandlerExecution getHandler(final Class<? extends Throwable> exception) {
        return handlers.getHandler(exception);
    }
}
