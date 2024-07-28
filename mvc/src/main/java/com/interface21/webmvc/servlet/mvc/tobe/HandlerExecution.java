package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.method.ArgumentResolvers;
import com.interface21.webmvc.servlet.mvc.tobe.method.MethodParameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class HandlerExecution {

    private final Object controller;
    private final Method method;
    private final ArgumentResolvers argumentResolvers;

    public HandlerExecution(final Object controller, final Method method) {
        this.controller = controller;
        this.method = method;
        this.argumentResolvers = ArgumentResolvers.getInstance();
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(controller, resolveArguments(request, response));
    }

    public Method getMethod() {
        return method;
    }

    private Object[] resolveArguments(HttpServletRequest request, HttpServletResponse response) {
        return convertToMethodParameters()
            .stream()
            .map(parameter -> argumentResolvers.resolveArgument(parameter, request, response))
            .toArray();
    }

    private List<MethodParameter> convertToMethodParameters() {
        return Arrays.stream(method.getParameters())
            .map(parameter -> new MethodParameter(method, parameter))
            .toList();
    }
}
