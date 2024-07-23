package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.exception.HandlerExecutionFailException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.util.Arrays;

public class HandlerExecution {

    private final Object instance;
    private final Method targetMethod;
    private final ArgumentResolvers argumentResolvers;

    public HandlerExecution(final Object instance, final Method targetMethod, final ArgumentResolvers argumentResolvers) {
        this.instance = instance;
        this.targetMethod = targetMethod;
        this.argumentResolvers = argumentResolvers;
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final Object[] args = resolveArguments(request, response);
        final Object invoke = targetMethod.invoke(instance, args);
        if (invoke instanceof final ModelAndView modelAndView) {
            return modelAndView;
        }
        throw new HandlerExecutionFailException("handle method must return ModelAndView Class");
    }

    private Object[] resolveArguments(final HttpServletRequest request, final HttpServletResponse response) {
        return Arrays.stream(targetMethod.getParameters())
                .map(parameterType -> argumentResolvers.resolveArgument(new MethodParameter(parameterType), request, response))
                .toArray();
    }
}
