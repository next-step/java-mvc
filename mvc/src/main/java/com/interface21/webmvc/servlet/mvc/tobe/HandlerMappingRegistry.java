package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.mvc.exception.HandlerMappingException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class HandlerMappingRegistry {
    private final List<HandlerMapping> handlerMappings;

    public HandlerMappingRegistry(final HandlerMapping handlerMapping, final HandlerMapping... handlerMappings) {
        this.handlerMappings = Stream.concat(
                Stream.of(handlerMapping),
                Stream.of(handlerMappings)
        ).toList();
    }

    public Object getHandler(final HttpServletRequest request) {
        return handlerMappings.stream()
                .map(handlerMapping -> handlerMapping.getHandler(request))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new HandlerMappingException("No handler found for request " + request.getRequestURI()));
    }
}
