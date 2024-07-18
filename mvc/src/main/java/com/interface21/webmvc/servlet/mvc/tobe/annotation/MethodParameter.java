package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import java.lang.reflect.Parameter;

public class MethodParameter {

    private final Class<?> parameterType;
    private final String parameterName;

    public MethodParameter(Parameter parameter) {
        this.parameterType = parameter.getType();
        this.parameterName = parameter.getName();
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public String getParameterName() {
        return parameterName;
    }
}
