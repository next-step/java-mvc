package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationHandlerMapping {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    private final Object[] basePackage;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
        this.handlerExecutions = new HashMap<>();
    }

    public void initialize() {
        log.info("Initialized AnnotationHandlerMapping!");
        Set<Class<?>> controllers = new Reflections(basePackage).getTypesAnnotatedWith(Controller.class);
        for (Class<?> controller : controllers) {
            registerHandlerMappings(controller);
        }
    }

    private void registerHandlerMappings(Class<?> controller) {
        for (Method method : controller.getDeclaredMethods()) {
            registerHandlerMapping(controller, method);
        }
    }

    private void registerHandlerMapping(Class<?> controller, Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping != null) {
            List<HandlerKey> handlerKeys = makeHandlerKeys(requestMapping);
            HandlerExecution handlerExecution = new HandlerExecution(instantiate(controller), method);
            for (HandlerKey handlerKey : handlerKeys) {
                handlerExecutions.put(handlerKey, handlerExecution);
                log.info("Mapped {} to {}", handlerKey, handlerExecution);
            }
        }
    }

    private Object instantiate(Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<HandlerKey> makeHandlerKeys(RequestMapping requestMapping) {
        String url = requestMapping.value();
        RequestMethod[] methods = requestMapping.method();
        return Arrays.stream(methods)
                     .map(method -> new HandlerKey(url, method))
                     .toList();
    }

    public Object getHandler(final HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        HandlerKey handlerKey = new HandlerKey(requestURI, RequestMethod.valueOf(method));
        return handlerExecutions.get(handlerKey);
    }
}
