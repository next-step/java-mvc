package com.interface21.webmvc.servlet.mvc.tobe.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public class HttpServletResponseResolver implements ParameterResolver{

    @Override
    public boolean support(Parameter parameter) {
        return HttpServletResponse.class
            .isAssignableFrom(parameter.getType());
    }

    @Override
    public Object parseValue(Parameter parameter, HttpServletRequest request, HttpServletResponse response) {
        return response;
    }
}
