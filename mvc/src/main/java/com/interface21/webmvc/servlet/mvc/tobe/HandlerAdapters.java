package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.mvc.HandlerAdapter;
import jakarta.servlet.ServletException;

import java.util.ArrayList;
import java.util.List;

public class HandlerAdapters {
    private final List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    public void initialize() {
        handlerAdapters.add(new AnnotationHandlerAdapter());
    }

    public void addAdapter(final HandlerAdapter handlerAdapter) {
        handlerAdapters.add(handlerAdapter);
    }

    public HandlerAdapter getHandlerAdapter(final Object handler) throws ServletException {
        for (final HandlerAdapter adapter : this.handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }

        throw new ServletException("No adapter for handler [" + handler +
   				"]: The DispatcherServlet configuration needs to include a HandlerAdapter that supports this handler");
   	}
}
