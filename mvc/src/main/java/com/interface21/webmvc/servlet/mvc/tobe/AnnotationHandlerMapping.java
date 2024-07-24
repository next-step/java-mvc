package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AnnotationHandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;
    private final ControllerScanner controllerScanner = ControllerScanner.getInstance();

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
        this.handlerExecutions = new HashMap<>();
    }

    public void initialize() {
        log.info("Initialized AnnotationHandlerMapping!");
        Controllers controllers = controllerScanner.scan(basePackage);
        controllers.values().forEach(this::mappingHandler);
    }

    private void addHandlerExecution(Method method, Object controllerInstance) {
        final var requestMapping = method.getAnnotation(RequestMapping.class);
        final var handlerExecution = new HandlerExecution(controllerInstance, method);
        final var httpPath = requestMapping.value();

        Arrays.stream(requestMapping.method())
                .forEach(requestMethod -> {
                    final var handlerKey = new HandlerKey(httpPath, requestMethod);
                    handlerExecutions.put(handlerKey, handlerExecution);
                });
    }

    private void mappingHandler(Class<?> clazz) {
        try {
            final var controllerInstance = clazz.getDeclaredConstructor().newInstance();
            final var methods = clazz.getDeclaredMethods();

            Arrays.stream(methods)
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                    .forEach(method -> addHandlerExecution(method, controllerInstance));

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException ignored) {
        }
    }

    public Object getHandler(final HttpServletRequest request) {
        final var handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));

        return handlerExecutions.get(handlerKey);
    }
}
