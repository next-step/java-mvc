package com.interface21.webmvc.servlet.mvc.tobe.support;

import java.lang.annotation.Annotation;
import java.util.Set;
import org.reflections.Reflections;

public final class ReflectionUtils {

    public static Set<Class<?>> getAnnotatedClass(Object[] basePackages,
        Class<? extends Annotation> clazz) {
        Reflections reflections = new Reflections(basePackages);

        return reflections.getTypesAnnotatedWith(clazz);
    }
}
