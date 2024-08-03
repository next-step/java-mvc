package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;

import com.interface21.core.TypeResolver;

public class ObjectMethodArgumentResolver implements MethodArgumentResolver {

    @Override
    public boolean supports(MethodParameter methodParameter) {
        return !methodParameter.isNativeType();
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ServletRequestResponse request) {

        Constructor<?> constructor = methodParameter.getDeclaredType().getConstructors()[0];
        Parameter[] parameters = constructor.getParameters();

        var args =
                Arrays.stream(parameters)
                        .map(
                                parameter ->
                                        TypeResolver.resolve(
                                                parameter,
                                                request.getParameter(parameter.getName())))
                        .toArray();

        try {
            return constructor.newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("failed to create object", e);
        }
    }
}
