package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class HttpServletResponseResolver implements ArgumentResolver {
    @Override
    public boolean supportsParameter(Parameter parameter) {
        return parameter.getType() == HttpServletResponse.class;
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Parameter parameter,
        Method method) {
        return response;
    }
}
