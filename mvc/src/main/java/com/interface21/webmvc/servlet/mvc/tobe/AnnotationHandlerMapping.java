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
import java.util.*;
import java.util.Map.Entry;
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
        handlerExecutions.putAll(mappingBasePackageHandlers());
    }

    private Map<HandlerKey, HandlerExecution> mappingBasePackageHandlers() {
        return Arrays.stream(basePackage)
                .map(this::mappingHandler)
                .flatMap(handlers -> handlers.entrySet().stream())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    private Map<HandlerKey, HandlerExecution> mappingHandler(Object basePackage1) {
        try {
            Map<HandlerKey, HandlerExecution> handlerExecutions = new HashMap<>();
            Reflections reflections = new Reflections(basePackage1);
            Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);
            for (Class<?> controller : controllers) {
                Object controllerInstance = controller.getConstructor().newInstance();
                List<Method> methods = Arrays.stream(controller.getMethods())
                        .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                        .toList();
                for (Method method : methods) {
                    RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
                    String url = requestMapping.value();
                    RequestMethod[] requestMethods = requestMapping.method();
                    for (RequestMethod requestMethod : requestMethods) {
                        HandlerKey handlerKey = new HandlerKey(url, requestMethod);
                        handlerExecutions.put(handlerKey, new HandlerExecution(controllerInstance, method.getName()));
                    }
                }
            }
            return handlerExecutions;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getHandler(final HttpServletRequest request) {
        HandlerKey handlerKey = createHandlerKey(request);
        if (handlerExecutions.containsKey(handlerKey)) {
            return handlerExecutions.get(handlerKey);
        }
        throw new IllegalArgumentException("지원하지 않는 request 요청입니다.");
    }

    private HandlerKey createHandlerKey(HttpServletRequest request) {
        return new HandlerKey(
                request.getRequestURI(),
                RequestMethod.valueOf(request.getMethod())
        );
    }
}
