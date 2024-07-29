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
        return instances.stream().map(it -> mappingInstanceAndMethod(it, annotationType)).toList();
    }

    public static <T extends Annotation> InstanceMethods mappingInstanceAndMethod(
            Object instance, Class<T> annotationType) {
        return InstanceMethods.of(instance, annotationType);
    }

    public static <T extends Annotation> T scanAnnotation(Method method, Class<T> annotation) {
        return method.getAnnotation(annotation);
    }

    public static Method scanMethod(Class<?> clazz, String methodName, Class<?>... args) {
        try {
            return clazz.getMethod(methodName, args);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
