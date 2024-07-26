package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.HandlerMapping;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationHandlerMapping implements HandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackages;
    private final Map<HandlerKey, HandlerExecution> handlerExecutionsMap;

    public AnnotationHandlerMapping(final Object... basePackages) {
        this.basePackages = basePackages;
        this.handlerExecutionsMap = new HashMap<>();
    }

    @Override
    public void initialize() {
        log.info("Initialized AnnotationHandlerMapping!");

        ControllerScanner controllerScanner = ControllerScanner.from(basePackages);
        Map<Class<?>, Object> controllersMap = controllerScanner.scan();

        for (Class<?> controllerClass : controllersMap.keySet()) {
            HandlerExecutions handlerExecutions = HandlerExecutions.of(controllerClass, controllersMap.get(controllerClass));
            registerHandlerExecutions(controllerClass, handlerExecutions);
        }
    }

    private void registerHandlerExecutions(Class<?> controllerClass, HandlerExecutions handlerExecutions) {
        String uriPrefix = extractUriPrefix(controllerClass);
        for (HandlerExecution handlerExecution : handlerExecutions) {
            HandlerKeys handlerKeys = HandlerKeys.of(uriPrefix, handlerExecution.extractAnnotation(RequestMapping.class));
            handlerKeys.forEach(handlerKey -> handlerExecutionsMap.put(handlerKey, handlerExecution));
        }
    }

    private String extractUriPrefix(Class<?> controllerClass) {
        RequestMapping controllerRequestMapping = controllerClass.getAnnotation(RequestMapping.class);
        if (controllerRequestMapping == null) {
            return "";
        }
        return controllerRequestMapping.value();
    }

    /**
     * @return 요청에 해당하는 Handler가 없을 경우 null을 반환
     */
    @Override
    public Object getHandler(final HttpServletRequest request) {
        HandlerKey newHandlerKey = new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod()));
        HandlerExecution handlerExecution = handlerExecutionsMap.get(newHandlerKey);
        if (handlerExecution != null) {
            return handlerExecution;
        }

        return handlerExecutionsMap.keySet()
                .stream()
                .filter(handlerKey -> handlerKey.checkUrlPattern(newHandlerKey))
                .findAny()
                .map(handlerExecutionsMap::get)
                .orElse(null);
    }
}
