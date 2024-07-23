package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class ControllerScanner {

    private ControllerScanner() {}

    public static Map<Class<?>, Object> newInstances(List<Class<?>> controllers) {
        return controllers.stream()
                .map(ControllerScanner::createInstance)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map.Entry<? extends Class<?>, ?> createInstance(Class<?> it) {
        try {
            return Map.entry(it, it.getDeclaredConstructor().newInstance());
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
