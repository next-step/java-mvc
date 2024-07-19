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

        Reflections reflections = new Reflections(basePackages);
        Set<Class<?>> controllerClasses = reflections.getTypesAnnotatedWith(Controller.class);
        for (Class<?> controllerClass : controllerClasses) {
            registerHandler(controllerClass);
        }
    }

    private void registerHandler(Class<?> controllerClass) {
        String uriPrefix = extractUriPrefix(controllerClass);
        Object controllerInstance = createControllerInstance(controllerClass);

        for (Method controllerMethod : controllerClass.getMethods()) {
            RequestMapping methodRequestMapping = controllerMethod.getAnnotation(RequestMapping.class);
            if (methodRequestMapping != null) {
                HandlerKeys handlerKeys = HandlerKeys.of(uriPrefix, methodRequestMapping);
                handlerKeys.forEach(handlerKey -> handlerExecutions.put(handlerKey, new HandlerExecution(controllerInstance, controllerMethod)));
            }
        }
    }

    private String extractUriPrefix(Class<?> controllerClass) {
        RequestMapping controllerRequestMapping = controllerClass.getAnnotation(RequestMapping.class);
        if (controllerRequestMapping == null) {
            return "";
        }
        return controllerRequestMapping.value();
    }

    private Object createControllerInstance(Class<?> controllerClass) {
        try {
            Constructor<?> controllerConstructor = controllerClass.getDeclaredConstructor();
            return controllerConstructor.newInstance();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            log.error("Controller의 기본 생성자를 찾을 수 없습니다.");
            throw new ControllerDefaultConstructorNotFoundException(e);
        }
    }

    public Object getHandler(final HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
