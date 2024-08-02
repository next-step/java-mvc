package com.interface21.webmvc.servlet.mvc;

import jakarta.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    HandlerExecution getHandler(HttpServletRequest request);

    void initialize();
}