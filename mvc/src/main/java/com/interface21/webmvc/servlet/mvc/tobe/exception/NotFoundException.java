package com.interface21.webmvc.servlet.mvc.tobe.exception;

import jakarta.servlet.http.HttpServletRequest;

public class NotFoundException extends RuntimeException {
    public NotFoundException(HttpServletRequest request) {
        super("404 Not Found: " + request.getRequestURI());
    }
}
