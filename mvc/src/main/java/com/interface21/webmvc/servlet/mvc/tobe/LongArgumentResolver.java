package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LongArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType() == long.class || parameter.getParameterType() == Long.class;
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final HttpServletRequest request, final HttpServletResponse response) {
        return Long.parseLong(request.getParameter(parameter.getParameterName()));
    }
}
