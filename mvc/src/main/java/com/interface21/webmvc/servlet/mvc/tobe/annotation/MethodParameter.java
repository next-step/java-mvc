package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Parameter;
import java.util.Objects;

import static com.interface21.webmvc.servlet.mvc.tobe.annotation.SupportParameterType.isSupport;

public class MethodParameter {

    private final Class<?> parameterType;
    private final String parameterName;
    private final PathParameter pathParameter;

    public MethodParameter(Class<?> parameterType, String parameterName, PathParameter pathParameter) {
        this.parameterType = parameterType;
        this.parameterName = parameterName;
        this.pathParameter = pathParameter;
    }

    public static MethodParameter of(String urlPattern, Parameter parameter) {
        return new MethodParameter(parameter.getType(), parameter.getName(), PathParameter.of(urlPattern, parameter));
    }

    public Object parseValue(HttpServletRequest request, HttpServletResponse response) {
        if (HttpServletRequest.class.isAssignableFrom(parameterType)) {
            return request;
        }
        if (HttpServletResponse.class.isAssignableFrom(parameterType)) {
            return response;
        }
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
        return Objects.equals(parameterType, that.parameterType) && Objects.equals(parameterName, that.parameterName) && Objects.equals(pathParameter, that.pathParameter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameterType, parameterName, pathParameter);
    }
}
