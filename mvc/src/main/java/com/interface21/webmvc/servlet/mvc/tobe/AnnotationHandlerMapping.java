package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
        this.handlerExecutions = new HashMap<>();
    }

    public void initialize() {
        log.info("Initialized AnnotationHandlerMapping!");
        Set<Class<?>> controllers = new Reflections(basePackage).getTypesAnnotatedWith(Controller.class);
        controllers.forEach(this::mappingHandler);
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
            // nothing
        }
    }

    private void addHandlerExecution(Method method, Object controllerInstance) {
        final var requestMapping = method.getAnnotation(RequestMapping.class);
        final var handlerKey = new HandlerKey(requestMapping.value(), requestMapping.method()[0]);
        final var handlerExecution = new HandlerExecution(controllerInstance, method);

        handlerExecutions.put(handlerKey, handlerExecution);
    }

    public Object getHandler(final HttpServletRequest request) {
        final var handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));

        return handlerExecutions.get(handlerKey);
    }
}
