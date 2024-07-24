package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HandlerMappings {
    final List<HandlerMapping> handlerMappings = new ArrayList<>();

    public void initialize() {
        handlerMappings.add(new AnnotationHandlerMapping());
        handlerMappings.forEach(HandlerMapping::initialize);
    }

    public void addHandlerMapping(final HandlerMapping handlerMapping) {
        handlerMappings.add(handlerMapping);
    }

    public Object getHandler(final HttpServletRequest req) {
        return this.handlerMappings.stream()
                .map(mapping -> mapping.getHandler(req))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
   	}
}
