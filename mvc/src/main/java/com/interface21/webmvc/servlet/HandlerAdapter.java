package com.interface21.webmvc.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface HandlerAdapter {
    void handle(final HttpServletRequest request, final HttpServletResponse response, final HandlerExecution handler) throws Exception;
}
