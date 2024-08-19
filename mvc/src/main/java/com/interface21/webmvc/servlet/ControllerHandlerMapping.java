package com.interface21.webmvc.servlet;

import com.interface21.context.stereotype.Controller;
import com.interface21.web.bind.annotation.RequestMapping;
import com.interface21.web.bind.annotation.RequestMethod;
import com.interface21.web.parameter.ParameterParsers;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ControllerHandlerMapping implements HandlerMapping<HttpRequestHandlers> {

    private final ParameterParsers parsers;
    private final Object[] basePackage;

    public ControllerHandlerMapping(final ParameterParsers parsers, final Object... basePackage) {
        this.parsers = parsers;
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
            var execution = new HandlerExecution(controllerInstance, method, parsers);
            handlers.add(key, execution);
        }
    }
}
