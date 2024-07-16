package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import com.interface21.context.stereotype.Controller;
import org.reflections.Reflections;

import java.util.List;
import java.util.Set;

public class ControllerScanner {

    private final Reflections reflections;

    public ControllerScanner(Object... basePackage) {
        this.reflections = new Reflections(basePackage);
    }

    public List<ControllerInstance> getControllers() {
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);
        return controllers.stream()
                .map(ControllerInstance::new)
                .toList();
    }
}
