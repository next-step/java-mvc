package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.core.util.ReflectionUtils;
import com.interface21.webmvc.servlet.mvc.tobe.exception.ControllerScanningException;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerScanner {
    private final Reflections reflections;

    public ControllerScanner(Reflections reflections) {
        this.reflections = reflections;
    }

    public Map<Class<?>, Object> getControllers() {
        Map<Class<?>, Object> map = new HashMap<>();

        for (Class<?> controller : getControllerAnnotatedClasses()) {
            map.put(controller, initiateObject(controller));
        }
        return map;
    }

    private List<Class<?>> getControllerAnnotatedClasses() {
        return reflections.getTypesAnnotatedWith(Controller.class)
                          .stream()
                          .filter(this::isConcreteClass)
                          .toList();
    }

    private Object initiateObject(Class<?> controllerType) {
        Constructor<?> constructor;

        try {
            constructor = ReflectionUtils.accessibleConstructor(controllerType);
        } catch (NoSuchMethodException e) {
            throw new ControllerScanningException("생성자가 존재하지 않습니다.", e);
        }

        try {
            return constructor.newInstance();
        } catch (InstantiationException e) {
            throw new ControllerScanningException("객체 생성에 실패했습니다.", e);
        } catch (IllegalAccessException e) {
            throw new ControllerScanningException("클래스 생성자에 접근할 수 없습니다.", e);
        } catch (InvocationTargetException e) {
            throw new ControllerScanningException("생성자 실행 중 예외가 발생했습니다.", e);
        }
    }

    private boolean isConcreteClass(Class<?> it) {
        return it.getSuperclass() == Object.class && !Modifier.isAbstract(it.getModifiers());
    }
}
