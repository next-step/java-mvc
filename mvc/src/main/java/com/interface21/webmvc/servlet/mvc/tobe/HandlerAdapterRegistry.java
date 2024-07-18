package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.exception.HandlerAdapterException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.stream.Stream;

public class HandlerAdapterRegistry {
    private final List<HandlerAdapter> handlerAdapters;

    public HandlerAdapterRegistry(final HandlerAdapter... handlerAdapters) {
        this.handlerAdapters = Stream.concat(
                Stream.of(new HandlerExecutionHandlerAdapter()),
                Stream.of(handlerAdapters)
        ).toList();
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final HandlerAdapter handlerAdapter = getHandlerAdapter(handler);
        return handlerAdapter.handle(request, response, handler);
    }

    private HandlerAdapter getHandlerAdapter(final Object handler) {
        return handlerAdapters.stream()
                .filter(handlerAdapter -> handlerAdapter.supports(handler))
                .findFirst()
                .orElseThrow(() -> new HandlerAdapterException("Unknown handler type: " + handler.getClass().getName()));
    }
}
