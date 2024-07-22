package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.core.util.ReflectionUtils;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.asis.Controller;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnnotationHandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;
    private final static HandlerExecution handlerExecution = new HandlerExecution();
    private Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
        this.handlerExecutions = new HashMap<>();
    }

    public void initialize() {
        log.info("Initialized AnnotationHandlerMapping!");
        var methods = ReflectionUtils.getMethodsWithAnnotationType(basePackage, RequestMapping.class);
        this.handlerExecutions = mappingHandlerExecution(methods);
    }


    public Object getHandler(final HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.from(request.getMethod())));
    }


    private static Map<HandlerKey, HandlerExecution> mappingHandlerExecution(List<Method> methods) {
        return methods.stream()
                .flatMap(method -> {
                    var annotation = method.getAnnotation(RequestMapping.class);
                    var path = annotation.value();
                    return Stream.of(annotation.method())
                            .map(requestMethod -> Map.entry(path, requestMethod));
                })
                .map(it -> Map.entry(new HandlerKey(it.getKey(), it.getValue()), handlerExecution))
                .distinct()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
