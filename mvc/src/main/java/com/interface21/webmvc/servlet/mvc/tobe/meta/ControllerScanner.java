package com.interface21.webmvc.servlet.mvc.tobe.meta;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerExecution;
import com.interface21.webmvc.servlet.mvc.tobe.HandlerKey;
import com.interface21.webmvc.servlet.mvc.tobe.exception.ControllerInitializationException;
import com.interface21.webmvc.servlet.mvc.tobe.support.ReflectionUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;

public class ControllerScanner {

    public static final String EMPTY = "";
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;

    public ControllerScanner() {
        this.handlerExecutions = new HashMap<>();
    }

    public static Map<HandlerKey, HandlerExecution> createHandlerMapping(Class<?> controller) {
        String controllerUri = getControllerUri(controller);
        Object instance = createInstance(controller);

        return Arrays.stream(
                controller.getDeclaredMethods())
            .filter(method -> method.isAnnotationPresent(RequestMapping.class))
            .collect(
                Collector.of(
                    HashMap::new,  //supplier
                    (acc, method) -> createHandlerKeys(controllerUri, method)  // accumulator
                        .forEach(key -> acc.put(key, new HandlerExecution(instance, method))),
                    (acc1, acc2) -> { // combiner
                        acc1.putAll(acc2);
                        return acc1;
                    }
                )
            );

    }

    public static List<HandlerKey> createHandlerKeys(String controllerUri, Method method) {
        String requestUri = controllerUri +
            method.getAnnotation(RequestMapping.class).value();

        return Arrays.stream(method.getAnnotation(RequestMapping.class).method())
            .map(
                requestMethod -> HandlerKey.of(requestUri, requestMethod)
            ).toList();
    }

    private static Object createInstance(Class<?> controller) {
        try {
            return controller.getDeclaredConstructor()
                .newInstance();

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new ControllerInitializationException("Controller Does not have a constructor");
        }

    }

    private static String getControllerUri(Class<?> controller) {
        return Optional
            .ofNullable(controller.getAnnotation(RequestMapping.class))
            .map(RequestMapping::value)
            .orElse(EMPTY);
    }

    public void initialize(Object... basePackages) {
        Set<Class<?>> controllers = ReflectionUtils.getAnnotatedClass(basePackages,
            Controller.class);
        controllers.forEach(
            controller -> handlerExecutions.putAll(createHandlerMapping(controller)));
    }

    public HandlerExecution get(HandlerKey key) {
        return handlerExecutions.get(key);
    }
}
