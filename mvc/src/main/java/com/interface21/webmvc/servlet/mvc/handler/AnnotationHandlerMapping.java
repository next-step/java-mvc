package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
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

    public void initialize() {
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        Map<Class<?>, Object> controllers = controllerScanner.getControllers();
        Set<Method> methods = getRequestMappingMethods(controllers.keySet());

        methods.forEach(method -> addHandlerExecutions(controllers, method, method.getDeclaredAnnotation(RequestMapping.class)));
        log.info("Initialized AnnotationHandlerMapping!");
    }

    public Object getHandler(final HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }

    private void addHandlerExecutions(Map<Class<?>, Object> handlerExecution, Method method, RequestMapping requestMapping) {
        String url = requestMapping.value();
        RequestMethod[] requestMethods = requestMapping.method();
        mapHandlerKeys(url, requestMethods)
                .forEach(handlerKey -> handlerExecutions.put(handlerKey, new HandlerExecution(handlerExecution.get(method.getDeclaringClass()), method)));
    }

    private Set<Method> getRequestMappingMethods(Set<Class<?>> controllersKeySet) {
        return controllersKeySet.stream()
                .map(clazz -> Arrays.stream(clazz.getMethods())
                        .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                        .toList())
                .flatMap(List::stream)
                .collect(Collectors.toSet());
    }

    private List<HandlerKey> mapHandlerKeys(String url, RequestMethod[] requestMethods) {
        return Arrays.stream(requestMethods)
                .map(requestMethod -> new HandlerKey(url, requestMethod))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AnnotationHandlerMapping that = (AnnotationHandlerMapping) o;
        boolean deepEquals = Objects.deepEquals(basePackage, that.basePackage);
        boolean equals = Objects.equals(handlerExecutions, that.handlerExecutions);
        return Objects.deepEquals(basePackage, that.basePackage) && Objects.equals(handlerExecutions, that.handlerExecutions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(basePackage), handlerExecutions);
    }
}
