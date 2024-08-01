package com.interface21.webmvc.servlet.mvc.handler;

import com.interface21.webmvc.servlet.ModelAndView;
import com.interface21.webmvc.servlet.mvc.handler.parameter.ArgumentResolver;
import com.interface21.webmvc.servlet.mvc.handler.parameter.ArgumentResolverConfiguration;
import com.interface21.webmvc.servlet.mvc.handler.parameter.MethodParameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class HandlerExecution {

    private final Object instance;
    private final Method method;
    private final List<ArgumentResolver> resolvers;

    public HandlerExecution(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
        this.resolvers = ArgumentResolverConfiguration.getResolvers();
    }

    public ModelAndView handle(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        Parameter[] parameters = method.getParameters();
        Object[] args = convertParameters(parameters, request, response);
        return (ModelAndView) method.invoke(instance, args);
    }

    private Object[] convertParameters(Parameter[] parameters, HttpServletRequest request, HttpServletResponse response) {
        return Arrays.stream(parameters)
                     .map(parameter -> resolveArgument(parameter, request, response))
                     .toArray(Object[]::new);
    }

    private Object resolveArgument(Parameter parameter, HttpServletRequest request, HttpServletResponse response) {
        return resolvers.stream()
                        .filter(resolver -> resolver.supportsParameter(parameter))
                        .findFirst()
                        .map(resolver -> {
                            try {
                                return resolver.resolveArgument(new MethodParameter(method, parameter), request, response);
                            } catch (Exception e) {
                                e.printStackTrace();
                                return null;
                            }
                        })
                        .orElse(null);
    }
}
