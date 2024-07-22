package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

public class HandlerExecution {

    private final Object bean;
    private final Method method;

    public HandlerExecution(final Object bean, final Method method) {
        this.bean = bean;
        this.method = method;
    }


    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(bean, request, response);
    }
}
