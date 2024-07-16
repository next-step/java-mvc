package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
        var controllers = new Reflections(basePackage).getTypesAnnotatedWith(Controller.class);
        controllers.forEach(this::mappingController);
    }

    private void mappingController(Class<?> controller) {
        try {
            var controllerInstance = controller.getDeclaredConstructor().newInstance();
            Arrays.stream(controller.getMethods()).forEach(method -> mappingHandler(controllerInstance, method));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException ignored) {
        }
    }

    private void mappingHandler(final Object controllerInstance, final Method method) {
        var mapping = method.getAnnotation(RequestMapping.class);
        if (mapping == null) {
            return;
        }
        for (RequestMethod requestMethod : mapping.method()) {
            var key = new HandlerKey(mapping.value(), requestMethod);
            var execution = new RequestMappedHandlerExecution(controllerInstance, method);
            handlerExecutions.put(key, execution);
        }
    }

    public Object getHandler(final HttpServletRequest request) {
        var uri = request.getRequestURI();
        var method = request.getMethod();
        return handlerExecutions.get(new HandlerKey(uri, RequestMethod.valueOf(method)));
    }
}
