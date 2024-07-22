package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HttpServletRequestArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.isAssignableFrom(HttpServletRequest.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final HttpServletRequest request, final HttpServletResponse response) {
        return request;
    }
}
