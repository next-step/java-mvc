package com.interface21.webmvc.servlet.mvc.tobe.argumentresolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class SimpleObjectArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(Parameter parameter) {
        return ParameterClassType.isSupportedType(parameter.getType());
    }

    @Override
    public Object resolveArgument(Parameter parameter, Method method, HttpServletRequest request, HttpServletResponse response) {
        return ParameterClassType.parse(parameter.getType(), request.getParameter(parameter.getName()));
    }
}
