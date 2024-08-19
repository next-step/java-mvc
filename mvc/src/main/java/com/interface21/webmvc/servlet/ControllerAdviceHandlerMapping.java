package com.interface21.webmvc.servlet;

import com.interface21.context.stereotype.ControllerAdvice;
import com.interface21.web.bind.annotation.ExceptionHandler;
import com.interface21.web.parameter.ParameterParsers;

import java.lang.reflect.Method;
import java.util.Arrays;

public class ControllerAdviceHandlerMapping implements HandlerMapping<ExceptionHandlers> {

    private final ParameterParsers parsers;
    private final Object[] basePackage;

    public ControllerAdviceHandlerMapping(ParameterParsers parsers, final Object... basePackage) {
        this.parsers = parsers;
        this.basePackage = basePackage;
    }

    @Override
    public void initialize(ExceptionHandlers handlers) {
        var components = ComponentScanner.scan(ControllerAdvice.class, basePackage);
        components.forEach(component ->
                Arrays.stream(component.methods).forEach(method -> mappingHandler(handlers, component.instance, method)));

    }

    private void mappingHandler(final ExceptionHandlers handlers, final Object controllerInstance, final Method method) {
        var handler = method.getAnnotation(ExceptionHandler.class);
        if (handler == null) {
            return;
        }
        for (Class<? extends Throwable> exceptionClass : handler.value()) {
            var execution = new HandlerExecution(controllerInstance, method, parsers);
            handlers.add(exceptionClass, execution);
        }
    }
}
