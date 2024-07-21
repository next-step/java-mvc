package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.mvc.exception.ObjectTypeArgumentResolveException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.stream.Stream;

public class ObjectTypeArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return false;
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final HttpServletRequest request, final HttpServletResponse response) {
        final Class<?> parameterType = parameter.getParameterType();
        try {
            return createObject(request, parameterType);
        } catch (final InstantiationException |
                       IllegalAccessException |
                       NoSuchMethodException |
                       InvocationTargetException e) {
            throw new ObjectTypeArgumentResolveException("Failed to resolve argument", e);
        }
    }

    private Object createObject(final HttpServletRequest request, final Class<?> parameterType) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (parameterType.isRecord()) {
            return createRecord(request, parameterType);
        }

        return createObjectByNoArgConstructor(request, parameterType);
    }

    private Object createObjectByNoArgConstructor(final HttpServletRequest request, final Class<?> parameterType) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        final Object instance = parameterType.getDeclaredConstructor().newInstance();
        for (final Field field : parameterType.getDeclaredFields()) {
            field.setAccessible(true);
            final String parameterValue = request.getParameter(field.getName());
            final Object convertedValue = BasicType.convert(field.getType(), parameterValue);
            field.set(instance, convertedValue);
            field.setAccessible(false);
        }
        return instance;
    }

    private Object createRecord(final HttpServletRequest request, final Class<?> recordClass) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        final Constructor<?> constructor = findCanonicalConstructor(recordClass);
        final Object[] constructorArgs = Stream.of(constructor.getParameters())
                .map(parameter -> BasicType.convert(parameter.getType(), request.getParameter(parameter.getName())))
                .toArray();
        return constructor.newInstance(constructorArgs);
    }

    private Constructor<?> findCanonicalConstructor(final Class<?> recordClass) throws NoSuchMethodException {
        final Class<?>[] componentTypes = Arrays.stream(recordClass.getRecordComponents())
                .map(RecordComponent::getType)
                .toArray(Class<?>[]::new);
        return recordClass.getDeclaredConstructor(componentTypes);
    }
}
