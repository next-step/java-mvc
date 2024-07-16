package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

public class MethodHandlerExecution extends HandlerExecution {

    private final Object classInstance;
    private final Method method;

    public MethodHandlerExecution(Object classInstance, Method method) {
        this.classInstance = classInstance;
        this.method = method;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(classInstance, request, response);
    }
}
