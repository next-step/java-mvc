package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ControllerScanner {

    private final Map<Class<?>, Object> controllers = new HashMap<>();

    public ControllerScanner(final Object... basePackage) {
        var reflections = new Reflections(basePackage).getTypesAnnotatedWith(Controller.class);
        reflections.forEach(this::mappingController);
    }

    private void mappingController(Class<?> controller) {
        try {
            var controllerInstance = controller.getDeclaredConstructor().newInstance();
            controllers.put(controller, controllerInstance);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException ignored) {
        }
    }

    public Map<Class<?>, Object> getControllers() {
        return Collections.unmodifiableMap(controllers);
    }
}
