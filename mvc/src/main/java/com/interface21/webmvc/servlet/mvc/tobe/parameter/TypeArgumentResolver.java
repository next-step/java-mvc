package com.interface21.webmvc.servlet.mvc.tobe.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public class TypeArgumentResolver implements ParameterResolver {

    @Override
    public boolean support(Parameter parameter) {
        return TypeMapper
            .getParser(parameter.getType())
            .isPresent();
    }

    @Override
    public Object parseValue(Parameter parameter, HttpServletRequest request,
        HttpServletResponse response) {

        String paramName = request.getParameter(parameter.getName());
        Object fieldValue = request.getParameter(paramName);

        return TypeMapper.parse(parameter.getType(), fieldValue);
    }
}
