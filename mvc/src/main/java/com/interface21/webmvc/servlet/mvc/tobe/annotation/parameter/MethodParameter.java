package com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Objects;

import static com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter.SupportParameterType.isSupport;

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
        if (pathParameter.isPathVariable()) {
            return parseParameterType(pathParameter.parsePathVariable(request.getRequestURI(), parameterName));
        }
        if (isSupport(parameterType)) {
            return request.getAttribute(parameterName);
        }

        try {
            Object instance = parameterType.getDeclaredConstructor()
                    .newInstance();
            for (Field field : parameterType.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(instance, request.getAttribute(field.getName()));
                field.setAccessible(false);
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("객체 파싱에서 문제가 발생했습니다.", e);
        }
    }

    private Object parseParameterType(String pathValue) {
        if (parameterType.equals(int.class) || parameterType.equals(Integer.class)) {
            return Integer.parseInt(pathValue);
        }
        if (parameterType.equals(long.class) || parameterType.equals(Long.class)) {
            return Long.parseLong(pathValue);
        }
        return pathValue;
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
