package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
        this.handlerExecutions = new HashMap<>();
    }

    public void initialize() {
        final Reflections reflections = new Reflections(basePackage);
        final Set<Class<?>> handlerClasses = reflections.getTypesAnnotatedWith(Controller.class);

        final Map<HandlerKey, HandlerExecution> map = handlerClasses.stream()
                        .map(this::controllerToHandlerExecutions)
                        .flatMap(handlerExecutionMap -> handlerExecutionMap.entrySet().stream())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        handlerExecutions.putAll(map);

        log.info("Initialized AnnotationHandlerMapping!");
    }

    private Map<HandlerKey, HandlerExecution> controllerToHandlerExecutions(final Class<?> controller){
        final Object instance = getInstanceOfController(controller);
        final Set<Method> methods = ReflectionUtils.getAllMethods(controller, ReflectionUtils.withAnnotation(RequestMapping.class));

        return methods.stream()
                    .map(method -> methodToHandlerExecutions(instance, method))
                    .flatMap(map -> map.entrySet().stream())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Object getInstanceOfController(final Class<?> controller) {
        try {
            return controller.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException("Controller instance creation failed");
        }
    }

    private Map<HandlerKey, HandlerExecution> methodToHandlerExecutions(final Object instance, final Method method) {
        final RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
        final RequestMethod[] methodTypes = requestMapping.method();

        return Arrays.stream(methodTypes)
                .map(methodType -> new HandlerKey(requestMapping.value(), methodType))
                .collect(Collectors.toMap(
                        handlerKey -> handlerKey,
                        handlerKey -> new HandlerExecution(instance, method.getName())
                        )
                );
    }

    public Object getHandler(final HttpServletRequest request) {
        final HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
        return handlerExecutions.get(handlerKey);
    }
}
