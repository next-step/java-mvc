package com.interface21.webmvc.servlet;

import com.interface21.webmvc.servlet.view.JspView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

public class HandlerExecution {
    private final Object controllerInstance;
    private final Method method;

    public HandlerExecution(Object controllerInstance, Method method) {
        this.controllerInstance = controllerInstance;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        var result = method.invoke(controllerInstance, request, response);
        if (result instanceof ModelAndView) {
            return (ModelAndView) result;
        }
        return new ModelAndView(new JspView((String) result));
    }

    public void handle(Throwable ex, HttpServletRequest request, HttpServletResponse response) {
        try {
            method.invoke(controllerInstance, ex, request, response);
        } catch (Exception ignored) {
        }
    }
}
