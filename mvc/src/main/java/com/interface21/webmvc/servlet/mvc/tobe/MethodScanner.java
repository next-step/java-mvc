package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

import org.reflections.ReflectionUtils;

public final class MethodScanner {

    private MethodScanner() {}

    public static <T extends Annotation> Set<Method> scanMethodsWithAnnotation(
            Class<?> clazz, Class<T> annotationType) {
        return ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(annotationType));
    }

    public static <T extends Annotation> List<InstanceMethods> mappingInstanceAndMethods(
            List<Object> instances, Class<T> annotationType) {
        return instances.stream().map(it -> InstanceMethods.of(it, annotationType)).toList();
    }

    public static <T extends Annotation> T scanAnnotation(Method method, Class<T> annotation) {
        return method.getAnnotation(annotation);
    }
}
