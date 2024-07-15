package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.exception.AnnotationHandlerMappingInitializeException;
import jakarta.servlet.http.HttpServletRequest;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
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
        log.info("Initialized AnnotationHandlerMapping!");
        final Map<Class<?>, Object> controllers = initializeControllers();
        this.handlerExecutions.putAll(createHandlerExecutions(controllers));
    }

    private Map<Class<?>, Object> initializeControllers() {
        final Reflections reflections = new Reflections(basePackage);
        return reflections.getTypesAnnotatedWith(Controller.class).stream()
                .collect(Collectors.toMap(Function.identity(), this::createInstance));
    }

    private Object createInstance(final Class<?> controllerClass) {
        try {
            final Constructor<?> declaredConstructor = controllerClass.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            final Object controllerInstance = declaredConstructor.newInstance();
            declaredConstructor.setAccessible(false);
            return controllerInstance;
        } catch (final NoSuchMethodException exception) {
            throw new AnnotationHandlerMappingInitializeException("No-arg constructor is not found from " + controllerClass.getName(), exception);
        } catch (final InvocationTargetException | InstantiationException | IllegalAccessException exception) {
            throw new AnnotationHandlerMappingInitializeException("Fail to create instance for " + controllerClass.getName(), exception);
        }
    }

    private Map<HandlerKey, HandlerExecution> createHandlerExecutions(final Map<Class<?>, Object> controllers) {
        return controllers.entrySet().stream()
                .map(entry -> createHandlerExecutions(entry.getKey(), entry.getValue()))
                .flatMap(HandlerExecutions -> HandlerExecutions.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<HandlerKey, HandlerExecution> createHandlerExecutions(final Class<?> controllerClass, final Object controllerInstance) {
        return Arrays.stream(controllerClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .map(method -> createHandlers(controllerInstance, method))
                .flatMap(handlers -> handlers.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<HandlerKey, HandlerExecution> createHandlers(final Object controllerInstance, final Method requestMappingMethod) {
        final RequestMapping requestMapping = requestMappingMethod.getDeclaredAnnotation(RequestMapping.class);
        return Arrays.stream(initRequestMethods(requestMapping))
                .map(method -> new HandlerKey(requestMapping.value(), method))
                .collect(Collectors.toMap(
                        Function.identity(),
                        handlerKey -> new HandlerExecution(controllerInstance, requestMappingMethod)
                ));
    }

    private RequestMethod[] initRequestMethods(final RequestMapping requestMapping) {
        final RequestMethod[] methods = requestMapping.method();
        if (methods.length > 0) {
            return methods;
        }
        return RequestMethod.values();
    }

    public Object getHandler(final HttpServletRequest request) {
        return this.handlerExecutions.get(HandlerKey.from(request));
    }
}
