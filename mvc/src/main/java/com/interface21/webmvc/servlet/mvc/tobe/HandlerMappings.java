package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

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
        for (final HandlerMapping mapping : this.handlerMappings) {
            final Object handler = mapping.getHandler(req);
            if (handler != null) {
                return handler;
            }
        }

   		return null;
   	}
}
