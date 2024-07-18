package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Parameter;

public class MethodParameter {

    private final Class<?> parameterType;
    private final String parameterName;

    public MethodParameter(Class<?> parameterType, String parameterName) {
        this.parameterType = parameterType;
        this.parameterName = parameterName;
    }

    public MethodParameter(Parameter parameter) {
        this(parameter.getType(), parameter.getName());
    }

    public Object parseValue(HttpServletRequest request) {
        if (parameterType.isPrimitive()) {
            return request.getAttribute(parameterName);
        }
        throw new IllegalStateException("실행 불가");
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public String getParameterName() {
        return parameterName;
    }
}
