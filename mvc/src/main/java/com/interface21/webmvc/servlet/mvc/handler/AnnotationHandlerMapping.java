package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AnnotationHandlerMapping implements HandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutionsMap;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
        this.handlerExecutionsMap = new HashMap<>();
    }

    public void initialize() {
        log.info("Initialized AnnotationHandlerMapping!");
        ArgumentResolvers argumentResolvers = initializeArgumentResolvers();
        initializeHandlerExecutionsMap(argumentResolvers);

    }

    private void initializeHandlerExecutionsMap(final ArgumentResolvers argumentResolvers) {
        ControllerScanner controllerScanner = new ControllerScanner(basePackage);
        Map<Class<?>, Object> controllers = controllerScanner.getControllers();

        for (Class<?> controllerClass : controllers.keySet()) {
            HandlerExecutions handlerExecutions = HandlerExecutions.of(controllerClass, controllers.get(controllerClass), argumentResolvers);
            registerHandlerExecutions(controllerClass, handlerExecutions);
        }
    }

    private void registerHandlerExecutions(final Class<?> controllerClass, final HandlerExecutions handlerExecutions) {
        String uriPrefix = extractUriPrefix(controllerClass);
        for (HandlerExecution handlerExecution : handlerExecutions) {
            HandlerKeys handlerKeys = HandlerKeys.of(uriPrefix, handlerExecution.extractAnnotation(RequestMapping.class));
            handlerKeys.forEach(handlerKey -> this.handlerExecutionsMap.put(handlerKey, handlerExecution));
        }
    }

    private String extractUriPrefix(final Class<?> controllerClass) {
        RequestMapping controllerRequestMapping = controllerClass.getAnnotation(RequestMapping.class);
        if (controllerRequestMapping == null) {
            return "";
        }
        return controllerRequestMapping.value();

    }

    private ArgumentResolvers initializeArgumentResolvers() {
        ArgumentResolvers argumentResolvers = new ArgumentResolvers();
        argumentResolvers.add(new RequestParamArgumentResolver());
        argumentResolvers.add(new PathVariableArgumentResolver());
        argumentResolvers.add(new RequestBodyArgumentResolver());
        return argumentResolvers;
    }

    public Object getHandler(final HttpServletRequest request) {
        HandlerKey newHandlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
        return handlerExecutionsMap.keySet()
                .stream()
                .filter(handlerKey -> handlerKey.checkUrlPatternAndMethod(newHandlerKey))
                .findAny()
                .map(handlerExecutionsMap::get)
                .orElse(null);
    }
}
