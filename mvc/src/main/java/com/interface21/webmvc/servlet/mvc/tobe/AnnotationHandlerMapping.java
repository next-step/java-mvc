package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.exception.AnnotationHandlerMappingException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Collections;
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
        final Controllers controllers = new Controllers(basePackage);
        this.handlerExecutions.putAll(createHandlerExecutions(controllers));
    }

    private Map<HandlerKey, HandlerExecution> createHandlerExecutions(final Controllers controllers) {
        final Map<HandlerKey, HandlerExecution> executions = new HashMap<>();
        for (final Object controller : controllers) {
            executions.putAll(createHandlerExecutions(controller));
        }
        return executions;
    }

    private Map<HandlerKey, HandlerExecution> createHandlerExecutions(final Object controllerInstance) {
        final Map<HandlerKey, HandlerExecution> executions = new HashMap<>();
        final Method[] methods = controllerInstance.getClass().getMethods();

        for (final Method method : methods) {
            executions.putAll(createHandlers(controllerInstance, method));
        }

        return executions;
    }

    private Map<HandlerKey, HandlerExecution> createHandlers(final Object controllerInstance, final Method requestMappingMethod) {
        if (!requestMappingMethod.isAnnotationPresent(RequestMapping.class)) {
            return Collections.emptyMap();
        }
        final Map<HandlerKey, HandlerExecution> handlers = new HashMap<>();
        final RequestMapping requestMapping = requestMappingMethod.getAnnotation(RequestMapping.class);
        final String url = requestMapping.value();

        for (final RequestMethod requestMethod : initRequestMethods(requestMapping)) {
            handlers.put(new HandlerKey(url, requestMethod), new HandlerExecution(controllerInstance, requestMappingMethod));
        }

        return handlers;
    }

    private RequestMethod[] initRequestMethods(final RequestMapping requestMapping) {
        final RequestMethod[] methods = requestMapping.method();
        if (methods.length == 0) {
            return RequestMethod.values();
        }
        return methods;
    }

    public Object getHandler(final HttpServletRequest request) {
        final HandlerKey handlerKey = HandlerKey.from(request);
        if (handlerExecutions.containsKey(handlerKey)) {
            return this.handlerExecutions.get(handlerKey);
        }
        throw new AnnotationHandlerMappingException("No handler found");
    }
}
