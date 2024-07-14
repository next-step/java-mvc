package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
        log.info("Initialized AnnotationHandlerMapping!");
        handlerExecutions.putAll(mappingBasePackageHandlers());
    }

    private Map<HandlerKey, HandlerExecution> mappingBasePackageHandlers() {
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        return controllerScanner.getControllers()
                .stream()
                .map(this::parseControllerHandler)
                .flatMap(controllers -> controllers.entrySet().stream())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    private Map<HandlerKey, HandlerExecution> parseControllerHandler(ControllerInstance controller) {
        return controller.getRequestMappingMethods()
                .stream()
                .map(method -> parseMethodHandler(method, controller.getInstance()))
                .flatMap(handlers -> handlers.entrySet().stream())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    private Map<HandlerKey, HandlerExecution> parseMethodHandler(Method method, Object controllerInstance) {
        RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
        String url = requestMapping.value();
        HandlerExecution handlerExecution = new HandlerExecution(controllerInstance, method.getName());

        return Arrays.stream(requestMapping.method())
                .map(requestMethod -> new HandlerKey(url, requestMethod))
                .collect(Collectors.toMap(
                        handlerKey -> handlerKey,
                        handlerKey -> handlerExecution)
                );
    }

    @Override
    public Object getHandler(final HttpServletRequest request) {
        HandlerKey handlerKey = HandlerKey.from(request);
        return handlerExecutions.get(handlerKey);
    }
}
