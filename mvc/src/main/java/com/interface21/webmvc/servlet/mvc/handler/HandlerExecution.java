package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.webmvc.servlet.mvc.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public class HandlerExecution {
    private final Object handler;
    private final Method method;
    private final ArgumentResolvers argumentResolvers;

    public HandlerExecution(Object handler, Method method, ArgumentResolvers argumentResolvers) {
        this.handler = handler;
        this.method = method;
        this.argumentResolvers = argumentResolvers;
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        Parameter[] parameters = method.getParameters();
        Object[] arguments = Arrays.stream(parameters)
                .map(parameter -> {
                    try {
                        ArgumentResolver argumentResolver = argumentResolvers.findOneSupports(parameter);
                        return argumentResolver.resolve(parameter, method, request, response);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray();

        return (ModelAndView) method.invoke(handler, arguments);
    }

    public <T extends Annotation> T extractAnnotation(Class<T> annotationType) {
        return method.getAnnotation(annotationType);
    }
}
