package com.interface21.webmvc.servlet.mvc.tobe.parameterresolver;

import com.interface21.core.util.ConversionUtil;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerKey;
import com.interface21.webmvc.servlet.mvc.tobe.ParameterResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class StructuredRequestParameterParameterResolver implements ParameterResolver {

    @Override
    public boolean accept(HttpServletRequest request, Parameter parameter) {
        Class<?> parameterType = parameter.getType();

        return parameterType.getSuperclass() == Object.class &&
                Modifier.isPublic(parameterType.getModifiers()) &&
                parameterType.getDeclaredConstructors().length > 0;
    }

    @Override
    public Object resolve(HttpServletRequest request,
                          HttpServletResponse response,
                          Parameter parameter,
                          HandlerKey handlerKey) {

        Constructor<?> constructor = parameter.getType().getDeclaredConstructors()[0];
        Object[] parameterValues = collectConstructingArguments(request, constructor);

        return createNewInstance(constructor, parameterValues);
    }

    private Object[] collectConstructingArguments(HttpServletRequest request, Constructor<?> constructor) {
        Parameter[] constructorParameters = constructor.getParameters();
        return Arrays.stream(constructorParameters)
                     .map(parameter -> resolveParameter(request, parameter))
                     .toArray();
    }

    private static Object resolveParameter(HttpServletRequest request, Parameter parameter) {
        String rawParameterValue = request.getParameter(parameter.getName());
        Class<?> parameterType = parameter.getType();

        return ConversionUtil.boxPrimitiveValue(rawParameterValue, parameterType);
    }

    private Object createNewInstance(Constructor<?> constructor, Object[] arguments) {
        try {
            return constructor.newInstance(arguments);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
