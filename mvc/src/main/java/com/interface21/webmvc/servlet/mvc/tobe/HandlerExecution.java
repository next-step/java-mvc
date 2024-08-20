package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.Arrays;

public class HandlerExecution {
    private final Object handler;
    private final Method method;
    private final MethodArgumentResolvers argumentResolvers;

    public HandlerExecution(Object handler, Method method) {
        this.handler = handler;
        this.method = method;
        this.argumentResolvers = MethodArgumentResolvers.create(method);
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        Object[] args = resolveArguments(request, response);
        return (ModelAndView) method.invoke(handler, args);
    }

    private Object[] resolveArguments(HttpServletRequest request, HttpServletResponse response) {
        return Arrays.stream(method.getParameters())
            .map(param -> argumentResolvers.resolveArguments(request, response, param,method))
            .toArray();
    }
}
