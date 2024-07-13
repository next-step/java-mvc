package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ControllerScanner {

    private final Reflections reflections;

    public ControllerScanner(Object... basePackage) {
        this.reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getControllers() {
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);
        return controllers.stream()
                .collect(Collectors.toMap(controller -> controller, this::createNoArgInstance));
    }

    private Object createNoArgInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor()
                    .newInstance();
        } catch (InstantiationException e) {
            throw new IllegalStateException("클래스가 아닌 추상클래스, 인터페이스는 생성자를 만들 수 없습니다.");
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("클래스의 생성자에 접근할 수 없습니다.");
        } catch (InvocationTargetException e) {
            throw new IllegalStateException("생성자에서 예외가 발생했습니다.", e);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("지원하는 생성자가 존재하지 않습니다.");
        }
    }
}
