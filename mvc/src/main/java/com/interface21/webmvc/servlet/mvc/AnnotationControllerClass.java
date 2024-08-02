package com.interface21.webmvc.servlet.mvc;

import com.interface21.web.bind.annotation.RequestMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class AnnotationControllerClass {
    private final Class<?> clazz;
    private Method[] requestMappingMethods = new Method[0];

    public AnnotationControllerClass(Class<?> clazz) {
        this.clazz = clazz;
        this.requestMappingMethods = findRequestMappingMethod();
    }

    public Optional<Object> getNewInstance() {
        try {
            return Optional.of(clazz.getDeclaredConstructor().newInstance());
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new ClassInstantiationException("Failed to instantiate object of type: " + clazz.getName(), e);
        }
    }

    public Method[] getRequestMappingMethod() {
        return requestMappingMethods;
    }

    private Method[] findRequestMappingMethod() {
        return Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .toArray(Method[]::new);
    }
}
