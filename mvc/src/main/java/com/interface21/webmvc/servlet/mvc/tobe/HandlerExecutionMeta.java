package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.tobe.exception.ControllerInitializationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import org.reflections.Reflections;

public class HandlerExecutionMeta {

    public static final String EMPTY = "";
    private static final Map<HandlerKey, HandlerExecution> handlerExecutions = new HashMap<>();

    public HandlerExecutionMeta(Object[] basePackages) {
        Set<Class<?>> controllers = scanControllers(basePackages);
        controllers.forEach(controller -> handlerExecutions.putAll(addController(controller)));
    }

    public static Map<HandlerKey, HandlerExecution> addController(Class<?> controller) {
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

    private static Set<Class<?>> scanControllers(Object[] basePackages) {
        Reflections reflections = new Reflections(basePackages);

        return reflections.getTypesAnnotatedWith(Controller.class);
    }

    private static List<HandlerKey> createHandlerKeys(String controllerUri, Method method) {
        String requestUri = controllerUri +
            method.getAnnotation(RequestMapping.class).value();

        List<HandlerKey> keys = Arrays.stream(method.getAnnotation(RequestMapping.class).method())
            .map(
                requestMethod -> HandlerKey.of(requestUri, requestMethod)
            ).toList();

        if (keys.isEmpty()) {
            return Arrays.stream(RequestMethod.values())
                .map(
                    requestMethod -> HandlerKey.of(requestUri, requestMethod)
                ).toList();
        }

        return keys;
    }

    private static Object createInstance(Class<?> controller) {
        try {
            return controller.getDeclaredConstructor().newInstance();

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new ControllerInitializationException();
        }

    }

    private static String getControllerUri(Class<?> controller) {
        return Optional
            .ofNullable(controller.getAnnotation(RequestMapping.class))
            .map(RequestMapping::value)
            .orElse(EMPTY);
    }

    public Optional<HandlerExecution> get(HandlerKey key) {
        return Optional.ofNullable(handlerExecutions.get(key));
    }
}
