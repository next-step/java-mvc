package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.interface21.core.util.StreamUtils;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;

public class AnnotationHandlerMapping implements HandlerMapping, HandlerAdapter {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;
    private Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
        this.handlerExecutions = new HashMap<>();
    }

    public void initialize() {
        log.info("Initialized AnnotationHandlerMapping!");
        var classes = ControllerScanner.scanControllers(basePackage);
        var classInstances = ControllerScanner.newInstances(classes);
        var mapping = MethodScanner.mappingInstanceAndMethods(classInstances, RequestMapping.class);

        this.handlerExecutions = createHandlerExecutionMap(mapping);

        this.handlerExecutions
                .keySet()
                .forEach(
                        path ->
                                log.info(
                                        "Path : {}, HandlerExecution : {}",
                                        path,
                                        handlerExecutions.get(path).getClass()));
    }

    @Override
    public boolean supports(HttpServletRequest request) {
        return handlerExecutions.containsKey(
                new HandlerKey(request.getRequestURI(), RequestMethod.from(request.getMethod())));
    }

    public Object getHandler(final HttpServletRequest request) {
        return handlerExecutions.get(
                new HandlerKey(request.getRequestURI(), RequestMethod.from(request.getMethod())));
    }

    private Map<HandlerKey, HandlerExecution> createHandlerExecutionMap(
            List<InstanceMethods> mapping) {
        return mapping.stream()
                .flatMap(InstanceMethods::flattenMethods)
                .flatMap(this::mappingKeyAndExecution)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Stream<Map.Entry<HandlerKey, HandlerExecution>> mappingKeyAndExecution(
            Map.Entry<Object, Method> entry) {

        var instance = entry.getKey();
        var method = entry.getValue();
        var requestMapping = MethodScanner.scanAnnotation(method, RequestMapping.class);

        return StreamUtils.flattenValues(
                        requestMapping.value(), Arrays.asList(requestMapping.method()))
                .map(HandlerKey::of)
                .map(handlerKey -> Map.entry(handlerKey, new HandlerExecution(instance, method)));
    }
}
