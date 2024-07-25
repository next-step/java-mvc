package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.interface21.core.util.StreamUtils;

public class InstanceMethods {

    private final Object instance;
    private final Set<Method> methods;

    public InstanceMethods(Object clazz, Set<Method> methods) {
        this.instance = clazz;
        this.methods = methods;
    }

    public static <T extends Annotation> InstanceMethods of(
            Object instance, Class<T> annotationType) {
        return new InstanceMethods(
                instance,
                MethodScanner.scanMethodsWithAnnotation(instance.getClass(), annotationType));
    }

    public Stream<Map.Entry<Object, Method>> flattenMethods() {
        return StreamUtils.flattenValues(Map.entry(instance, new ArrayList<>(methods)));
    }
}
