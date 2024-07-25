package com.interface21.webmvc.servlet.mvc.tobe;

import java.util.ArrayList;
import java.util.List;

import org.reflections.Reflections;

import com.interface21.context.stereotype.Controller;

public final class ControllerScanner {

    private ControllerScanner() {}

    public static List<Object> newInstances(List<Class<?>> controllers) {
        return controllers.stream().map(ControllerScanner::createInstance).toList();
    }

    public static Object createInstance(Class<?> it) {
        try {
            return it.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Class<?>> scanControllers(Object[] basePackages) {
        // Reflections 객체를 사용하여 패키지 스캔
        var reflections = new Reflections(basePackages);

        return new ArrayList<>(reflections.getTypesAnnotatedWith(Controller.class));
    }
}
