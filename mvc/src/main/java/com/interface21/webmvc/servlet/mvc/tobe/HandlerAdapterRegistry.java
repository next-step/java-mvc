package com.interface21.webmvc.servlet.mvc.tobe;

import java.util.Arrays;
import java.util.List;

public class HandlerAdapterRegistry {
    private final List<HandlerAdapter> handlerAdapters;

    public HandlerAdapterRegistry(final HandlerAdapter... handlerAdapters) {
        this.handlerAdapters = Arrays.asList(handlerAdapters);
    }

    public HandlerAdapter getHandlerAdapter(final Object handler) {
        return handlerAdapters.stream()
                .filter(handlerAdapter -> handlerAdapter.supports(handler))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported handler type: " + handler.getClass().getName()));
    }
}
