package com.interface21.web.parameter;

import com.interface21.web.support.TypedParsers;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class ParameterTypedParser implements ParameterParser {
    private final Class<?> type;

    public ParameterTypedParser(Class<?> type) {
        this.type = type;
    }

    @Override
    public Object parse(Method method, Parameter parameter, HttpServletRequest request) {
        if (!parameter.getType().equals(type)) {
            return null;
        }
        var constructors = type.getDeclaredConstructors();
        var fullyFieldConstructor = Arrays.stream(constructors).filter(constructor -> constructor.getParameterCount() == type.getDeclaredFields().length).findFirst();
        if (fullyFieldConstructor.isPresent()) {
            return parseFully(fullyFieldConstructor.get(), request);
        }
        var defaultConstructor = Arrays.stream(constructors).filter(constructor -> constructor.getParameterCount() == 0).findFirst();
        return defaultConstructor.map(constructor -> parseDefault(constructor, request)).orElse(null);
    }

    private Object parseFully(Constructor<?> constructor, HttpServletRequest request) {
        var parameters = constructor.getParameters();
        var args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            args[i] = TypedParsers.parse(parameters[i].getType(), request.getParameter(parameters[i].getName()));
        }
        try {
            return constructor.newInstance(args);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    private Object parseDefault(Constructor<?> constructor, HttpServletRequest request) {
        try {
            var instance = constructor.newInstance();
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(instance, TypedParsers.parse(field.getType(), request.getParameter(field.getName())));
            }
            return instance;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
}
