package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ControllerScanner {

    private final Reflections reflections;
    private final List<Object> controllerBeans;

    public ControllerScanner(final Object[] basePackage) {
        reflections = new Reflections(basePackage);
        controllerBeans = createControllerBeans();
    }

    private List<Object> createControllerBeans() {
        return scanClassesTypeAnnotatedWith(Controller.class)
                .stream()
                .map(this::createNewInstance)
                .toList();
    }

    private Set<Class<?>> scanClassesTypeAnnotatedWith(Class<? extends Annotation> annotation) {
        return reflections.getTypesAnnotatedWith(annotation)
                .stream()
                .filter(type -> (!type.isAnnotation() && !type.isInterface()))
                .collect(Collectors.toSet());
    }

    private Object createNewInstance(final Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object> getControllerBeans() {
        return Collections.unmodifiableList(controllerBeans);
    }
}
