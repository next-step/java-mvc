package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HttpServletResponseParameterParser implements MethodParameterParser {

    private final Class<?> parameterType;

    public HttpServletResponseParameterParser(Class<?> parameterType) {
        this.parameterType = parameterType;
    }

    @Override
    public boolean accept() {
        return HttpServletResponse.class.isAssignableFrom(parameterType);
    }

    @Override
    public Object parseValue(HttpServletRequest request, HttpServletResponse response) {
        return response;
    }
}
