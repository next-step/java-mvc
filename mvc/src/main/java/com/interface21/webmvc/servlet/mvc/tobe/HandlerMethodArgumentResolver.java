package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface HandlerMethodArgumentResolver {
    boolean supportsParameter(MethodParameter parameter);

    Object resolveArgument(MethodParameter parameter, HttpServletRequest request, HttpServletResponse response);
}
