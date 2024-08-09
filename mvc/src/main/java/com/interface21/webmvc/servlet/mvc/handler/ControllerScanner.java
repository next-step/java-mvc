package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.context.stereotype.Controller;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ControllerScanner {
    private final Reflections reflections;

    public ControllerScanner(Object... basePackage) {
        this.reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getControllers() {
        Set<Class<?>> handlerClasses = reflections.getTypesAnnotatedWith(Controller.class);
        return instantiateControllers(handlerClasses);
    }

    private Map<Class<?>, Object> instantiateControllers(Set<Class<?>> handlerClasses) {
        return handlerClasses.stream()
                .map(this::instantiateController)
                .collect(Collectors.toMap(controller -> controller.getClass(), controller -> controller));
    }

    private Object instantiateController(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("클래스의 인스턴스를 생성할 수 없습니다.", e);
        }
    }
}
