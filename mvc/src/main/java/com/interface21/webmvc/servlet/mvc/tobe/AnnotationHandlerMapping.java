package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        log.info("Initialized AnnotationHandlerMapping!");
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        controllerScanner.scan().forEach(this::processController);
    }

    @Override
    public Object getHandler(final HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }

    private void processController(Class<?> controller, ControllerInstance instance) {
        Arrays.stream(controller.getMethods())
              .filter(this::hasRequestMappingMethod)
              .flatMap(method -> createHandlerExecutions(instance, method))
              .forEach(this::putHandlerExecution);
    }

    private boolean hasRequestMappingMethod(Method method) {
        return method.isAnnotationPresent(RequestMapping.class);
    }

    private Stream<Entry<HandlerKey, HandlerExecution>> createHandlerExecutions(ControllerInstance instance, Method method) {
        HandlerExecution handlerExecution = makeHandlerExecution(instance, method);
        return makeHandlerKeys(handlerExecution).entrySet().stream();
    }

    private void putHandlerExecution(Map.Entry<HandlerKey, HandlerExecution> entry) {
        handlerExecutions.put(entry.getKey(), entry.getValue());
    }

    private HandlerExecution makeHandlerExecution(ControllerInstance instance, Method method) {
        return new HandlerExecution(instance.getController(), method);
    }

    private Map<HandlerKey, HandlerExecution> makeHandlerKeys(final HandlerExecution handlerExecution) {
        RequestMapping requestMapping = handlerExecution.getMethod().getAnnotation(RequestMapping.class);
        return Stream.of(requestMapping.method())
                     .collect(Collectors.toMap(
                         requestMethod -> new HandlerKey(requestMapping.value(), requestMethod),
                         requestMethod -> handlerExecution));
    }
}
