package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping implements HandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
        this.handlerExecutions = new HashMap<>();
    }

    @Override
    public void initialize() {
        final Map<Class<?>, Object> classObjectMap = new ControllerScanner(basePackage).getControllers();
        final Set<Method> requestMappingMethods = getRequestMappingMethod(classObjectMap.keySet());

        requestMappingMethods.forEach(method -> {
            final RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            addHandlerExecutions(classObjectMap, method, requestMapping);
        });

        log.info("Initialized AnnotationHandlerMapping!");
    }

    private void addHandlerExecutions(final Map<Class<?>, Object> controllers, final Method method, final RequestMapping requestMapping) {
        final Object instance = controllers.get(method.getDeclaringClass());
        final List<HandlerKey> handlerKeys = mapHandlerKeys(requestMapping.value(), requestMapping.method());

        handlerKeys.forEach(
                handlerKey -> handlerExecutions.put(handlerKey, new HandlerExecution(instance, method))
        );
    }

    private Set<Method> getRequestMappingMethod(final Set<Class<?>> classes) {
        return classes.stream()
                .flatMap(clazz -> ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class)).stream())
                .collect(Collectors.toSet());
    }

    private List<HandlerKey> mapHandlerKeys(final String url, final RequestMethod[] requestMethods) {
        return Arrays.stream(requestMethods)
                .map(requestMethod -> new HandlerKey(url, requestMethod))
                .collect(Collectors.toList());
    }

    @Override
    public Object getHandler(final HttpServletRequest request) {
        final HandlerKey handlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
        return handlerExecutions.get(handlerKey);
    }
}
