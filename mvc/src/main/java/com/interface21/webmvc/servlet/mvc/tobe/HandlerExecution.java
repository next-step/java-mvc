package com.interface21.webmvc.servlet.mvc.tobe;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.tobe.parameterresolver.HttpServletRequestResolver;
import com.interface21.webmvc.servlet.mvc.tobe.parameterresolver.HttpServletResponseResolver;
import com.interface21.webmvc.servlet.mvc.tobe.parameterresolver.HttpSessionResolver;
import com.interface21.webmvc.servlet.mvc.tobe.parameterresolver.RequestParameterResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class HandlerExecution {

    private final Object target;
    private final Method method;
    private final HandlerKey handlerKey;
    private List<ParameterResolver> parameterResolvers;

    public HandlerExecution(Object target, Method method, HandlerKey handlerKey) {
        this.target = target;
        this.method = method;
        this.handlerKey = handlerKey;

        initParameterResolvers();
    }

    private void initParameterResolvers() {
        this.parameterResolvers = new ArrayList<>();
        this.parameterResolvers.add(new HttpServletRequestResolver());
        this.parameterResolvers.add(new HttpServletResponseResolver());
        this.parameterResolvers.add(new HttpSessionResolver());
        this.parameterResolvers.add(new RequestParameterResolver(handlerKey));
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        Object[] resolvedArguments = Arrays.stream(method.getParameters())
                               .map(parameter -> resolveParameter(parameter, request, response))
                               .toArray();

        return (ModelAndView) method.invoke(target, resolvedArguments);
    }

    private Object resolveParameter(Parameter parameter, HttpServletRequest request, HttpServletResponse response) {
        return parameterResolvers.stream()
                                 .filter(resolver -> resolver.accept(request, parameter))
                                 .findFirst()
                                 .map(resolver -> resolver.resolve(request, response, parameter))
                                 .orElseThrow(() -> new NoSuchElementException("No value present") /*XXX: 예외 정의할 것*/);
    }
}
