package com.interface21.webmvc.servlet;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.Arrays;

public class AnnotationHandlerMapping implements HandlerMapping<HttpRequestHandlers> {

    private final Object[] basePackage;

    public AnnotationHandlerMapping(final Object... basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void initialize(final HttpRequestHandlers httpRequestHandlers) {
        var components = ComponentScanner.scan(Controller.class, basePackage);
        components.forEach(component ->
                Arrays.stream(component.methods).forEach(method -> mappingHandler(httpRequestHandlers, component.instance, method)));
    }

    private void mappingHandler(final HttpRequestHandlers handlers, final Object controllerInstance, final Method method) {
        var handler = method.getAnnotation(RequestMapping.class);
        if (handler == null) {
            return;
        }
        for (RequestMethod requestMethod : handler.method()) {
            var key = new HandlerKey(handler.value(), requestMethod);
            var execution = new HandlerExecution(controllerInstance, method);
            handlers.add(key, execution);
        }
    }
}
