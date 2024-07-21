package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AnnotationHandlerMapping implements HandlerMapping {

    private final ControllerScanner scanner;
    private final Map<HandlerKey, HandlerExecution> handlerExecutions;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.scanner = new ControllerScanner(basePackage);
        this.handlerExecutions = new HashMap<>();
    }

    @Override
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

    @Override
    public Object getHandler(final HttpServletRequest request) {
        return handlerExecutions.get(new HandlerKey(request));
    }
}
