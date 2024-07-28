package com.interface21.webmvc.servlet;

public interface HandlerMapping<T> {
    void initialize(final T handlers);
}
