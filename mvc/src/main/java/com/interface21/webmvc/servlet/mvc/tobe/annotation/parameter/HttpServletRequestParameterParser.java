package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HttpServletRequestParameterParser implements MethodParameterParser {

    private final Class<?> parameterType;

    public HttpServletRequestParameterParser(Class<?> parameterType) {
        this.parameterType = parameterType;
    }

    @Override
    public boolean accept() {
        return HttpServletRequest.class.isAssignableFrom(parameterType);
    }

    @Override
    public Object parseValue(HttpServletRequest request, HttpServletResponse response) {
        return request;
    }
}
