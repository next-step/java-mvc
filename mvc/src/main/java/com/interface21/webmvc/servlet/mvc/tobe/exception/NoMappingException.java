package com.interface21.webmvc.servlet.mvc.tobe.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

public class NoMappingException extends ServletException {
    public NoMappingException(HttpServletRequest request) {
        super("매핑이 없어요: " + request.getRequestURI());
    }
}
