package com.interface21.webmvc.servlet.mvc.tobe;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.stream.Collectors;

public class BeanScanner {

    private final Reflections reflections;

    public BeanScanner(final Object[] basePackage) {
        reflections = new Reflections(basePackage);
    }

    public Set<Class<?>> scanClassesTypeAnnotatedWith(Class<? extends Annotation> annotation) {
        return reflections.getTypesAnnotatedWith(annotation)
                .stream()
                .filter(type -> (!type.isAnnotation() && !type.isInterface()))
                .collect(Collectors.toSet());
    }
}
