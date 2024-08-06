package com.interface21.webmvc.servlet.mvc.tobe;

import java.util.ArrayList;
import java.util.List;

public class HandlerAdapters {
    private final List<HandlerAdapter> values;

    private HandlerAdapters() {
        this.values = new ArrayList<>();
    }

    public static HandlerAdapters of(List<HandlerAdapter> handlerAdapters) {
        HandlerAdapters handlerAdapter = new HandlerAdapters();
        handlerAdapter.values.addAll(handlerAdapters);

        return handlerAdapter;
    }
    public HandlerAdapter findBy(Object handler) {
        return values.stream()
                .filter(handlerAdapter -> handlerAdapter.accept(handler))
                .findAny()
                .orElseThrow(() -> new RuntimeException("지원가능한 adapter가 없습니다."));
    }
}
