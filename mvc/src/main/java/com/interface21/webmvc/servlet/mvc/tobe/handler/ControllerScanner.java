package com.interface21.webmvc.servlet.mvc.tobe.handler;

import com.interface21.context.stereotype.Controller;
import com.interface21.webmvc.servlet.mvc.asis.Value;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ControllerScanner {
    private Reflections reflections;
    private Map<String, Object> valueMap;

    public ControllerScanner(
            String basePackage,
            Map<String, Object> valueMap
    ) {
        this.reflections = new Reflections(basePackage);
        this.valueMap = valueMap;
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

                            Constructor<?> ctor = getConstructor(classObj);

                            try {
                                return ctor.newInstance(getValuesFromFields(classObj.getDeclaredFields()));
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

    private Constructor<?> getConstructor(Class<?> classObj) {
        try {
            return classObj.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return classObj.getConstructors()[0];
        }
    }


    private Object[] getValuesFromFields(Field[] fields) {
        List<Object> list = new ArrayList<>();
        for (Field field : fields) {
            Value value = field.getAnnotation(Value.class);
            if (value != null) {
                list.add(valueMap.get(value.value()));
            }
        }
        return list.toArray();
    }
}
