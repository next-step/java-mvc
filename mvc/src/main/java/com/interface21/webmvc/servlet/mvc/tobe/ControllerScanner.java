package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.stream.Collectors;
import org.reflections.Reflections;

public class ControllerScanner {

    private final Reflections reflections;

    public ControllerScanner(String... basePackage) {
        this.reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, Object> scan() {
        return reflections.getTypesAnnotatedWith(Controller.class)
                          .stream()
                          .collect(Collectors.toMap(classes -> classes, this::createInstance));
    }

    private Object createInstance(Class<?> controller) {
        try {
            return controller.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(controller.getName() + "의 인스턴스화에 실패했습니다.", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(controller.getName() + "의 생성자에 대한 접근이 허용되지 않습니다.", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(controller.getName() + "의 생성자 호출 중 예외가 발생했습니다.", e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(controller.getName() + "에 기본 생성자가 없습니다.", e);
        }
    }
}
