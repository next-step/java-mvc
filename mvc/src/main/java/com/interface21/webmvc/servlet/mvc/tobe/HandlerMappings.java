package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.mvc.tobe.exception.HandlerNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HandlerMappings {
    private final List<HandlerMapping> values;

    private HandlerMappings(List<HandlerMapping> values) {
        this.values = values;
    }

    public static HandlerMappings of(List<HandlerMapping> handlerMappings) {
        return new HandlerMappings(new ArrayList<>(handlerMappings));
    }

    public void add(HandlerMapping handlerMapping) {
        values.add(handlerMapping);
    }

    public void initialize() {
        values.forEach(HandlerMapping::initialize);
    }

    public Object getHandler(HttpServletRequest request) {
        return values.stream()
                .map(handlerMapping -> handlerMapping.getHandler(request))
                .filter(Objects::nonNull)
                .findAny()
                .orElseThrow(() -> new HandlerNotFoundException(request.getRequestURI()));
    }
}
