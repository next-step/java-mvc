package com.interface21.webmvc.servlet.mvc.tobe.argumentresolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class ObjectArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(Parameter parameter) {
        return !ParameterClassType.isSupportedType(parameter.getType());
    }

    @Override
    public Object resolveArgument(Parameter parameter, Method method, HttpServletRequest request, HttpServletResponse response) {
        return createInstance(parameter, request);
    }

    private Object createInstance(final Parameter parameter, final HttpServletRequest request) {
        try {
            final Constructor<?> constructor = constructor(parameter);
            final Object[] arguments = constructorArguments(constructor.getParameters(), request);
            return constructor.newInstance(arguments);
        } catch (final Exception e) {
            throw new IllegalArgumentException("Failed to create instance : " + parameter.getType());
        }
    }

    private Constructor<?> constructor(final Parameter parameter) {
        return parameter.getType()
                .getConstructors()[0];
    }

    private Object[] constructorArguments(final Parameter[] parameters, final HttpServletRequest request) {
        return Arrays.stream(parameters)
                .map(parameter -> ParameterClassType.parse(parameter.getType(), request.getParameter(parameter.getName())))
                .toArray();
    }
}
