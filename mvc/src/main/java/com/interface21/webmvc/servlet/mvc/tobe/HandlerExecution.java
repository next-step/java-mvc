package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HandlerExecution {

    private final Object controller;
    private final Method method;
    private final List<HandlerInterceptor> interceptors;
    private List<Object> arguments;


    public HandlerExecution(final Object controller, final Method method, List<HandlerInterceptor> interceptors) {
        this.controller = controller;
        this.method = method;
        this.interceptors = interceptors;
        this.arguments = new ArrayList<>();
    }

    public Object handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        preHandle(request, response);
        return method.invoke(controller, arguments.toArray());
    }

    public Method getMethod() {
        return method;
    }

    public void addArgument(Object arguments) {
        this.arguments.add(arguments);
    }

    private void preHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        for (HandlerInterceptor interceptor : interceptors) {
            interceptor.preHandle(request, response, this);
        }
    }
}
