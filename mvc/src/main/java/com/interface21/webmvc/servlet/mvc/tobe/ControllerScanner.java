package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import org.reflections.Reflections;

import java.util.stream.Collectors;

public final class ControllerScanner implements Scanner {
    private static final ControllerScanner instance;

    static {
        instance = new ControllerScanner();
    }

    private ControllerScanner() {
    }

    public static ControllerScanner getInstance() {
        return instance;
    }

    @Override
    public Controllers scan(Object... basePackage) {
        final var reflections = new Reflections(basePackage);
        final var controllerClasses = reflections.getTypesAnnotatedWith(Controller.class).stream()
                .map(AnnotationControllerClass::new)
                .collect(Collectors.toSet());

        return new Controllers(controllerClasses);
    }
}
