package com.interface21.webmvc.servlet.mvc.tobe.parameter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import java.util.List;

public final class MethodArgumentResolverRegistry implements ResolverRegistry {

    private final List<ParameterResolver> parameterResolvers;
    private final ParameterResolver defaultParameterResolver;

    public MethodArgumentResolverRegistry(List<ParameterResolver> parameterResolvers,
        ParameterResolver defaultParameterResolver) {
        this.parameterResolvers = parameterResolvers;
        this.defaultParameterResolver = defaultParameterResolver;
    }

    @Override
    public Object resolve(Parameter parameter, HttpServletRequest request,
        HttpServletResponse response) {

        return parameterResolvers.stream()
            .filter(parameterResolver -> parameterResolver.support(parameter))
            .findFirst()
            .orElse(defaultParameterResolver)
            .parseValue(parameter, request, response);
    }

    @Override
    public ResolverRegistry addResolver(String url) {
        parameterResolvers.add(new PathVariableParameterResolver(url));
        return this;
    }
}
