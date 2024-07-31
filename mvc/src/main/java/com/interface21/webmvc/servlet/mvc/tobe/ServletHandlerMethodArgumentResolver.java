package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ServletHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        final Class<?> parameterType = parameter.getType();
        return parameterType.equals(HttpServletRequest.class) || parameterType.equals(HttpServletResponse.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ServletWebRequest webRequest) throws Exception {
        if (parameter.getType().equals(HttpServletRequest.class)) {
            return webRequest.getRequest();
        }

        return webRequest.getResponse();
    }
}
