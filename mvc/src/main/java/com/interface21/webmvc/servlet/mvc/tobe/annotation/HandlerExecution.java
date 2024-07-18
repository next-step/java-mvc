package com.interface21.webmvc.servlet.mvc.tobe.annotation;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.annotation.parameter.MethodParameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

public class HandlerExecution {

    private final Object handler;
    private final Method method;
    private final MethodParameters methodParameters;

    public HandlerExecution(Object handler, Method method, String urlPattern) {
        this.handler = handler;
        this.method = method;
        this.methodParameters = MethodParameters.of("", method);
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(handler, methodParameters.parseValues(request, response));
    }
}
