package com.interface21.webmvc.servlet.mvc;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.support.PathPatternUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationHandlerMapping implements HandlerMapping{

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;
    private final List<String> urlPatterns = new ArrayList<>();
    private final ControllerScanner controllerScanner = ControllerScanner.getInstance();

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
        this.handlerExecutions = new HashMap<>();
    }

    @Override
    public void initialize() {
        log.info("Initialized AnnotationHandlerMapping!");
        Controllers controllers = controllerScanner.scan(basePackage);
        controllers.values().forEach(this::mappingHandler);
    }

    private void addHandlerExecution(final Method method, final AnnotationControllerClass controller) {
        final var requestMapping = method.getAnnotation(RequestMapping.class);
        final var handlerExecution = new HandlerExecution(controller, method);
        final var urlPattern = requestMapping.value();

        log.info("Path : {}, Controller : {}", urlPattern, controller.getClass());

        Arrays.stream(requestMapping.method())
                .forEach(requestMethod -> {
                    final var handlerKey = new HandlerKey(urlPattern, requestMethod);
                    handlerExecutions.put(handlerKey, handlerExecution);
                    urlPatterns.add(urlPattern);
                });
    }

    private void mappingHandler(AnnotationControllerClass controllerClass) {
        Arrays.stream(controllerClass.getRequestMappingMethod())
                .forEach(method -> addHandlerExecution(method, controllerClass));
    }

    @Override
    public HandlerExecution getHandler(final HttpServletRequest request) {
        String urlPattern = urlPatterns.stream()
            .filter(it -> PathPatternUtil.isUrlMatch(it, request.getRequestURI()))
            .findFirst()
            .orElseThrow(() -> new UnSupportedHandlerException("Unsupported request: " + request.getRequestURI()));

        final var handlerKey = new HandlerKey(urlPattern, RequestMethod.valueOf(request.getMethod()));

        return handlerExecutions.get(handlerKey);
    }
}
