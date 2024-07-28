package com.interface21.webmvc.servlet;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class ComponentScanner {

    public static List<HandlerComponent> scan(final Class<? extends Annotation> type, final Object... basePackage) {
        var reflections = new Reflections(basePackage).getTypesAnnotatedWith(type);
        return reflections.stream().map(ComponentScanner::mapping).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private static HandlerComponent mapping(final Class<?> componentType) {
        try {
            var instance = componentType.getDeclaredConstructor().newInstance();
            return new HandlerComponent(componentType, instance);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException ignored) {
        }
        return null;
    }
}
