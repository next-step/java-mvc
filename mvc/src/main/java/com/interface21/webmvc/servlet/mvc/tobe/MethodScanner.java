package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

import org.reflections.ReflectionUtils;

import com.interface21.core.util.StreamUtils;

public final class MethodScanner {

    public static <T extends Annotation> Set<Method> scanMethodsWithAnnotation(
            Class<?> clazz, Class<T> annotationType) {
        return ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(annotationType));
    }

    public static <T extends Annotation> Map<Object, List<Method>> mappingInstanceAndMethods(
            Map<Class<?>, Object> classObjectMap, Class<T> annotationType) {
        return classObjectMap.entrySet().stream()
                .map(
                        entry ->
                                Map.of(
                                        entry.getValue(),
                                        List.copyOf(
                                                scanMethodsWithAnnotation(
                                                        entry.getKey(), annotationType))))
                .reduce(StreamUtils::pullAll)
                .orElse(Map.of());
    }
}
