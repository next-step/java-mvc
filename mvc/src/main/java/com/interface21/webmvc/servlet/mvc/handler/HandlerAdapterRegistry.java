package com.interface21.webmvc.servlet.mvc.handler;

import java.util.List;

public class HandlerAdapterRegistry {

    private final List<HandlerAdapter> handlerAdapters;

    public HandlerAdapterRegistry(HandlerAdapter... handlerAdapters) {
        this.handlerAdapters = List.of(handlerAdapters);
    }

    public HandlerAdapter getHandlerAdapter(Object handlerMapping) {
        return handlerAdapters.stream()
                              .filter(handlerAdapter -> handlerAdapter.accept(handlerMapping))
                              .findAny()
                              .orElseThrow(() -> new IllegalStateException("unsupported handler mapping type"));
    }
}
