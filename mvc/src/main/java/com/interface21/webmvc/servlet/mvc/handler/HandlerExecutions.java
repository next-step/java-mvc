package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.web.bind.annotation.RequestMapping;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.reflections.ReflectionUtils;

public class HandlerExecutions implements Iterable<HandlerExecution> {
    private final List<HandlerExecution> handlerExecutions;

    private HandlerExecutions(List<HandlerExecution> handlerExecutions) {
        this.handlerExecutions = handlerExecutions;
    }

    public static HandlerExecutions of(Class<?> controllerClass, Object controllerInstance, ArgumentResolvers argumentResolvers) {
        Set<Method> methodsWithRequestMapping = ReflectionUtils.getAllMethods(controllerClass, ReflectionUtils.withAnnotation(RequestMapping.class));
        List<HandlerExecution> handlerExecutions = methodsWithRequestMapping.stream()
                .map(method -> new HandlerExecution(controllerInstance, method, argumentResolvers))
                .toList();

        return new HandlerExecutions(handlerExecutions);
    }

    @Override
    public Iterator<HandlerExecution> iterator() {
        return handlerExecutions.iterator();
    }
}
