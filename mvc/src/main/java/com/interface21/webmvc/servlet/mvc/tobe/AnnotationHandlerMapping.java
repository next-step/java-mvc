package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationHandlerMapping implements HandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackages;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(final Object... basePackages) {
        this.basePackages = basePackages;
        this.handlerExecutions = new HashMap<>();
    }

    @Override
    public void initialize() {
        log.info("Initialized AnnotationHandlerMapping!");

        ControllerScanner controllerScanner = ControllerScanner.from(basePackages);
        Map<Class<?>, Object> controllersMap = controllerScanner.scan();
        
        for (Class<?> controllerClass : controllersMap.keySet()) {
            String uriPrefix = extractUriPrefix(controllerClass);

            Set<Method> requestMappingMethods = ReflectionUtils.getAllMethods(controllerClass, ReflectionUtils.withAnnotation(RequestMapping.class));
            for (Method requestMappingMethod : requestMappingMethods) {
                Object controller = controllersMap.get(controllerClass);
                HandlerExecution handlerExecution = new HandlerExecution(controller, requestMappingMethod);

                RequestMapping methodRequestMapping = requestMappingMethod.getAnnotation(RequestMapping.class);
                HandlerKeys handlerKeys = HandlerKeys.of(uriPrefix, methodRequestMapping);

                handlerKeys.forEach(handlerKey -> handlerExecutions.put(handlerKey, handlerExecution));
            }
        }
    }

    private String extractUriPrefix(Class<?> controllerClass) {
        RequestMapping controllerRequestMapping = controllerClass.getAnnotation(RequestMapping.class);
        if (controllerRequestMapping == null) {
            return "";
        }
        return controllerRequestMapping.value();
    }

    @Override
    public Object getHandler(final HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request.getRequestURI(), RequestMethod.valueOf(request.getMethod())));
    }
}
