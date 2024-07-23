package com.interface21.webmvc.servlet.mvc.tobe;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class ArgumentResolvers {

    final List<HandlerMethodArgumentResolver> resolvers;
    final HandlerMethodArgumentResolver defaultResolver;

    public ArgumentResolvers(final String urlPattern) {
        resolvers = List.of(new HttpServletRequestArgumentResolver(),
                new HttpServletResponseArgumentResolver(),
                new PathVariableArgumentResolver(urlPattern),
                new BasicTypeArgumentResolver());
        defaultResolver = new ObjectTypeArgumentResolver();
    }

    public Object resolveArgument(final MethodParameter methodParameter, final HttpServletRequest request, final HttpServletResponse response) {
        return resolvers.stream()
                .filter(resolver -> resolver.supportsParameter(methodParameter))
                .findFirst()
                .orElse(defaultResolver)
                .resolveArgument(methodParameter, request, response);
    }
}
