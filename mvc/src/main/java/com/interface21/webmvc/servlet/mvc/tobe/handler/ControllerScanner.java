package com.interface21.webmvc.servlet.mvc.tobe.handler;

import com.interface21.context.stereotype.Controller;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecution;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ControllerScanner {
    private Reflections reflections;

    public ControllerScanner(String basePackage) {
        this.reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getControllers() {
        Set<Class<?>> set = reflections.getTypesAnnotatedWith(Controller.class);
        return instantiateControllers(set);
    }

    private Map<Class<?>, Object> instantiateControllers(Set<Class<?>> classSet) {
        return classSet.stream()
                .collect(Collectors.toMap(
                        classObj -> classObj,
                        classObj -> {
                            Constructor<?> ctor = null;
                            try {
                                ctor = classObj.getConstructor();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                            try {
                                return ctor.newInstance();
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            throw new RuntimeException();
                        }
                ));
    }
}
