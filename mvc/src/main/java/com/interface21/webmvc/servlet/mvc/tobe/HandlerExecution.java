package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.web.bind.annotation.ResponseBody;
import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.exception.NotSupportedTypeResolveException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class HandlerExecution {

    private final Object target;
    private final Method method;
    private final List<ParameterResolver> parameterResolvers;
    private final HandlerKey handlerKey;

    public HandlerExecution(Object target, Method method, HandlerKey handlerKey,
                            List<ParameterResolver> parameterResolvers) {
        this.target = target;
        this.method = method;
        this.handlerKey = handlerKey;

        this.parameterResolvers = parameterResolvers;
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        return (ModelAndView) method.invoke(target, resolveParameters(request, response));
    }

    private Object[] resolveParameters(HttpServletRequest request, HttpServletResponse response) {
        return Arrays.stream(method.getParameters())
                     .map(parameter -> resolveEachParameter(parameter, request, response))
                     .toArray();
    }

    private Object resolveEachParameter(Parameter parameter, HttpServletRequest request, HttpServletResponse response) {
        return parameterResolvers.stream()
                                 .filter(resolver -> resolver.accept(request, parameter))
                                 .findFirst()
                                 .map(resolver -> resolver.resolve(request, response, parameter, handlerKey))
                                 .orElseThrow(() -> new NotSupportedTypeResolveException(parameter.getType()));
    }

    public boolean hasAnnotated(Class<ResponseBody> annotationClass) {
        return this.method.isAnnotationPresent(annotationClass) ||
                this.method.getDeclaringClass().isAnnotationPresent(annotationClass);
    }
}
