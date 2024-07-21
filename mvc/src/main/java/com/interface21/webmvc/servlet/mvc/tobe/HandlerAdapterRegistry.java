package com.interface21.webmvc.servlet.mvc.tobe;

import java.util.ArrayList;
import java.util.List;

public class HandlerAdapterRegistry {

    private final List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    public void addHandlerAdapter(HandlerAdapter handlerAdapter) {
        handlerAdapters.add(handlerAdapter);
    }

    public HandlerAdapter getHandlerAdapter(Object object) {
        return handlerAdapters
            .stream()
            .filter(handlerAdapter -> handlerAdapter.supports(object))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(object.getClass() + "핸들러는 지원되지 않습니다."));
    }
}
