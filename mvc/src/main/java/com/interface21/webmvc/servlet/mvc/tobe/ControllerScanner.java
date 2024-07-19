package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import java.util.Map;
import java.util.stream.Collectors;
import org.reflections.Reflections;

public class ControllerScanner {

    private final Reflections reflections;

    public ControllerScanner(Object... basePackage) {
        this.reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getControllers() {
        return reflections.getTypesAnnotatedWith(Controller.class)
                          .stream()
                          .collect(Collectors.toMap(clazz -> clazz, this::instantiate));
    }

    private Object instantiate(Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
