package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> handlerClasses = reflections.getTypesAnnotatedWith(Controller.class);

        Map<HandlerKey, HandlerExecution> basePackageHandler = handlerClasses.stream()
                .map(this::parseControllerHandler)
                .flatMap(handlers -> handlers.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        handlerExecutions.putAll(basePackageHandler);
        log.info("Initialized AnnotationHandlerMapping!");
    }

    public Object getHandler(final HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }

    private Map<HandlerKey, HandlerExecution> parseControllerHandler(Class<?> clazz) {
        Object controllerInstance;
        try {
            controllerInstance = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("클래스의 인스턴스를 생성할 수 없습니다.", e);
        }

            return Arrays.stream(clazz.getMethods())
                    .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                    .map(method -> parseMethodHandler(method, controllerInstance))
                    .flatMap(handlers -> handlers.entrySet().stream())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<HandlerKey, HandlerExecution> parseMethodHandler(Method method, Object controllerInstance) {
        RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
        String url = requestMapping.value();
        HandlerExecution handlerExecution = new HandlerExecution(controllerInstance, method);

        return Arrays.stream(requestMapping.method())
                .map(requestMethod -> new HandlerKey(url, requestMethod))
                .collect(Collectors.toMap(
                        handlerKey -> handlerKey,
                        handlerKey -> handlerExecution)
                );
    }
}
