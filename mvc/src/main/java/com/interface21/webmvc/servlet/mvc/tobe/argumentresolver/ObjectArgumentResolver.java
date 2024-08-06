package com.interface21.webmvc.servlet.mvc.tobe.argumentresolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ObjectArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(Parameter parameter) {
        return !ParameterClassType.isSupportedType(parameter.getType());
    }

    @Override
    public Object resolveArgument(Parameter parameter, Method method, HttpServletRequest request, HttpServletResponse response) {
        final Object object = createInstance(parameter.getType());
        for (final Field field : parameter.getType().getDeclaredFields()) {
            setField(object, request, field);
        }
        return object;
    }

    private Object createInstance(final Class<?> parameterType) {
        try {
            return parameterType.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to create instance of " + parameterType.getName());
        }
    }

    private void setField(final Object object, final HttpServletRequest httpServletRequest, final Field field) {
        field.setAccessible(true);
        String parameterValue = httpServletRequest.getParameter(field.getName());
        Object value = ParameterClassType.parse(field.getType(), parameterValue);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Failed to set value to field " + field.getName());
        }
    }
}
