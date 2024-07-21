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

    private final ControllerScanner scanner;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.scanner = new ControllerScanner(basePackage);
        this.handlerExecutions = new HashMap<>();
    }

    public void initialize() {
        scanner.getControllers().forEach((controller, instance) ->
                Arrays.stream(controller.getMethods()).forEach(method -> mappingHandler(instance, method)));
    }

    private void mappingHandler(final Object controllerInstance, final Method method) {
        var handler = method.getAnnotation(RequestMapping.class);
        if (handler == null) {
            return;
        }
        for (RequestMethod requestMethod : handler.method()) {
            var key = new HandlerKey(handler.value(), requestMethod);
            var execution = new HandlerExecution(controllerInstance, method);
            handlerExecutions.put(key, execution);
        }
    }

    public Object getHandler(final HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request));
    }
}
