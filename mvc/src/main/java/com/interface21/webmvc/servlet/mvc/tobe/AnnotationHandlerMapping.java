package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AnnotationHandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackages;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(final Object... basePackages) {
        this.basePackages = basePackages;
        this.handlerExecutions = new HashMap<>();
    }

    public void initialize() {
        log.info("Initialized AnnotationHandlerMapping!");
        try {
            Reflections reflections = new Reflections(basePackages);
            Set<Class<?>> controllerClasses = reflections.getTypesAnnotatedWith(Controller.class);
            for (Class<?> controllerClass : controllerClasses) {
                Constructor<?> controllerConstructor = controllerClass.getDeclaredConstructor();
                Object controllerInstance = controllerConstructor.newInstance();
                Method[] controllerMethods = controllerClass.getMethods();

                String uriPrefix = extractUriPrefix(controllerClass);
                for (Method controllerMethod : controllerMethods) {
                    RequestMapping methodRequestMapping = controllerMethod.getAnnotation(RequestMapping.class);
                    if (methodRequestMapping != null) {
                        List<HandlerKey> handlerKeys = createHandlerKeys(uriPrefix, methodRequestMapping);
                        handlerKeys.forEach(handlerKey -> handlerExecutions.put(handlerKey, new HandlerExecution(controllerInstance, controllerMethod)));
                    }
                }
            }
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private String extractUriPrefix(Class<?> controllerClass) {
        RequestMapping controllerRequestMapping = controllerClass.getAnnotation(RequestMapping.class);
        if (controllerRequestMapping == null) {
            return "";
        }
        return controllerRequestMapping.value();
    }

    private List<HandlerKey> createHandlerKeys(String uriPrefix, RequestMapping controllerRequestMapping) {
        String uri = uriPrefix + controllerRequestMapping.value();
        RequestMethod[] requestMethods = controllerRequestMapping.method();
        if (requestMethods == null || requestMethods.length == 0) {
            return createHandlerKeys(RequestMethod.values(), uri);
        }

        return createHandlerKeys(requestMethods, uri);
    }

    private List<HandlerKey> createHandlerKeys(RequestMethod[] requestMethods, String uri) {
        return Arrays.stream(requestMethods)
                .map(requestMethod -> new HandlerKey(uri, requestMethod))
                .toList();
    }

    public Object getHandler(final HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
