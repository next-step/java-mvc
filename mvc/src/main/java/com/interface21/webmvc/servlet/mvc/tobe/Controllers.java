package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.webmvc.servlet.mvc.exception.ControllerInitializeException;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

public class Controllers implements Iterable<Object> {

    public final List<Object> controllers;

    public Controllers(final Object basePackage, final Object... basePackages) {
        this.controllers = initialize(basePackage, basePackages);
    }

    private List<Object> initialize(final Object basePackage, final Object... basePackages) {
        final Reflections reflections = new Reflections(basePackage, basePackages);
        return reflections.getTypesAnnotatedWith(Controller.class).stream()
                .map(this::createInstance)
                .toList();
    }

    private Object createInstance(final Class<?> controllerClass) {
        try {
            final Constructor<?> declaredConstructor = controllerClass.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            final Object controllerInstance = declaredConstructor.newInstance();
            declaredConstructor.setAccessible(false);
            return controllerInstance;
        } catch (final NoSuchMethodException exception) {
            throw new ControllerInitializeException("No-arg constructor is not found from " + controllerClass.getName(), exception);
        } catch (final InvocationTargetException | InstantiationException | IllegalAccessException exception) {
            throw new ControllerInitializeException("Fail to create instance for " + controllerClass.getName(), exception);
        }
    }

    @Override
    public Iterator<Object> iterator() {
        return controllers.iterator();
    }

}
