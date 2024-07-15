package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        final Controllers controllers = new Controllers(basePackage);
        this.handlerExecutions.putAll(createHandlerExecutions(controllers));
    }

    private Map<HandlerKey, HandlerExecution> createHandlerExecutions(final Controllers controllers) {
        return controllers.stream()
                .map(this::createHandlerExecutions)
                .flatMap(HandlerExecutions -> HandlerExecutions.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<HandlerKey, HandlerExecution> createHandlerExecutions(final Object controllerInstance) {
        return Arrays.stream(controllerInstance.getClass().getDeclaredMethods())
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
        if (methods.length == 0) {
            return RequestMethod.values();
        }
        return methods;
    }

    public Object getHandler(final HttpServletRequest request) {
        return this.handlerExecutions.get(HandlerKey.from(request));
    }
}
