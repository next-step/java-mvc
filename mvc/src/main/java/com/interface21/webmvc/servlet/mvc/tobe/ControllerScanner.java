package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.core.util.ReflectionUtils;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    private final Reflections reflections;

    public ControllerScanner(Reflections reflections) {
        this.reflections = reflections;
    }

    public Map<Class<?>, Object> getControllers() {
        var controllerTypes = reflections.getTypesAnnotatedWith(Controller.class);

        return instantiateControllers(controllerTypes);
    }

    private Map<Class<?>, Object> instantiateControllers(Set<Class<?>> controllerTypes) {
        Map<Class<?>, Object> map = new HashMap<>();

        for (Class<?> controller : controllerTypes) {
            map.put(controller, initiateObject(controller));
        }
        return map;
    }

    private Object initiateObject(Class<?> controllerType) {
        try {
            Constructor<?> constructor = ReflectionUtils.accessibleConstructor(controllerType);
            return constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
