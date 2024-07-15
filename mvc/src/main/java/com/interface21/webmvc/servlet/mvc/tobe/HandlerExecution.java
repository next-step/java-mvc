package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.exception.HandlerExecutionFailException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

public class HandlerExecution {

    private final Object instance;
    private final Method targetMethod;

    public HandlerExecution(final Object instance, final Method targetMethod) {
        this.instance = instance;
        this.targetMethod = targetMethod;
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final Object invoke = targetMethod.invoke(instance, request, response);
        if (invoke instanceof final ModelAndView modelAndView) {
            return modelAndView;
        }
        throw new HandlerExecutionFailException("handle method must return ModelAndView Class");
    }
}
