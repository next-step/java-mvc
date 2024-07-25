package com.interface21.webmvc.servlet.mvc.tobe;

import java.lang.reflect.Method;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.interface21.webmvc.servlet.ModelAndView;

public class HandlerExecution {

    private final Object callerClass;
    private final Method method;

    public HandlerExecution(Object clazz, Method method) {
        this.callerClass = clazz;
        this.method = method;
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

        return (ModelAndView) method.invoke(callerClass, request, response);
    }
}
