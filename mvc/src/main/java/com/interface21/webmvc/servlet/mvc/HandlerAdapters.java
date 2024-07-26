package com.interface21.webmvc.servlet.mvc;

import java.util.ArrayList;
import java.util.List;

public class HandlerAdapters {
    private final List<HandlerAdapter> values;

    public HandlerAdapters() {
        this.values = new ArrayList<>();
    }

    public void add(HandlerAdapter handlerAdapter) {
        values.add(handlerAdapter);
    }

    public HandlerAdapter findBy(Object handler) {
        return values.stream()
                .filter(handlerAdapter -> handlerAdapter.supports(handler))
                .findAny()
                .orElseThrow(() -> new HandlerAdapterNotFoundException(handler.getClass().getSimpleName()));
    }
}
