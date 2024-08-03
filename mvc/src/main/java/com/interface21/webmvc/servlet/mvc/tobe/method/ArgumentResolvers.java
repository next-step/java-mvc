package com.interface21.webmvc.servlet.mvc.tobe.method;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ArgumentResolvers {

    private final List<HandlerMethodArgumentResolver> resolvers;

    private ArgumentResolvers() {
        resolvers = List.of(
            new HttpServletRequestArgumentResolver(),
            new HttpServletResponseArgumentResolver(),
            new PathVariableArgumentResolver(),
            new SimpleArgumentResolver(),
            new ObjectArgumentResolver()
        );
    }

    private static class Holder {
        private static final ArgumentResolvers INSTANCE = new ArgumentResolvers();
    }

    public static ArgumentResolvers getInstance() {
        return Holder.INSTANCE;
    }

    public Object[] resolveArguments(Method method, HttpServletRequest httpServletRequest,
                                     HttpServletResponse httpServletResponse) {
        return extractMethodParameters(method).stream()
            .map(parameter -> resolveArgument(parameter, httpServletRequest, httpServletResponse))
            .toArray();
    }

    private Object resolveArgument(MethodParameter parameter, HttpServletRequest request,
                                    HttpServletResponse response) {
        return resolvers.stream()
            .filter(resolver -> resolver.supportsParameter(parameter))
            .findFirst()
            .map(resolver -> resolver.resolveArgument(parameter, request, response))
            .orElse(null);
    }

    private List<MethodParameter> extractMethodParameters(Method method) {
        return Arrays.stream(method.getParameters())
            .map(parameter -> new MethodParameter(method, parameter))
            .toList();
    }
}
