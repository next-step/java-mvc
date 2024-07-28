package com.interface21.webmvc.servlet.mvc.tobe.method;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

public class ArgumentResolvers {

    private final List<HandlerMethodArgumentResolver> resolvers;

    private ArgumentResolvers() {
        resolvers = List.of(
            new SimpleArgumentResolver(),
            new PathVariableArgumentResolver(),
            new HttpServletRequestArgumentResolver(),
            new HttpServletResponseArgumentResolver(),
            new ObjectArgumentResolver()
        );
    }

    private static class holder {
        private static final ArgumentResolvers INSTANCE = new ArgumentResolvers();
    }

    public static ArgumentResolvers getInstance() {
        return holder.INSTANCE;
    }

    public Object resolveArgument(MethodParameter methodParameter, HttpServletRequest httpServletRequest,
                                  HttpServletResponse httpServletResponse) {
        return resolvers.stream()
            .filter(resolver -> resolver.supportsParameter(methodParameter))
            .map(resolver -> resolver.resolveArgument(methodParameter, httpServletRequest, httpServletResponse))
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }
}
