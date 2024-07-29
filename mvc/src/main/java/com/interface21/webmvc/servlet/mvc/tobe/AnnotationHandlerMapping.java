package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

public class AnnotationHandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final String[] basePackages;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(String... basePackages) {
        this.basePackages = basePackages;
        this.handlerExecutions = new HashMap<>();
    }

    public void initialize() {
        log.info("Initializing AnnotationHandlerMapping");
        Set<Class<?>> controllerClasses = scanControllers();
        for (Class<?> controllerClass : controllerClasses) {
            Object controllerInstance = createControllerInstance(controllerClass);
            mapHandlers(controllerClass, controllerInstance);
        }
        log.info("AnnotationHandlerMapping initialized {}", handlerExecutions.size());
    }

    public Object getHandler(HttpServletRequest request) {
        HandlerKey key = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
        return handlerExecutions.get(key);
    }

    private Set<Class<?>> scanControllers() {
        Reflections reflections = new Reflections((Object[]) basePackages);
        return reflections.getTypesAnnotatedWith(Controller.class);
    }

    private Object createControllerInstance(Class<?> controllerClass) {
        try {
            return controllerClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to load instance", e);
        }
    }

    private void mapHandlers(Class<?> controllerClass, Object controllerInstance) {
        String baseUrl = extractBaseUrl(controllerClass);
        for (Method method : controllerClass.getMethods()) {
            RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
            if (methodMapping != null) {
                String fullUrl = baseUrl + methodMapping.value();
                RequestMethod[] requestMethods = methodMapping.method();
                if (requestMethods.length == 0) {
                    requestMethods = RequestMethod.values();
                }
                for (RequestMethod requestMethod : requestMethods) {
                    HandlerKey key = new HandlerKey(fullUrl, requestMethod);
                    HandlerExecution execution = new HandlerExecution(controllerInstance, method);
                    handlerExecutions.put(key, execution);
                }
            }
        }
    }

    private String extractBaseUrl(Class<?> controllerClass) {
        RequestMapping classMapping = controllerClass.getAnnotation(RequestMapping.class);
        return (classMapping != null) ? classMapping.value() : "";
    }
}