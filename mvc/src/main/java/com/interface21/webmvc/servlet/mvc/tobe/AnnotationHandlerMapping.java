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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class AnnotationHandlerMapping {
    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
        this.handlerExecutions = new HashMap<>();
    }

    public void initialize() {
        registerRequestMapping();

        log.info("Initialized AnnotationHandlerMapping!");
    }

    private void registerRequestMapping() {
        Set<Class<?>> controllers = getControllers(new Reflections(basePackage));
        Stream<Method> requestMappedMethods = getRequestMappings(controllers);
        requestMappedMethods.forEach(this::registerMappings);
    }

    private Set<Class<?>> getControllers(Reflections reflections) {
        return reflections.getTypesAnnotatedWith(Controller.class);
    }

    private Stream<Method> getRequestMappings(Set<Class<?>> controllers) {
        return controllers.stream()
                          .flatMap(controllerType -> Arrays.stream(controllerType.getDeclaredMethods()))
                          .filter(it -> it.isAnnotationPresent(RequestMapping.class));
    }

    private void registerMappings(Method methodType) {
        Object target;
        try {
            target = methodType.getDeclaringClass()
                               .getDeclaredConstructor()
                               .newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        RequestMapping annotation = methodType.getAnnotation(RequestMapping.class);
        var uri = annotation.value();
        var requestMethods = annotation.method();

        for (RequestMethod requestMethod : requestMethods) {
            HandlerKey handlerKey = new HandlerKey(uri, requestMethod);
            HandlerExecution handlerExecution = new HandlerExecution(target, methodType);

            handlerExecutions.put(handlerKey, handlerExecution);
        }
    }

    public HandlerExecution getHandler(final HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());
        HandlerKey handlerKey = new HandlerKey(requestURI, requestMethod);

        return handlerExecutions.get(handlerKey);
    }
}
