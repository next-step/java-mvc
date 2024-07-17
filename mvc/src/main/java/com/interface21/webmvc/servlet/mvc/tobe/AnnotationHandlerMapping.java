package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javassist.tools.reflect.Reflection;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

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
        Reflections reflections = new Reflections(basePackage);
        reflections.getTypesAnnotatedWith(Controller.class)
                   .forEach(this::processController);
    }

    public Object getHandler(final HttpServletRequest request) {
        return handlerExecutions.get(HandlerKey.of(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }

    private void processController(Class<?> controller) {
        Object instance = createInstance(controller);
        Arrays.stream(controller.getMethods())
              .filter(this::isRequestMappingMethod)
              .flatMap(method -> createHandlerExecutions(instance, method))
              .forEach(this::putHandlerExecution);
    }

    private boolean isRequestMappingMethod(Method method) {
        return method.isAnnotationPresent(RequestMapping.class);
    }

    private Stream<Entry<HandlerKey, HandlerExecution>> createHandlerExecutions(Object instance, Method method) {
        HandlerExecution handlerExecution = makeHandlerExecution(instance, method);
        return makeHandlerKeys(handlerExecution).entrySet().stream();
    }

    private void putHandlerExecution(Map.Entry<HandlerKey, HandlerExecution> entry) {
        handlerExecutions.put(entry.getKey(), entry.getValue());
    }

    private Object createInstance(Class<?> controller) {
        try {
            return controller.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private HandlerExecution makeHandlerExecution(Object controller, Method method) {
        return new HandlerExecution(controller, method);
    }

    private Map<HandlerKey, HandlerExecution> makeHandlerKeys(final HandlerExecution handlerExecution) {
        RequestMapping requestMapping = handlerExecution.getMethod().getAnnotation(RequestMapping.class);
        return Stream.of(requestMapping.method())
                     .collect(Collectors.toMap(
                         requestMethod -> HandlerKey.of(requestMapping.value(), requestMethod),
                         requestMethod -> handlerExecution));
    }
}
