package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.argumentresolver.ArgumentResolvers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

public class HandlerExecution {

    private final Object handler;
    private final Method method;
    private final ArgumentResolvers argumentResolvers;

    public HandlerExecution(final Object handler, final Method method) {
        this.handler = handler;
        this.method = method;
        this.argumentResolvers = ArgumentResolvers.getInstance();
    }

    public HandlerExecution(final Object handler, final String methodName) {
        this.handler = handler;
        this.method = getHandlerMethod(handler, methodName);
        this.argumentResolvers = ArgumentResolvers.getInstance();
    }

    private Method getHandlerMethod(final Object handler, final String methodName) {
        try {
            return handler.getClass()
                    .getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
        } catch (final NoSuchMethodException e) {
            throw new IllegalArgumentException("No such method : " + methodName);
        }
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final Object[] arguments = argumentResolvers.resolveArguments(method, request, response);
        final Object result = method.invoke(handler, arguments);
        return (ModelAndView) result;
    }
}
