package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.mvc.tobe.method.ArgumentResolvers;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HandlerExecution {

    private final Object controller;
    private final Method method;


    public HandlerExecution(final Object controller, final Method method) {
        this.controller = controller;
        this.method = method;
    }

    public Object handle(ArgumentResolvers argumentResolvers, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return method.invoke(controller, argumentResolvers.resolveArguments(method, request, response));
    }

    public Method getMethod() {
        return method;
    }
}
