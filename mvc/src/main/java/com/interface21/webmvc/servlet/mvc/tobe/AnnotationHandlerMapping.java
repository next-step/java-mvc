package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.webmvc.servlet.mvc.tobe.config.ValueConfig;
import com.interface21.webmvc.servlet.mvc.tobe.handler.ControllerScanner;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AnnotationHandlerMapping implements HandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;
    private final ValueConfig valueConfig;

    public AnnotationHandlerMapping(
            ValueConfig valueConfig,
            final Object... basePackage
    ) {
        this.basePackage = basePackage;
        this.handlerExecutions = new HashMap<>();
        this.valueConfig = valueConfig;
    }

    public void initialize() {
        valueConfig.init();

        for (Object object : basePackage) {
            String packageStr = (String) object;
            ControllerScanner controllerScanner = new ControllerScanner(packageStr, valueConfig.getValueMap());
            Map<Class<?>, Object> map = controllerScanner.getControllers();
            addHandlerExecutions(map);
        }
        log.info("Initialized AnnotationHandlerMapping!");
    }

    private void addHandlerExecutions(
            Map<Class<?>, Object> map
    ) {
        map.forEach(
                (classObj, controller) -> {
                    putRequestMappingMethodFromClass(classObj, controller);
                }
        );
    }


    private void putRequestMappingMethodFromClass(Class<?> classObj, Object controller) {
        for (final Method method : classObj.getDeclaredMethods()) {
            if (method.isAnnotationPresent(RequestMapping.class)) {
                putRequestMappingMethod(method, controller);
            }
        }
    }

    private void putRequestMappingMethod(Method method, Object controller) {
        method.setAccessible(true);
        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
        for (RequestMethod requestMethod : annotation.method()) {
            HandlerKey handlerKey = new HandlerKey(annotation.value(), requestMethod);
            HandlerExecution handlerExecution = new HandlerExecution(
                    method,
                    controller
            );

            handlerExecutions.put(handlerKey, handlerExecution);
        }
    }


    public Object getHandler(final HttpServletRequest request) {
        HandlerKey handlerKey = new HandlerKey(
                request.getRequestURI(),
                RequestMethod.valueOf(request.getMethod())
        );

        return handlerExecutions.get(handlerKey);
    }
}
