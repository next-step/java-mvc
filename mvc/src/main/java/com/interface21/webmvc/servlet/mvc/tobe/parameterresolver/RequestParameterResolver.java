package com.interface21.webmvc.servlet.mvc.tobe.parameterresolver;

import com.interface21.webmvc.servlet.mvc.tobe.HandlerKey;
import com.interface21.webmvc.servlet.mvc.tobe.ParameterResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

public class RequestParameterResolver implements ParameterResolver {
    private static final List<Type> SIMPLE_TYPES = List.of(
            String.class,
            Long.class,
            Integer.class,
            int.class,
            long.class);

    private final HandlerKey handlerKey;

    public RequestParameterResolver(HandlerKey handlerKey) {
        this.handlerKey = handlerKey;
    }

    @Override
    public boolean accept(HttpServletRequest request, Parameter parameter) {
        Class<?> parameterType = parameter.getType();

        return SIMPLE_TYPES.contains(parameterType) || isResolvableObjectType(parameterType);
    }

    private boolean isResolvableObjectType(Class<?> parameterType) {
        return parameterType.getSuperclass() == Object.class &&
                Modifier.isPublic(parameterType.getModifiers()) &&
                parameterType.getDeclaredConstructors().length == 1;
    }

    @Override
    public Object resolve(HttpServletRequest request, HttpServletResponse response, Parameter parameter) {
        if (SIMPLE_TYPES.contains(parameter.getType())) {
            return resolveSimpleTypes(request, parameter);
        }

        return resolveComplexTypes(request, parameter);
    }

    private Object resolveSimpleTypes(HttpServletRequest request, Parameter parameter) {
        return getParameterValueWithType(request, parameter.getName(), parameter.getType());
    }

    private Object resolveComplexTypes(HttpServletRequest request, Parameter parameter) {
        Class<?> objectType = parameter.getType();
        Object[] arguments = collectConstructingArguments(request, objectType);
        return createObjectWithArguments(objectType, arguments);
    }

    private Object[] collectConstructingArguments(HttpServletRequest request, Class<?> parameterType) {
        Constructor<?> constructor = parameterType.getDeclaredConstructors()[0];
        Parameter[] constructorParameters = constructor.getParameters();
        return Arrays.stream(constructorParameters)
                     .map(parameter -> getParameterValueWithType(request, parameter.getName(), parameter.getType()))
                     .toArray();
    }

    private Object createObjectWithArguments(Class<?> parameterType, Object[] arguments) {
        Constructor<?> constructor = parameterType.getDeclaredConstructors()[0];
        try {
            return constructor.newInstance(arguments);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Object getParameterValueWithType(HttpServletRequest request, String name, Type parameterType) {
        String value = getParameterValue(request, name);
        if (parameterType.equals(int.class)) {
            return Integer.parseInt(value);
        }
        if (parameterType.equals(long.class)) {
            return Long.parseLong(value);
        }
        return value;
    }

    private String getParameterValue(HttpServletRequest request, String parameterName) {
        String value = request.getParameter(parameterName);
        if (value != null) {
            return value;
        }
        return handlerKey.getPathVariable(request.getRequestURI(), parameterName);
    }

}
