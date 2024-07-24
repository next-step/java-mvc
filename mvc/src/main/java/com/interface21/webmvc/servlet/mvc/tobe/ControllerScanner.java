package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import org.reflections.Reflections;

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
        Reflections reflections = new Reflections(basePackage);
        return new Controllers(reflections.getTypesAnnotatedWith(Controller.class));
    }
}
