package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Parameter;
import java.util.Objects;

import static com.interface21.webmvc.servlet.mvc.tobe.annotation.SupportParameterType.isSupport;

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
        if (isSupport(parameterType)) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodParameter that = (MethodParameter) o;
        return Objects.equals(parameterType, that.parameterType) && Objects.equals(parameterName, that.parameterName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameterType, parameterName);
    }
}
