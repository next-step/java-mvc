package com.interface21.webmvc.servlet;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public abstract class Handlers<T> {

    protected final Map<T, HandlerExecution> handlers = new HashMap<>();

    public void add(final T key, final HandlerExecution handler) {
        handlers.put(key, handler);
    }

    public HandlerExecution getHandler(final T key) {
        return handlers.get(key);
    }

    public HandlerExecution getHandler(final HttpServletRequest request) {
        return null;
    }

}
