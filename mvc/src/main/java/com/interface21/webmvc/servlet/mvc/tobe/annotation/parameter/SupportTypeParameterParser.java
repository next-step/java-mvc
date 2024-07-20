package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Parameter;

import static com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter.SupportParameterType.isSupport;
import static com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter.SupportParameterType.parse;

public class SupportTypeParameterParser implements MethodParameterParser {

    private final Class<?> parameterType;
    private final String parameterName;

    private SupportTypeParameterParser(Class<?> parameterType, String parameterName) {
        this.parameterType = parameterType;
        this.parameterName = parameterName;
    }

    public static SupportTypeParameterParser from(Parameter parameter) {
        return new SupportTypeParameterParser(parameter.getType(), parameter.getName());
    }

    @Override
    public boolean accept() {
        return isSupport(parameterType);
    }

    @Override
    public Object parseValue(HttpServletRequest request, HttpServletResponse response) {
        return parseToType(request.getParameter(parameterName));
    }

    private Object parseToType(String parseValue) {
        return parse(parameterType, parseValue);
    }
}
