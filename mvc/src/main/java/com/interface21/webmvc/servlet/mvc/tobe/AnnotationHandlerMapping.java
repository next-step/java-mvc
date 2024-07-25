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
        var classObjectMap = ControllerScanner.newInstances(classes);
        var mapping = MethodScanner.mappingInstanceAndMethods(classObjectMap, RequestMapping.class);

        this.handlerExecutions = mappingController(mapping);

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

    private Map<HandlerKey, HandlerExecution> mappingController(Map<Object, List<Method>> mapping) {
        return mapping.entrySet().stream()
                .flatMap(StreamUtils::flattenValues)
                .flatMap(this::mappingKeyAndExecution)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Stream<Map.Entry<HandlerKey, HandlerExecution>> mappingKeyAndExecution(
            Map.Entry<Object, Method> entry) {

        var requestMapping = entry.getValue().getAnnotation(RequestMapping.class);
        var methodToExecute = entry.getValue();

        return Arrays.stream(requestMapping.method())
                .map(method -> new HandlerKey(requestMapping.value(), method))
                .map(handlerKey -> Map.entry(handlerKey, new HandlerExecution(methodToExecute)));
    }
}
