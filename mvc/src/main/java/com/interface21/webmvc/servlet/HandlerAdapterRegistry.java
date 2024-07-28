package com.interface21.webmvc.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.List;

public class HandlerAdapterRegistry {
    private final List<HandlerAdapter> adapters = new ArrayList<>();

    public void addAdapter(final HandlerAdapter adapter) {
        adapters.add(adapter);
    }

    public void handle(final HttpServletRequest request, final HttpServletResponse response, final HandlerExecution handler) throws Exception {
        adapters.get(0).handle(request, response, handler);
    }
}
