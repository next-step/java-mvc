package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.webmvc.servlet.mvc.tobe.ControllerInstance;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Collectors;
import org.reflections.Reflections;

public class ControllerScanner {

    private final Reflections reflections;

    public ControllerScanner(Object... basePackage) {
        this.reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, ControllerInstance> scan() {
        return reflections.getTypesAnnotatedWith(Controller.class)
                          .stream()
                          .collect(Collectors.toMap(clazz -> clazz, ControllerInstance::new));
    }
}
